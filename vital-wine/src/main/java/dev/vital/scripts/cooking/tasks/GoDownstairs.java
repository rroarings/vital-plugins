package dev.vital.scripts.cooking.tasks;

import dev.vital.scripts.cooking.VitalWineConfig;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class GoDownstairs implements ScriptTask
{
	private static final WorldArea COOKING_GUILD_1 = new WorldArea(3138, 3444, 7, 7, 1);
	private static final WorldArea COOKING_GUILD_2 = new WorldArea(3138, 3444, 7, 7, 2);

	VitalWineConfig config;

	public GoDownstairs(VitalWineConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {

		Player local = LocalPlayer.get();
		return local != null && Inventory.isFull() && (COOKING_GUILD_1.contains(local) || COOKING_GUILD_2.contains(local));
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return -1;
		}

		TileObjects.getNearest("Staircase").interact("Climb-down");

		return -3;
	}
}
