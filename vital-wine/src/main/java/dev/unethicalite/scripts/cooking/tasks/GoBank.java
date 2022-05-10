package dev.unethicalite.scripts.cooking.tasks;

import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.commons.Time;
import dev.unethicalite.api.entities.TileItems;
import dev.unethicalite.api.entities.TileObjects;
import dev.unethicalite.api.items.Bank;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import dev.unethicalite.api.movement.Reachable;
import net.runelite.api.GroundObject;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.TileItem;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

public class GoBank implements ScriptTask
{
	private static final WorldPoint COOKING_GUILD_DOOR = new WorldPoint(3143, 3443, 0);
	private static final WorldArea COOKING_GUILD_0 = new WorldArea(3138, 3444, 7, 7, 0);
	private static final WorldArea VARROCK_WEST_BANK_AREA = new WorldArea(3181, 3434, 5, 10, 0);
	@Override
	public boolean validate() {

		Player local = LocalPlayer.getEntity();
		return local != null
				&& (Inventory.contains(ItemID.JUG_OF_WINE) || Inventory.contains(ItemID.JUG_OF_BAD_WINE));
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.getEntity();

		if (local.isAnimating() || Movement.isWalking()) {

			return 1000;
		}

		if(COOKING_GUILD_0.contains(local)) {

			TileObject door = TileObjects.getFirstAt(COOKING_GUILD_DOOR, x -> x.hasAction("Open"));

			door.interact("Open");

			Time.sleep(Rand.nextInt(1000, 1500));
		}
		else if(!VARROCK_WEST_BANK_AREA.contains(local)){

			Movement.walkTo(VARROCK_WEST_BANK_AREA.getCenter());

			Time.sleep(Rand.nextInt(400, 500));
		}
		else if(!Bank.isOpen()) {

			TileObjects.getNearest("Bank booth").interact("Bank");
		}
		else {

			Bank.depositInventory();

			Time.sleep(Rand.nextInt(300, 500));
		}

		return Rand.nextInt(100, 200);
	}
}
