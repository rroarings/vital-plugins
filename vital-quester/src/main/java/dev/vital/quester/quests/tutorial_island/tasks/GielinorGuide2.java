package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class GielinorGuide2 implements ScriptTask
{
	private final WorldPoint gielinor_guide_point = new WorldPoint(3094, 3107, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_guide = new DialogTask("Gielinor Guide", gielinor_guide_point, (String) null);

	public GielinorGuide2(VitalQuesterConfig config)
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
				return widget_child.getText().contains("On the side panel,");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!talk_to_guide.taskCompleted())
		{
			return talk_to_guide.execute();
		}

		return -2;
	}
}
