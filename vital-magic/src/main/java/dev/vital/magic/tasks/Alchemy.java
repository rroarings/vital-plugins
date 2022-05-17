package dev.vital.magic.tasks;

import com.google.inject.Inject;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.magic.Magic;
import dev.unethicalite.api.magic.SpellBook;
import dev.unethicalite.api.movement.Movement;
import dev.vital.magic.VitalMagicConfig;
import net.runelite.api.ItemID;

public class Alchemy implements ScriptTask
{
	@Inject
	private VitalMagicConfig config;

	@Override
	public boolean validate() {

		return config.alchemyEnabled() && config.alchemyItem() != 0 && Inventory.contains(config.alchemyItem()) && SpellBook.Standard.HIGH_LEVEL_ALCHEMY.canCast();
	}

	@Override
	public int execute() {

		if (Movement.isWalking() && !config.alchemyWhileMoving()) {

			return 1000;
		}

		if(Magic.isSpellSelected(SpellBook.Standard.HIGH_LEVEL_ALCHEMY)) {

			Magic.cast(SpellBook.Standard.HIGH_LEVEL_ALCHEMY, Inventory.getFirst(config.alchemyItem()));

			return Rand.nextInt(config.minimumDelay(), config.maximumDelay());
		}
		else {

			Magic.selectSpell(SpellBook.Standard.HIGH_LEVEL_ALCHEMY);

			return Rand.nextInt(550, 1400);
		}
	}
}
