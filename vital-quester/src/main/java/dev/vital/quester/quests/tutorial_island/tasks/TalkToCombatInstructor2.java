package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToCombatInstructor2 implements ScriptTask
{
	private final WorldPoint combat_instructor_point = new WorldPoint(3106, 9508, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_combat_instructor = new DialogTask("Combat Instructor", combat_instructor_point, (String) null);

	public TalkToCombatInstructor2(VitalQuesterConfig config)
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
				return widget_child.getText().contains("You're now holding your dagger");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!talk_to_combat_instructor.taskCompleted())
		{
			return talk_to_combat_instructor.execute();
		}

		return -1;
	}
}
