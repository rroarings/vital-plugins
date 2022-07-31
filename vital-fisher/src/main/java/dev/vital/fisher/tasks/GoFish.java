package dev.vital.fisher.tasks;

import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;

public class GoFish implements ScriptTask {

	@Override
	public boolean validate() {

		return Inventory.contains(ItemID.FISHING_ROD) && Inventory.contains(ItemID.SANDWORMS);
	}

	@Override
	public int execute() {

		var an = LocalPlayer.get().getAnimation();
		var fishspot = NPCs.getNearest(6825);
		if(fishspot == null) {

			Movement.walkTo(new WorldPoint(1828, 3775, 0));
		}
		else if(an != 623 && an != 622) {

			fishspot.interact("Bait");
			return -3;
		}
		else if(LocalPlayer.get().getAnimation() == 623){

			return Rand.nextInt(1000 * 30, 1000 * 120);
		}

		return -3;
	}
}
