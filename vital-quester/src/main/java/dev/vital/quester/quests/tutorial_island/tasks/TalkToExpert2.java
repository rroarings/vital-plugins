package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToExpert2 implements ScriptTask
{
	private final WorldPoint survival_expert_point = new WorldPoint(3103, 3095, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_expert = new DialogTask("Survival Expert", survival_expert_point, (String) null);

	public TalkToExpert2(VitalQuesterConfig config)
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
				return widget_child.getText().contains("On this menu you can view your skills");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!talk_to_expert.taskCompleted())
		{
			return talk_to_expert.execute();
		}
		return -1;
	}
}
