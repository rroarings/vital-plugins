package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class GielinorGuide implements ScriptTask
{
	private final WorldPoint gielinor_guide_point = new WorldPoint(3094, 3107, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_guide = new DialogTask("Gielinor Guide", gielinor_guide_point, "I am brand new!");

	public GielinorGuide(VitalQuesterConfig config)
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
				return widget_child.getText().contains("Before you begin,");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		return talk_to_guide.execute();
	}
}
