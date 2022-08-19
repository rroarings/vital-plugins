package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;

public class GetEmeraldKey implements ScriptTask
{
	WorldPoint piano_point = new WorldPoint(1645, 4843, 0);
	WorldPoint wall_point = new WorldPoint(1649, 4831, 0);
	WorldPoint shelves_point = new WorldPoint(1646, 4827, 0);
	WorldPoint door_point = new WorldPoint(1634, 4835, 0);
	VitalQuesterConfig config;
	BasicTask get_emerald_key = new BasicTask(() ->
	{
		if (Inventory.contains(ItemID.EMERALD_KEY_21054))
		{
			return 0;
		}

		var piano = TileObjects.getNearest(29658);
		if (piano.hasAction("Search"))
		{
			piano.interact("Search");
			return -5;
		}

		return -1;
	});
	BasicTask climb_wall = new BasicTask(() ->
	{
		if (Tools.interactWith("Damaged wall", "Climb", wall_point, Tools.EntityType.TILE_OBJECT) == -5)
		{
			return 0;
		}

		return -5;
	});
	BasicTask open_door = new BasicTask(() ->
	{

		if (Tools.interactWith(30116, "Open", shelves_point, Tools.EntityType.TILE_OBJECT) == -5)
		{
			return 0;
		}

		return -5;
	});
	BasicTask open_door2 = new BasicTask(() ->
	{

		if (Tools.interactWith(30117, "Open", door_point, Tools.EntityType.TILE_OBJECT) == -5)
		{
			return 0;
		}

		return -5;
	});

	public GetEmeraldKey(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 80;
	}

	@Override
	public int execute()
	{

		if (!get_emerald_key.taskCompleted())
		{
			return get_emerald_key.execute();
		}
		else if (!climb_wall.taskCompleted())
		{
			return climb_wall.execute();
		}
		else if (!open_door.taskCompleted())
		{
			return open_door.execute();
		}
		else if (!open_door2.taskCompleted())
		{
			return open_door2.execute();
		}

		return -1;
	}
}
