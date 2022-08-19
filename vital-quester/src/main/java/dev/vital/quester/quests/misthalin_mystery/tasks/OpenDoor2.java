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
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.QuestVarbits;

public class OpenDoor2 implements ScriptTask
{
	WorldPoint knife_point = new WorldPoint(1637, 4829, 0);
	VitalQuesterConfig config;
	BasicTask open_door = new BasicTask(() ->
	{
		if (Tools.interactWith(30116, "Open", knife_point, Tools.EntityType.TILE_OBJECT) == -5)
		{
			return 0;
		}

		return -5;
	});
	BasicTask get_ruby_key = new BasicTask(() ->
	{
		if (Inventory.contains(ItemID.RUBY_KEY_21053))
		{
			return 0;
		}

		var painting = TileObjects.getNearest(29650);
		if (painting.hasAction("Search"))
		{
			painting.interact("Search");
		}
		else
		{
			if (Reachable.isInteractable(painting))
			{
				Inventory.getFirst(ItemID.KNIFE).useOn(TileObjects.getNearest(29650));
			}
			else
			{
				Movement.walkTo(painting);
			}
		}
		return -5;
	});

	public OpenDoor2(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 45;
	}

	@Override
	public int execute()
	{

		if (!get_ruby_key.taskCompleted())
		{
			return get_ruby_key.execute();
		}
		else if (!open_door.taskCompleted())
		{
			return open_door.execute();
		}

		return -1;
	}
}
