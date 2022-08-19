package dev.vital.guardians;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.GameAccount;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PluginDescriptor(name = "vital-guardians", enabledByDefault = false)
@Extension
@Slf4j
public class VitalGuardians extends LoopedPlugin
{
	private static final WorldArea INSIDE_GUARDIANS = new WorldArea(3586, 9484, 70, 70, 0);
	private static final WorldArea OUTSIDE_GUARDIANS = new WorldArea(3611, 9471, 9, 12, 0);
	private static final String REWARD_POINT_REGEX = "Elemental attunement level:[^>]+>(\\d+).*Catalytic attunement level:[^>]+>(\\d+)";
	private static final Pattern REWARD_POINT_PATTERN = Pattern.compile(REWARD_POINT_REGEX);
	public int tick_delay = 0;
	public int tick_count = 0;
	public boolean game_started = false;
	public boolean tick_delay_begin = false;
	public boolean has_enough_mats = false;
	public boolean should_deposit = false;
	@Inject
	private VitalGuardiansConfig config;

	@Override
	protected int loop()
	{
		if (Movement.isWalking() || LocalPlayer.get().isAnimating() || !Game.isLoggedIn())
		{

			return Rand.nextInt(1000, 2000);
		}

		if (tick_delay_begin)
		{

			if (tick_count < tick_delay)
			{
				return 1;
			}
			else
			{

				var min = config.minimumDelay();
				var max = config.maximumDelay();

				if (min > 600)
				{
					min = 600;
				}
				if (max > 600)
				{
					max = 600;
				}

				tick_delay_begin = false;

				return Rand.nextInt(min, max);
			}
		}

		if (!game_started)
		{

		}
		else if (INSIDE_GUARDIANS.contains(LocalPlayer.get()))
		{

			if (!has_enough_mats)
			{
				if (Inventory.getCount(ItemID.GUARDIAN_FRAGMENTS) < 150)
				{
					if (!LocalPlayer.get().isAnimating())
					{
						TileObjects.getNearest(43717).interact("Mine");

					}
				}
				else
				{
					has_enough_mats = true;
				}
			}
			else if (Inventory.contains(ItemID.ELEMENTAL_GUARDIAN_STONE))
			{
				NPCs.getNearest("The Great Guardian").interact("Power-up");
				should_deposit = true;
				return Rand.nextInt(2800, 3000);
			}
			else if (should_deposit)
			{
				TileObjects.getNearest(43696).interact("Deposit-runes");
				should_deposit = false;
				has_enough_mats = false;
			}
			else
			{

				if (Inventory.getCount(ItemID.GUARDIAN_ESSENCE) < Inventory.getFreeSlots())
				{
					TileObjects.getNearest(43754).interact("Work-at");
					return Rand.nextInt(15000, 17000);
				}
				else
				{

					var rc = Skills.getLevel(Skill.RUNECRAFT);

					for (var uh : TileObjects.getAll(43705, 43701, 43710, 43702, 43703, 43711, 43704, 43708, 43712, 43707, 43706, 43709, 43702))
					{

						if (((DynamicObject) uh).getAnimationID() != 9362)
						{

							if (!uh.getName().contains("Blood") && !uh.getName().contains("Death"))
							{

								uh.interact("Enter");
							}
						}
					}
					//if(((DynamicObject)TileObjects.getNearest(43701)).getAnimationID()=

				}
			}
		}
		else
		{

			if (Inventory.contains(ItemID.GUARDIAN_ESSENCE))
			{
				TileObjects.getNearest("Altar").interact("Craft-rune");
				return Rand.nextInt(1000, 1200);
			}
			else
			{

				TileObjects.getNearest("Portal").interact("Use");
				return Rand.nextInt(2500, 3500);
			}
		}

		return Rand.nextInt(1000, 1200);
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if (chatMessage.getType() != ChatMessageType.SPAM && chatMessage.getType() != ChatMessageType.GAMEMESSAGE)
			return;

		String msg = chatMessage.getMessage();
		if (msg.contains("The rift becomes active!"))
		{
			game_started = true;
		}
		else if (msg.contains("The rift will become active in 30 seconds."))
		{

		}
		else if (msg.contains("The rift will become active in 10 seconds."))
		{

		}
		else if (msg.contains("The rift will become active in 5 seconds."))
		{

		}
		else if (msg.contains("The Portal Guardians will keep their rifts open for another 30 seconds."))
		{

		}
		else if (msg.contains("You found some loot:"))
		{

		}

		Matcher rewardPointMatcher = REWARD_POINT_PATTERN.matcher(msg);
		if (rewardPointMatcher.find())
		{
			//elementalRewardPoints = Integer.parseInt(rewardPointMatcher.group(1));
			//catalyticRewardPoints = Integer.parseInt(rewardPointMatcher.group(2));
		}
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		tick_count++;

		if (!tick_delay_begin)
		{
			tick_delay = tick_count + Rand.nextInt(config.tickMinDelay(), config.tickMaxDelay());
		}
	}

	@Provides
	VitalGuardiansConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalGuardiansConfig.class);
	}
}
