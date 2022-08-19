package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class TalkToFred implements ScriptTask
{
	private final WorldPoint farmer_fred_point = new WorldPoint(3190, 3273, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_fred = new DialogTask("Fred the Farmer", farmer_fred_point,
			"Yes.", "I'm looking for a quest.", "Yes, okay. I can do that.");

	public TalkToFred(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Inventory.getCount(false, ItemID.BALL_OF_WOOL) >= 20;
	}

	@Override
	public int execute()
	{

		if (!talk_to_fred.taskCompleted())
		{

			return talk_to_fred.execute();
		}

		return -1;
	}
}
