package dev.vital.sandcrab.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.sandcrab.VitalSandCrabConfig;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;

public class CrabClawIsle implements ScriptTask {

	VitalSandCrabConfig config;

	WorldPoint chest_location = new WorldPoint(1718, 3465, 0);

	public CrabClawIsle(VitalSandCrabConfig config) { this.config = config; }

	@Override
	public boolean validate() { return !WorldLocation.CRAB_CLAW_ISLE.getWorldArea().contains(LocalPlayer.get()); }

	@Override
	public int execute() {

		if(Inventory.getCount(true, ItemID.COINS_995) >= 10000) {
			Movement.walkTo(WorldLocation.CRAB_CLAW_ISLE.getWorldArea());
		}
		else {

			if (Bank.isOpen()) {
				if(Inventory.contains(ItemID.COINS_995) && Inventory.getCount(true, ItemID.COINS_995) >= 10000) {
					Bank.withdraw(ItemID.COINS_995,10000, Bank.WithdrawMode.DEFAULT);
				}
			}
			else {
				var chest = TileObjects.getFirstAt(chest_location, x -> x.getName().equals("Bank chest"));
				if(chest != null && Reachable.isInteractable(chest)) {
					chest.interact("Use");
				}
				else {
					Movement.walkTo(chest_location);
				}
			}

		}

		return -1;
	}
}