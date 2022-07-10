package dev.vital.scripts.cooking.tasks;

import dev.vital.scripts.cooking.VitalWineConfig;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.Player;
import net.runelite.api.TileItem;
import net.runelite.api.coords.WorldArea;

public class Gather implements ScriptTask
{
	private static final WorldArea COOKING_GUILD_2 = new WorldArea(3138, 3444, 10, 10, 2);
	private static int waitfucker = 0;

	VitalWineConfig config;

	public Gather(VitalWineConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {

		Player local = LocalPlayer.get();
		return local != null && !Inventory.isFull() && COOKING_GUILD_2.contains(local);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return -1;
		}

		TileItem item = TileItems.getNearest("Grapes", "Jug");
		if(item != null) {

			waitfucker++;

			item.interact("Take");

			return -5;
		}

		if(waitfucker == 2 && Inventory.getFreeSlots() > 1) {

			Time.sleep(Rand.nextInt(60000, 62500));

			waitfucker = 0;
		}

		return -1;
	}
}
