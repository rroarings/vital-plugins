package dev.vital.construction;

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
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Dialog;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-construction", enabledByDefault = false)
@Extension
@Slf4j
public class VitalConstruction extends LoopedPlugin
{
	private static final WorldArea mahogany_homes = new WorldArea(2989, 3364, 2, 2, 0);
	public int current_task = -1;
	public int tick_delay = 0;
	public int tick_count = 0;
	public boolean has_tasak = false;
	public boolean tick_delay_begin = false;
	@Inject
	private VitalConstructionConfig config;

	@Override
	protected int loop()
	{
		var local = LocalPlayer.get();
		if (Movement.isWalking() || local.isAnimating() || !Game.isLoggedIn())
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

		if (!has_tasak)
		{
			if (!mahogany_homes.contains(local))
			{
				Movement.walkTo(mahogany_homes.getRandom());
			}
			else
			{
				NPC Amy = NPCs.query().names("Amy").results().nearest(mahogany_homes.getCenter());
				if (Amy != null)
				{
					Amy.interact("Contract");
				}

				if (Dialog.isOpen())
				{

					if (Dialog.canContinue())
					{

						Dialog.continueSpace();
					}

					var level = Skills.getLevel(Skill.CONSTRUCTION);

					if (level < 20)
					{

						Keyboard.type('1');
					}
					else if (level >= 20 && level < 50)
					{

						Keyboard.type('2');
					}
					else if (level >= 50 && level < 70)
					{

						Keyboard.type('3');
					}
					else if (level >= 70)
					{

						Keyboard.type('4');
					}
				}
				else
				{

					has_tasak = true;
				}
			}
		}
		else
		{

			if (current_task == 0)
			{

				//if(WorldLocation.ARDOUGNE_STALLS.contains(ItemID.ARDOUGNE_TELEPORT)) {

				//	Inventory.getFirst(ItemID.ARDOUGNE_TELEPORT).interact("Break");
				//}
			}
		}

		return Rand.nextInt(1000, 1200);
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

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		final String message = event.getMessage();
		if (message.contains("Go see Noella"))
		{ //ardougne

			current_task = 0;
		}
		else if (message.contains("Go see Bob"))
		{ //varrock

			current_task = 1;
		}
		else if (message.contains("Go see Jeff"))
		{ //varrock 3240, 3450

			current_task = 2;
		}
		else if (message.contains("Go see Barbara"))
		{ //Hosidius xeric glade south 1749, 3534

			current_task = 3;
		}
		else if (message.contains("Go see Mariah"))
		{ //Hosidius xeric glade north 1765, 3621

			current_task = 4;
		}
		else if (message.contains("Go see Sarah"))
		{ //varrock 3235, 3385

			current_task = 5;
		}
		else if (message.contains("Go see Larry"))
		{ //Falador 3235, 3385

			current_task = 6;
		}
		else if (message.contains("Go see Leela"))
		{ //Hosidius 1785, 3589

			current_task = 7;
		}
		else if (message.contains("Go see Tau"))
		{ //Hosidius 3048, 3347

			current_task = 8;
		}
		else if (message.contains("Go see Ross"))
		{ //Ardougne 2612, 2612

			current_task = 9;
		}
		else if (message.contains("Go see Norman"))
		{ //Falador 3038, 3344

			current_task = 10;
		}
		else if (message.contains("Go see Jess"))
		{ //Falador 2621, 3293

			current_task = 11;
		}
	}

	@Provides
	VitalConstructionConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalConstructionConfig.class);
	}
}
