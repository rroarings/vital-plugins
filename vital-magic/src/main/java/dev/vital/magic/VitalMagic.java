package dev.vital.magic;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.Spell;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-magic", enabledByDefault = false)
@Extension
@Slf4j
public class VitalMagic extends LoopedPlugin
{
	@Inject
	private VitalMagicConfig config;

	Spell getSpell(Spell... spells)
	{

		Spell wanted_spell = null;

		for (var spell : spells)
		{

			if (spell.canCast())
			{

				wanted_spell = spell;
				break;
			}
		}

		return wanted_spell;
	}

	@Override
	protected int loop()
	{
		if (!Game.isLoggedIn() || Bank.isOpen() || Dialog.isOpen() || Dialog.isViewingOptions() || Dialog.canContinue() || Shop.isOpen())
		{

			return -5;
		}

		Time.sleepTicks(Rand.nextInt(config.tickMinDelay(), config.tickMaxDelay()));

		/*if(config.enchantBolt())
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
		}*/

		if (config.alchemyEnabled() && config.alchemyItem() != 0 && Inventory.contains(config.alchemyItem()))
		{
			if (Movement.isWalking() && !config.alchemyWhileMoving())
			{
				return -1;
			}

			if (SpellBook.Standard.HIGH_LEVEL_ALCHEMY.canCast() && Skills.getLevel(Skill.MAGIC) >= 55)
			{

				Magic.cast(SpellBook.Standard.HIGH_LEVEL_ALCHEMY, Inventory.getFirst(config.alchemyItem()));
			}
			else if (SpellBook.Standard.LOW_LEVEL_ALCHEMY.canCast() && Skills.getLevel(Skill.MAGIC) < 55)
			{

				Magic.cast(SpellBook.Standard.LOW_LEVEL_ALCHEMY, Inventory.getFirst(config.alchemyItem()));
			}
		}

		if (config.teleportAlch())
		{

			Time.sleep(Rand.nextInt(config.teleDelayMin(), config.teleDelayMax()));

			var spell = getSpell(SpellBook.Standard.TELEPORT_TO_KOUREND,
					SpellBook.Standard.WATCHTOWER_TELEPORT, SpellBook.Standard.ARDOUGNE_TELEPORT,
					SpellBook.Standard.CAMELOT_TELEPORT, SpellBook.Standard.FALADOR_TELEPORT,
					SpellBook.Standard.LUMBRIDGE_TELEPORT, SpellBook.Standard.VARROCK_TELEPORT);
			if (spell != null)
			{
				spell.cast();
			}
		}

		return 1;
	}


	@Provides
	VitalMagicConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalMagicConfig.class);
	}
}
