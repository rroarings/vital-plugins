package dev.vital.scripts.cooking.tasks;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import dev.vital.scripts.cooking.VitalWineConfig;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class FillJugs implements ScriptTask
{
	private static final WorldArea COOKING_GUILD_0 = new WorldArea(3138, 3444, 7, 7, 0);

	VitalWineConfig config;

	public FillJugs(VitalWineConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {

		return Inventory.contains(ItemID.JUG) &&Inventory.contains(ItemID.GRAPES) && COOKING_GUILD_0.contains(LocalPlayer.get());
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return -1;
		}

		Inventory.getFirst(ItemID.JUG).useOn(TileObjects.getNearest("Sink"));

		Time.sleep(Rand.nextInt(12000, 16000));

		return -1;
	}
}
