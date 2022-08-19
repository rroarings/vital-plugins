package dev.vital.quester.quests.romeo_and_juliet.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class TalkToFatherLawrence implements ScriptTask
{
	private final WorldPoint father_lawrence_point = new WorldPoint(3254, 3479, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_father_lawrence = new DialogTask("Father Lawrence", father_lawrence_point,
			(String) null);

	public TalkToFatherLawrence(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getVarp(QuestVarPlayer.QUEST_ROMEO_AND_JULIET.getId()) == 30;
	}

	@Override
	public int execute()
	{

		if (!talk_to_father_lawrence.taskCompleted())
		{
			return talk_to_father_lawrence.execute();
		}

		return -1;
	}
}
