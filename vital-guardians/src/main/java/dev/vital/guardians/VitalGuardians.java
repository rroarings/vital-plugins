package dev.vital.guardians;

import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.skillcalculator.skills.RunecraftAction;
import net.runelite.client.plugins.worldhopper.WorldHopperPlugin;
import net.unethicalite.api.quests.Quests;
import org.pf4j.Extension;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@PluginDescriptor(name = "vital-guardians", enabledByDefault = false)
@Extension
@Slf4j
public class VitalGuardians extends LoopedPlugin
{
	private final int BODY_GUARDIAN = 43709;
	private final int MIND_GUARDIAN = 43705;
	private final int DEATH_GUARDIAN = 43707;
	private final int AIR_GUARDIAN = 43701;
	private final int COSMIC_GUARDIAN = 43710;
	private final int WATER_GUARDIAN = 43702;
	private final int EARTH_GUARDIAN = 43703;
	private final int CHAOS_GUARDIAN = 43706;
	private final int LAW_GUARDIAN = 43712;
	private final int BLOOD_GUARDIAN = 43708;
	private final int FIRE_GUARDIAN = 43704;
	private final int NATURE_GUARDIAN = 43711;

	private ScheduledExecutorService executor;

	@Inject
	private VitalGuardiansConfig config;

	private static final WorldArea INSIDE_GUARDIANS = new WorldArea(3586, 9484, 70, 70, 0);
	private static final WorldArea OUTSIDE_GUARDIANS = new WorldArea(3611, 9471, 9, 12, 0);

	public boolean game_started = false;
	public boolean has_enough_mats = false;
	public boolean should_deposit = false;

	@Override
	protected int loop()
	{
		if(!Game.isLoggedIn() || Movement.isWalking()) {

			return Rand.nextInt(1000, 2000);
		}

		if(!game_started) {

			if(OUTSIDE_GUARDIANS.contains(LocalPlayer.get()))
			{
				var barrier = TileObjects.getNearest(43700);
				if(barrier != null)
				{
					var fuck = ((GameObject) barrier).getRenderable();

					DynamicObject object = (DynamicObject) fuck;
					if (object.getAnimationID() == 9366)
					{
						barrier.interact("Pass");
						return Rand.nextInt(350, 500);
					}
				}
			}
			else if(!INSIDE_GUARDIANS.contains(LocalPlayer.get()))
			{
				var portal = TileObjects.getNearest("Portal");
				var altar = TileObjects.getNearest("Altar");
				if (portal != null && altar != null)
				{
					portal.interact("Use");
				}
				else {

					Game.logout();
				}
			}
			return 1000;
		}
		else if(INSIDE_GUARDIANS.contains(LocalPlayer.get())) {

			if(!Inventory.contains(ItemID.GUARDIAN_FRAGMENTS) && !Inventory.contains(ItemID.GUARDIAN_ESSENCE))
			{
				has_enough_mats = false;
			}

			if(Inventory.contains(ItemID.ELEMENTAL_GUARDIAN_STONE) || Inventory.contains(ItemID.CATALYTIC_GUARDIAN_STONE))
			{
				NPCs.getNearest("The Great Guardian").interact("Power-up");
				should_deposit = true;
				return Rand.nextInt(2800, 3000);
			}
			else if(!has_enough_mats)
			{
				if (Inventory.getCount(true, ItemID.GUARDIAN_FRAGMENTS) < config.guardianFragments())
				{
					if(!LocalPlayer.get().isAnimating())
					{
						TileObjects.getNearest(43717).interact("Mine");
					}
				}
				else
				{
					has_enough_mats = true;
				}
			}
			else if(should_deposit) {
				TileObjects.getNearest(43696).interact("Deposit-runes");
				should_deposit = false;
				return Rand.nextInt(4300, 5200);
			}
			else {

				if(!Inventory.isFull())
				{
					if(Inventory.contains(ItemID.GUARDIAN_FRAGMENTS))
					{
						if(!Inventory.contains(ItemID.GUARDIAN_ESSENCE))
						{
							if(Inventory.getCount(true, ItemID.GUARDIAN_FRAGMENTS) <= config.minGuardianFragments()) {
								has_enough_mats = false;
								should_deposit = false;
							}
							else {
								TileObjects.getNearest(43754).interact("Work-at");
							}
						}
						else {

							has_enough_mats = true;
						}
					}
				}
				else if(Inventory.isFull()) {

					if(!Inventory.contains(ItemID.GUARDIAN_ESSENCE)) {

						return 100;
					}

					for(var guardian : TileObjects.getAll(43705, 43701, 43710, 43702, 43703, 43711, 43704, 43708, 43712,
							43707, 43706, 43709, 43702)) {

						var fuck = ((GameObject)guardian).getRenderable();

						DynamicObject object = (DynamicObject)fuck;
						if(object.getAnimationID() == 9363) {

							var rc_level = Skills.getLevel(Skill.RUNECRAFT);
							boolean should_skip = true;
							switch(guardian.getId()){

								case BLOOD_GUARDIAN: {
									if(rc_level >= RunecraftAction.BLOOD_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case EARTH_GUARDIAN: {
									if(rc_level >= RunecraftAction.EARTH_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case LAW_GUARDIAN: {
									if(rc_level >= RunecraftAction.LAW_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case NATURE_GUARDIAN: {
									if(rc_level >= RunecraftAction.NATURE_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case AIR_GUARDIAN: {
									if(rc_level >= RunecraftAction.AIR_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case MIND_GUARDIAN: {
									if(rc_level >= RunecraftAction.MIND_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case FIRE_GUARDIAN: {
									if(rc_level >= RunecraftAction.FIRE_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case DEATH_GUARDIAN: {
									if(rc_level >= RunecraftAction.DEATH_RUNE.getLevel() && Quests.getState(Quest.MOURNINGS_END_PART_II) == QuestState.FINISHED) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case WATER_GUARDIAN: {
									if(rc_level >= RunecraftAction.WATER_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case BODY_GUARDIAN: {
									if(rc_level >= RunecraftAction.BODY_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case COSMIC_GUARDIAN: {
									if(rc_level >= RunecraftAction.COSMIC_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
								case CHAOS_GUARDIAN: {
									if(rc_level >= RunecraftAction.CHAOS_RUNE.getLevel()) {
										should_skip = false;
										guardian.interact("Enter");
									}
									break;
								}
							}

							if(!should_skip)
							{
								break;
							}
						}
					}
				}
			}
		}
		else {

			if(Inventory.contains(ItemID.GUARDIAN_ESSENCE))
			{
				TileObjects.getNearest("Altar").interact("Craft-rune");
			}
			else {

				TileObjects.getNearest("Portal").interact("Use");
			}

			return Rand.nextInt(1400, 1600);
		}

		return Rand.nextInt(1000, 1200);
	}
	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if(chatMessage.getType() != ChatMessageType.SPAM && chatMessage.getType() != ChatMessageType.GAMEMESSAGE) return;

		String msg = chatMessage.getMessage();
		if(msg.contains("The rift becomes active!")) {
			has_enough_mats = false;
			game_started = true;
		}
		else if(msg.contains("The Portal Guardians close their rifts.")) {
			has_enough_mats = false;
			game_started = false;
		}
		else if(msg.contains("The Great Guardian was defeated!")) {
			has_enough_mats = false;
			game_started = false;
		}
		else if(msg.contains("The Great Guardian successfully closed the rift!")) {
			has_enough_mats = false;
			game_started = false;
		}
	}

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked e)
	{
		if (!e.getGroup().equals("vitalguardiansconfig"))
		{
			return;
		}

		switch (e.getKey())
		{
			case "riftManualStart":
				game_started = true;
				has_enough_mats = false;
				break;
		}
	}
	@Override
	public void startUp()
	{
		has_enough_mats = game_started = false;
	}

	@Override
	public void shutDown()
	{
		has_enough_mats = game_started = false;
	}

	@Provides
	VitalGuardiansConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalGuardiansConfig.class);
	}
}
