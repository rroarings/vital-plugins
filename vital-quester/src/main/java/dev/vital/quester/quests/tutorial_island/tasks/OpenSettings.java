package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.CameraTask;
import net.unethicalite.api.widgets.Widgets;

public class OpenSettings implements ScriptTask
{
	VitalQuesterConfig config;
	CameraTask camera_task = new CameraTask(4);

	public OpenSettings(VitalQuesterConfig config)
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
				return widget_child.getText().contains("Please click on the flashing spanner icon found at the bottom right of your screen.");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!camera_task.taskCompleted())
		{
			camera_task.moveLeft();
			return -2;
		}


		var widget = Widgets.get(164, 40);
		if (widget != null)
		{

			widget.interact("Settings");
		}

		return -2;
	}
}
