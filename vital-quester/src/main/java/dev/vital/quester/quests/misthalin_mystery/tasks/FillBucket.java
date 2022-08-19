package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;

public class FillBucket implements ScriptTask
{
	private final WorldPoint bucket_point = new WorldPoint(1619, 4816, 0);

	VitalQuesterConfig config;
	BasicTask work_barrel = new BasicTask(() ->
	{
		if (Inventory.contains(ItemID.BUCKET))
		{

			Inventory.getFirst(ItemID.BUCKET).useOn(TileObjects.getNearest("A barrel of rainwater"));
		}
		else if (Inventory.contains(ItemID.BUCKET_OF_WATER))
		{

			TileObjects.getNearest("A barrel of rainwater").interact("Search");
			return 0;
		}

		return -5;
	});

	public FillBucket(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 20;
	}

	@Override
	public int execute()
	{

		if (!work_barrel.taskCompleted())
		{
			return work_barrel.execute();
		}

		return -1;
	}
}
