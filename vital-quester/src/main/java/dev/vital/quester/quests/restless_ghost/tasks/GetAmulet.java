package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class GetAmulet implements ScriptTask
{
	private final WorldPoint father_urhney_point = new WorldPoint(3147, 3175, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_urhney = new DialogTask("Father Urhney", father_urhney_point,
			"Father Aereck sent me to talk to you.", "He's got a ghost haunting his graveyard.");

	public GetAmulet(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getVarp(QuestVarPlayer.QUEST_THE_RESTLESS_GHOST.getId()) == 1;
	}

	@Override
	public int execute()
	{
		if (!talk_to_urhney.taskCompleted())
		{
			return talk_to_urhney.execute();
		}

		return -1;
	}
}
