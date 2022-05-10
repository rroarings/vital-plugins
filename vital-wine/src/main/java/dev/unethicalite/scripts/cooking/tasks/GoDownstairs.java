package dev.unethicalite.scripts.cooking.tasks;

import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.commons.Time;
import dev.unethicalite.api.entities.TileObjects;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class GoDownstairs implements ScriptTask
{
	private static final WorldArea COOKING_GUILD_1 = new WorldArea(3138, 3444, 7, 7, 1);
	private static final WorldArea COOKING_GUILD_2 = new WorldArea(3138, 3444, 7, 7, 2);

	@Override
	public boolean validate() {

		Player local = LocalPlayer.getEntity();
		return local != null && Inventory.isFull() && (COOKING_GUILD_1.contains(local) || COOKING_GUILD_2.contains(local));
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.getEntity();

		if (local.isAnimating() || Movement.isWalking()) {

			return 1000;
		}

		TileObjects.getNearest("Staircase").interact("Climb-down");

		return Rand.nextInt(1200, 2500);
	}
}
