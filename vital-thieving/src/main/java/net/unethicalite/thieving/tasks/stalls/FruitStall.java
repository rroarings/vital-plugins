package net.unethicalite.thieving.tasks.stalls;

import net.runelite.api.ItemID;
import net.runelite.api.World;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.thieving.ThievingType;
import net.unethicalite.thieving.VitalThievingConfig;
import net.unethicalite.thieving.tasks.ScriptTask;

public class FruitStall implements ScriptTask
{
	private static final WorldArea FRUIT_STALLS = new WorldArea(1796, 3606, 5, 5, 0);

	VitalThievingConfig config;

	public FruitStall(VitalThievingConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return config.thievingType().equals(ThievingType.STALL_FRUIT);
	}

	@Override
	public int execute()
	{

		if (Inventory.isFull() && config.dropItems())
		{

			for (var item : Inventory.getAll(ItemID.COOKING_APPLE, ItemID.STRANGE_FRUIT,
					ItemID.BANANA, ItemID.LEMON,
					ItemID.LIME, ItemID.GOLOVANOVA_FRUIT_TOP, ItemID.JANGERBERRIES, ItemID.PINEAPPLE,
					ItemID.REDBERRIES, ItemID.STRAWBERRY, ItemID.PAPAYA_FRUIT))
			{

				item.interact("Drop");
				Time.sleep(180, 230);
			}
		}
		else
		{
			var fruit_Stall = TileObjects.getNearest("Fruit Stall");
			if (fruit_Stall != null && Reachable.isInteractable(fruit_Stall) && fruit_Stall.distanceTo(LocalPlayer.get()) < 5)
			{

				if (Players.getNearest(x -> x != LocalPlayer.get() && FRUIT_STALLS.contains(x)) != null)
				{

					Worlds.hopTo(Worlds.getRandom(World::isMembers));
					return -12;
				}

				fruit_Stall.interact("Steal-from");

				Time.sleep(1500);
			}
			else
			{

				Movement.walkTo(FRUIT_STALLS);
			}
		}

		return -1;
	}
}
