package dev.vital.fisher.tasks;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class GearUp implements ScriptTask {

	private static final WorldArea PORT_PISC_BANK = new WorldArea(1800, 3787, 4, 10, 0);

	@Override
	public boolean validate() { return !Inventory.contains(ItemID.SANDWORMS) || !Inventory.contains(ItemID.FISHING_ROD) || Inventory.isFull(); }

	@Override
	public int execute() {

		if(!Bank.isOpen()) {

			var booth = TileObjects.getNearest("Bank booth");
			if (booth != null) {

				booth.interact("Bank");
			}
			else {

				Movement.walkTo(PORT_PISC_BANK.getCenter());
			}
		}
		else  {

			if(!Inventory.contains(ItemID.FISHING_ROD)) {

				if(Bank.contains(ItemID.FISHING_ROD)) {

					Bank.withdraw(ItemID.FISHING_ROD, 1, Bank.WithdrawMode.ITEM);
				}
				else {
					Game.logout();
				}
			}
			else if(!Inventory.contains(ItemID.SANDWORMS)) {

				if(Bank.contains(ItemID.SANDWORMS)) {

					Bank.withdraw(ItemID.SANDWORMS, 9999, Bank.WithdrawMode.ITEM);
				}
				else {

					Game.logout();
				}
			}
			else {

				Bank.depositAllExcept(ItemID.SANDWORMS, ItemID.FISHING_ROD);
			}
		}
		return -2;
	}
}
