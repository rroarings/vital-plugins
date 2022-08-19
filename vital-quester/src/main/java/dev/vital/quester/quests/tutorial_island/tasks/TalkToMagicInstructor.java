package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToMagicInstructor implements ScriptTask
{
	private final WorldPoint magic_instructor_point = new WorldPoint(3141, 3089, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_magic_instructor = new DialogTask("Magic Instructor", magic_instructor_point, (String) null);

	public TalkToMagicInstructor(VitalQuesterConfig config)
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
				return widget_child.getText().contains("Follow the path to the wizard's house");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!talk_to_magic_instructor.taskCompleted())
		{
			return talk_to_magic_instructor.execute();
		}
		return -1;
	}
}
