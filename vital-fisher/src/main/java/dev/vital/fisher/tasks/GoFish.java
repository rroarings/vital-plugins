package dev.vital.fisher.tasks;

import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;
import net.runelite.api.Player;

public class GoFish implements ScriptTask {

	@Override
	public boolean validate() {

		return Inventory.contains(ItemID.FISHING_ROD) && Inventory.contains(ItemID.SANDWORMS);
	}

	@Override
	public int execute() {

		var fishspot = NPCs.getNearest(6825);
		if(fishspot == null) {

			Movement.walkTo(WorldLocation.PISCARILIUS_ANGLERFISH.getWorldArea());
		}
		else {

			if(!LocalPlayer.get().isAnimating())
			{
				fishspot.interact("Bait");

				return Rand.nextInt(5000, 100000);
			}

		}

		return -3;
	}
}
