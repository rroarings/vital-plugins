package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.CameraTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class LeaveChefsRoom implements ScriptTask
{
	VitalQuesterConfig config;
	CameraTask camera_task = new CameraTask(Rand.nextInt(0, 4));

	public LeaveChefsRoom(VitalQuesterConfig config)
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
				return widget_child.getText().contains("Well done! You've baked your first loaf");
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
			return -2;
		}

		TileObjects.getFirstAt(new WorldPoint(3072, 3090, 0), "Door").interact("Open");

		return -5;
	}
}
