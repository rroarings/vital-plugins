package dev.unethicalite.scripts.cooking.tasks;

import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.commons.Time;
import dev.unethicalite.api.entities.TileObjects;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class FillJugs implements ScriptTask
{
	private static final WorldArea COOKING_GUILD_0 = new WorldArea(3138, 3444, 7, 7, 0);
	@Override
	public boolean validate() {

		Player local = LocalPlayer.getEntity();
		return local != null && Inventory.contains(ItemID.JUG) &&Inventory.contains(ItemID.GRAPES) && COOKING_GUILD_0.contains(local);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.getEntity();

		if (local.isAnimating() || Movement.isWalking()) {

			return 1000;
		}

		Item jug = Inventory.getFirst(ItemID.JUG);
		jug.useOn(TileObjects.getNearest("Sink"));

		Time.sleep(Rand.nextInt(12000, 30000));

		return 2000;
	}
}
