package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.CameraTask;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.widgets.Widgets;

public class TalkToMiningInstructor implements ScriptTask
{
	private final WorldPoint quest_guide_point = new WorldPoint(3080, 9505, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_mining_instructor = new DialogTask("Mining Instructor", quest_guide_point, (String) null);
	CameraTask camera_task = new CameraTask(Rand.nextInt(0, 4));

	public TalkToMiningInstructor(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		var widget = Widgets.get(263, 1);
		if (widget != null)
		{
			var widget_child = widget.getChild(0);
			if (widget_child != null)
			{
				return widget_child.getText().contains("Next let's get you a weapon");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!camera_task.taskCompleted())
		{
			camera_task.moveRight();
			return -1;
		}

		if (!talk_to_mining_instructor.taskCompleted())
		{
			return talk_to_mining_instructor.execute();
		}
		return -1;
	}
}
