package dev.vital.magic;

import com.google.inject.Inject;
import com.google.inject.Provides;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.Rune;
import net.unethicalite.api.magic.RuneRequirement;
import net.unethicalite.api.magic.Spell;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Tab;
import net.unethicalite.api.widgets.Tabs;
import net.unethicalite.api.widgets.Widgets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-magic", enabledByDefault = false)
@Extension
@Slf4j
public class VitalMagic extends LoopedPlugin
{

	@Inject
	private VitalMagicConfig config;

	public int tick_delay = 0;
	public int tick_count = 0;

	public boolean tick_delay_begin = false;
	@Override
	protected int loop()
	{
		if(Bank.isOpen()) {

			return 2000;
		}

		if(tick_delay_begin) {

			if(tick_count < tick_delay)
			{
				return 1;
			}
			else {

				var min = config.minimumDelay();
				var max = config.maximumDelay();

				if(min > 600) { min = 600; }
				if(max > 600) { max = 600; }

				tick_delay_begin = false;

				return Rand.nextInt(min, max);
			}
		}

		if(config.alchemyEnabled() && config.alchemyItem() != 0 && Inventory.contains(config.alchemyItem()) && SpellBook.Standard.HIGH_LEVEL_ALCHEMY.canCast())
		{
			if (Movement.isWalking() && !config.alchemyWhileMoving())
			{
				return 1;
			}

			if(config.enchantBolt())
			{
				if(Dialog.isOpen()) {
					Keyboard.sendSpace();
					Time.sleep(Rand.nextInt(config.enchantMin(), config.enchantMax()));
				}
				else {
					if(Tabs.isOpen(Tab.MAGIC)) {
						Widget widget = Widgets.get(WidgetInfo.SPELL_ENCHANT_CROSSBOW_BOLT);
						if (widget != null)
						{
							widget.interact(0);
						}
					}
					else {
						Tabs.openInterface(Tab.MAGIC);
					}

					return Rand.nextInt(150, 300);
				}
			}
			Magic.cast(SpellBook.Standard.HIGH_LEVEL_ALCHEMY, Inventory.getFirst(config.alchemyItem()));

			if(config.teleportAlch()) {

				Time.sleep(Rand.nextInt(config.teleDelayMin(), config.teleDelayMax()));

				if(SpellBook.Standard.TELEPORT_TO_KOUREND.canCast()) { Magic.cast(SpellBook.Standard.TELEPORT_TO_KOUREND); }
				else if(SpellBook.Standard.TELEPORT_TO_APE_ATOLL.canCast()) { Magic.cast(SpellBook.Standard.TELEPORT_TO_APE_ATOLL); }
				else if(SpellBook.Standard.TROLLHEIM_TELEPORT.canCast()) { Magic.cast(SpellBook.Standard.TROLLHEIM_TELEPORT); }
				else if(SpellBook.Standard.WATCHTOWER_TELEPORT.canCast()) { Magic.cast(SpellBook.Standard.WATCHTOWER_TELEPORT); }
				else if(SpellBook.Standard.ARDOUGNE_TELEPORT.canCast()) { Magic.cast(SpellBook.Standard.ARDOUGNE_TELEPORT); }
				else if(SpellBook.Standard.CAMELOT_TELEPORT.canCast()) { Magic.cast(SpellBook.Standard.CAMELOT_TELEPORT); }
				else if(SpellBook.Standard.TELEPORT_TO_HOUSE.canCast()) { Magic.cast(SpellBook.Standard.TELEPORT_TO_HOUSE); }
				else if(SpellBook.Standard.FALADOR_TELEPORT.canCast()) { Magic.cast(SpellBook.Standard.FALADOR_TELEPORT); }
				else if(SpellBook.Standard.LUMBRIDGE_TELEPORT.canCast()) { Magic.cast(SpellBook.Standard.LUMBRIDGE_TELEPORT); }
				else if(SpellBook.Standard.VARROCK_TELEPORT.canCast()) { Magic.cast(SpellBook.Standard.VARROCK_TELEPORT); }
			}

			tick_delay_begin = true;
		}

		return 1;
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		tick_count++;

		if(!tick_delay_begin)
		{
			tick_delay = tick_count + Rand.nextInt(config.tickMinDelay(), config.tickMaxDelay());
		}
	}

	@Provides
	VitalMagicConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalMagicConfig.class);
	}
}
