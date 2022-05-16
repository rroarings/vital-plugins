package dev.vital.alchemy.tasks;

import com.google.inject.Inject;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.items.Bank;
import dev.unethicalite.api.items.Equipment;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.magic.Magic;
import dev.unethicalite.api.magic.SpellBook;
import dev.unethicalite.api.movement.Movement;
import dev.vital.alchemy.VitalAlchemyConfig;
import net.runelite.api.Item;
import net.runelite.api.ItemID;

public class HighAlch implements ScriptTask
{
	@Inject
	private VitalAlchemyConfig config;

	@Override
	public boolean validate() {

		return Inventory.contains("Nature rune") && Inventory.contains(config.Item()) && Equipment.contains(ItemID.STAFF_OF_FIRE);
	}

	@Override
	public int execute() {

		if (Movement.isWalking()) {

			return Rand.nextInt(800, 1000);
		}

		if(Bank.isOpen()) {

			Bank.close();
		}
		else
		{
			Item noted_id = Inventory.getFirst(config.Item());

			if (!Magic.isSpellSelected(SpellBook.Standard.HIGH_LEVEL_ALCHEMY))
			{
				Magic.selectSpell(SpellBook.Standard.HIGH_LEVEL_ALCHEMY);
			}
			else
			{
				Magic.cast(SpellBook.Standard.HIGH_LEVEL_ALCHEMY, noted_id);

				if (config.postCastDelayMin() != 0 || config.postCastDelayMax() != 0)
				{

					return Rand.nextInt(config.postCastDelayMin(), config.postCastDelayMax());
				}
			}
		}

		return Rand.nextInt(600, 1200);
	}
}
