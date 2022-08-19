package dev.vital.quester.quests.enter_the_abyss;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.enter_the_abyss.tasks.TalkToAubury;
import dev.vital.quester.quests.enter_the_abyss.tasks.TalkToCromperty;
import dev.vital.quester.quests.enter_the_abyss.tasks.TalkToMage;
import dev.vital.quester.quests.enter_the_abyss.tasks.TalkToSedridor;
import dev.vital.quester.quests.x_marks_the_spot.tasks.StartQuest;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class EnterTheAbyss implements ScriptTask
{
	static List<ScriptTask> tasks = new ArrayList<>();
	VitalQuesterConfig config;

	public EnterTheAbyss(VitalQuesterConfig config)
	{
		this.config = config;

		tasks.clear();

		tasks.add(new StartQuest(config));
		tasks.add(new TalkToMage(config));
		tasks.add(new TalkToCromperty(config));
		tasks.add(new TalkToSedridor(config));
		tasks.add(new TalkToAubury(config));
	}

	@Override
	public boolean validate()
	{
		return (config.currentQuest().equals(QuestList.RUNE_MYSTERIES) || config.automaticOptimal()) && Quests.getState(Quest.RUNE_MYSTERIES) != QuestState.FINISHED;
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
