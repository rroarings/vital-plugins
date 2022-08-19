package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;

public class GetJob2 implements ScriptTask
{
	private final WorldPoint wydin_point = new WorldPoint(3014, 3204, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_wydin = new DialogTask("Wydin", wydin_point, "Can I get a job here?");

	public GetJob2(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return !talk_to_wydin.taskCompleted();
	}

	@Override
	public int execute()
	{

		return talk_to_wydin.execute();
	}
}
