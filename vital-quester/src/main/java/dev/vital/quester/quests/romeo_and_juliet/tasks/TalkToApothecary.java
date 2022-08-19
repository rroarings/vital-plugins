package dev.vital.quester.quests.romeo_and_juliet.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import dev.vital.quester.tasks.WalkTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class TalkToApothecary implements ScriptTask
{
	private final WorldPoint apothecary_point = new WorldPoint(3194, 3403, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_apothecary = new DialogTask("Apothecary", apothecary_point,
			"Talk about something else.", "Talk about Romeo & Juliet.");
	WalkTask walk_task1 = new WalkTask(new WorldPoint(3283, 3428, 0));

	public TalkToApothecary(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getVarp(QuestVarPlayer.QUEST_ROMEO_AND_JULIET.getId()) == 40;
	}

	@Override
	public int execute()
	{

		if (!walk_task1.taskCompleted())
		{
			return walk_task1.execute();
		}
		else if (!talk_to_apothecary.taskCompleted())
		{
			return talk_to_apothecary.execute();
		}

		return -1;
	}
}
