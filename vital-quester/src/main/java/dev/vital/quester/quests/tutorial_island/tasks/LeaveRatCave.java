package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class LeaveRatCave implements ScriptTask
{
	VitalQuesterConfig config;

	public LeaveRatCave(VitalQuesterConfig config)
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
				return widget_child.getText().contains("You have completed the tasks here");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		TileObjects.getNearest("Ladder").interact("Climb-up");

		return -5;
	}
}
