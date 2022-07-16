package dev.vital.sandcrab.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.sandcrab.VitalSandCrabConfig;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;

public class CrabClawIsle implements ScriptTask {

	VitalSandCrabConfig config;

	public CrabClawIsle(VitalSandCrabConfig config) { this.config = config; }

	@Override
	public boolean validate() { return !WorldLocation.CRAB_CLAW_ISLE.getWorldArea().contains(LocalPlayer.get()); }

	@Override
	public int execute() {

		var sandicrahb = NPCs.getNearest("Sandicrabh");
		if(sandicrahb != null && Reachable.isInteractable(sandicrahb)) {

			if(Inventory.getCount(true, ItemID.COINS_995) >= 10000) {

				sandicrahb.interact("Travel");
			}
		}
		else {

			Movement.walkTo(new WorldPoint(1784, 3458, 0));
		}
		return -1;
	}
}