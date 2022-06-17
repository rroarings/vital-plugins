package dev.vital.scripts.cooking.tasks;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import dev.vital.scripts.cooking.VitalWineConfig;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class GoUpstairs implements ScriptTask
{
	private static final WorldArea COOKING_GUILD_0 = new WorldArea(3138, 3444, 7, 7, 0);
	private static final WorldArea COOKING_GUILD_1 = new WorldArea(3138, 3444, 7, 7, 1);

	VitalWineConfig config;

	public GoUpstairs(VitalWineConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {

		Player local = LocalPlayer.get();
		return local != null && Inventory.isEmpty() && (COOKING_GUILD_0.contains(local) || COOKING_GUILD_1.contains(local));
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return 1000;
		}

		TileObjects.getNearest("Staircase").interact("Climb-up");

		return Rand.nextInt(1200, 2500);
	}
}
