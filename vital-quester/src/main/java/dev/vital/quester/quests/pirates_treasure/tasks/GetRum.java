package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.ObjectItemTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

public class GetRum implements ScriptTask
{
	private final WorldPoint rum_point = new WorldPoint(3010, 3208, 0);

	VitalQuesterConfig config;
	ObjectItemTask get_rum = new ObjectItemTask(2071, ItemID.KARAMJAN_RUM, 1, false, "Search", rum_point);

	public GetRum(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return !get_rum.taskCompleted();
	}

	@Override
	public int execute()
	{

		return get_rum.execute();
	}
}
