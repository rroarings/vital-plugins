package dev.vital.quester.quests.restless_ghost;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.restless_ghost.tasks.EquipAmulet;
import dev.vital.quester.quests.restless_ghost.tasks.GetAmulet;
import dev.vital.quester.quests.restless_ghost.tasks.GetQuest;
import dev.vital.quester.quests.restless_ghost.tasks.GetSkull;
import dev.vital.quester.quests.restless_ghost.tasks.ReturnSkull;
import dev.vital.quester.quests.restless_ghost.tasks.TalkToGhost;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class RestlessGhost implements ScriptTask
{
	static List<ScriptTask> tasks = new ArrayList<>();
	VitalQuesterConfig config;

	public RestlessGhost(VitalQuesterConfig config)
	{
		this.config = config;

		tasks.clear();

		tasks.add(new GetQuest(config));
		tasks.add(new GetAmulet(config));
		tasks.add(new EquipAmulet(config));
		tasks.add(new TalkToGhost(config));
		tasks.add(new GetSkull(config));
		tasks.add(new ReturnSkull(config));
	}

	@Override
	public boolean validate()
	{
		return (config.currentQuest().equals(QuestList.THE_RESTLESS_GHOST) || config.automaticOptimal()) && Quests.getState(Quest.THE_RESTLESS_GHOST) != QuestState.FINISHED;
	}

	@Override
	public int execute()
	{

		for (ScriptTask task : tasks)
		{
			if (task.validate())
			{
				int sleep = task.execute();
				if (task.blocking())
				{
					return sleep;
				}
			}
		}

		return -1;
	}
}
