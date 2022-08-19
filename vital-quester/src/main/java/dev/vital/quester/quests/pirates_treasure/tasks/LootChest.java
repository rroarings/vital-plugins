package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class LootChest implements ScriptTask
{
	private final WorldPoint rum_point = new WorldPoint(3219, 3395, 1);

	VitalQuesterConfig config;
	BasicTask loot_chest = new BasicTask(() ->
	{
		if (Inventory.contains(ItemID.PIRATE_MESSAGE))
		{
			Inventory.getFirst(ItemID.PIRATE_MESSAGE).interact("Read");
			return 0;
		}

		if (!LocalPlayer.get().getWorldLocation().equals(new WorldPoint(3219, 3395, 1)))
		{
			if (!Movement.isWalking())
			{
				Movement.walkTo(rum_point);
			}
		}
		else
		{

			Inventory.getFirst(ItemID.CHEST_KEY).useOn(TileObjects.getNearest("Chest"));

			return -5;
		}

		return -1;
	});

	public LootChest(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return !loot_chest.taskCompleted();
	}

	@Override
	public int execute()
	{

		return loot_chest.execute();
	}
}
