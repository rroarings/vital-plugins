package dev.vital.quester.handlers;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.widgets.Widgets;

import static net.runelite.api.widgets.WidgetInfo.QUEST_COMPLETED;

public class HandleQuestComplete implements ScriptTask
{
	VitalQuesterConfig config;

	public HandleQuestComplete(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		var quest_completed_x = Widgets.get(QUEST_COMPLETED);
		return quest_completed_x != null;
	}

	@Override
	public int execute()
	{

		Time.sleepTicks(3);
		Widgets.get(153, 16).interact("Close");

		return -1;
	}
}
