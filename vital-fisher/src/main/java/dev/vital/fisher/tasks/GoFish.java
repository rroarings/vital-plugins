package dev.vital.fisher.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.entities.NPCs;
import dev.unethicalite.api.entities.TileObjects;
import dev.unethicalite.api.items.Bank;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class GoFish implements ScriptTask {
	private static final WorldArea PORT_PISC_BANK = new WorldArea(1800, 3787, 4, 10, 0);
	@Override
	public boolean validate() {

		Player local = LocalPlayer.getEntity();

		return local != null && Inventory.contains(ItemID.FISHING_ROD) && Inventory.contains(ItemID.SANDWORMS);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.getEntity();

		if (local.isAnimating() || Movement.isWalking()) {

			return Rand.nextInt(2000, 3000);
		}

		if(local.getAnimation() == 623) {

			return Rand.nextInt(5000, 200000);
		}

		var fishspot = NPCs.getNearest(6825);
		if(fishspot == null) {
			Movement.walkTo(WorldLocation.PISCARILIUS_ANGLERFISH.getWorldArea().getCenter());
		}
		else {

			fishspot.interact("Bait");
		}

		return Rand.nextInt(400, 1200);
	}
}
