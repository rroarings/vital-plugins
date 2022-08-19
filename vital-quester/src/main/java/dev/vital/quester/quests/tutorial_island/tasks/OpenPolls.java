package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class OpenPolls implements ScriptTask
{
	VitalQuesterConfig config;

	public OpenPolls(VitalQuesterConfig config)
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
				return widget_child.getText().contains("This is your bank");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		TileObjects.getNearest("Poll booth").interact("Use");

		return -5;
	}
}
