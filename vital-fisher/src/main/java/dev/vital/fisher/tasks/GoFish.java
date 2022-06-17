package dev.vital.fisher.tasks;

import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class GoFish implements ScriptTask {
	private static final WorldArea PORT_PISC_BANK = new WorldArea(1800, 3787, 4, 10, 0);
	@Override
	public boolean validate() {

		Player local = LocalPlayer.get();

		return local != null && Inventory.contains(ItemID.FISHING_ROD) && Inventory.contains(ItemID.SANDWORMS);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return Rand.nextInt(2000, 3000);
		}

		if(local.getAnimation() == 623) {

			return Rand.nextInt(5000, 200000);
		}

		var fishspot = NPCs.getNearest(6825);
		if(fishspot == null) {
			Movement.walkTo(WorldLocation.PISCARILIUS_ANGLERFISH.getWorldArea().getRandom());
		}
		else {

			fishspot.interact("Bait");
		}

		return Rand.nextInt(400, 1200);
	}
}
