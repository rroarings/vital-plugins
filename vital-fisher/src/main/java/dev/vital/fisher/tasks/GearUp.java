package dev.vital.fisher.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.entities.TileObjects;
import dev.unethicalite.api.items.Bank;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class GearUp implements ScriptTask {
	private static final WorldArea PORT_PISC_BANK = new WorldArea(1800, 3787, 4, 10, 0);
	@Override
	public boolean validate() {

		Player local = LocalPlayer.getEntity();

		return local != null && (!Inventory.contains(ItemID.SANDWORMS) || !Inventory.contains(ItemID.FISHING_ROD)) || (Inventory.isFull() && Inventory.contains(ItemID.RAW_ANGLERFISH));
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.getEntity();

		if (local.isAnimating() || Movement.isWalking()) {

			return 1000;
		}

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

				Bank.withdraw(ItemID.FISHING_ROD, 1, Bank.WithdrawMode.ITEM);
			}
			else if(!Inventory.contains(ItemID.SANDWORMS)) {

				Bank.withdraw(ItemID.SANDWORMS, 9999, Bank.WithdrawMode.ITEM);
			}
			else {

				Bank.deposit(ItemID.RAW_ANGLERFISH, 26);
			}
		}
		return Rand.nextInt(400, 1200);
	}
}
