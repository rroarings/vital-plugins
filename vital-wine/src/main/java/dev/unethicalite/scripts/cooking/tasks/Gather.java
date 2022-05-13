package dev.unethicalite.scripts.cooking.tasks;

import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.commons.Time;
import dev.unethicalite.api.entities.TileItems;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import net.runelite.api.Player;
import net.runelite.api.TileItem;
import net.runelite.api.coords.WorldArea;

public class Gather implements ScriptTask
{
	private static final WorldArea COOKING_GUILD_2 = new WorldArea(3138, 3444, 10, 10, 2);
	private static int waitfucker = 0;
	@Override
	public boolean validate() {

		Player local = LocalPlayer.getEntity();
		return local != null && !Inventory.isFull() && COOKING_GUILD_2.contains(local);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.getEntity();

		if (local.isAnimating() || Movement.isWalking()) {

			return 1500;
		}

		TileItem item = TileItems.getNearest("Grapes", "Jug");
		if(item != null) {

			waitfucker++;

			item.interact("Take");

			Time.sleep(Rand.nextInt(4000, 4200));
		}

		if(waitfucker == 2 && Inventory.getFreeSlots() > 1) {

			Time.sleep(Rand.nextInt(58500, 62500));

			waitfucker = 0;
		}

		return 1000;
	}
}
