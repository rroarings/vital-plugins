package dev.vital.quester.quests.pirates_treasure;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.pirates_treasure.tasks.BuyRum;
import dev.vital.quester.quests.pirates_treasure.tasks.EndJob;
import dev.vital.quester.quests.pirates_treasure.tasks.FillCrate;
import dev.vital.quester.quests.pirates_treasure.tasks.GetApron;
import dev.vital.quester.quests.pirates_treasure.tasks.GetJob;
import dev.vital.quester.quests.pirates_treasure.tasks.GetJob2;
import dev.vital.quester.quests.pirates_treasure.tasks.GetRum;
import dev.vital.quester.quests.pirates_treasure.tasks.LootChest;
import dev.vital.quester.quests.pirates_treasure.tasks.PickBananas;
import dev.vital.quester.quests.pirates_treasure.tasks.StartQuest;
import dev.vital.quester.quests.pirates_treasure.tasks.TalkToFrank;
import dev.vital.quester.quests.pirates_treasure.tasks.WorkFalador;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class PiratesTreasure implements ScriptTask
{
	static List<ScriptTask> tasks = new ArrayList<>();
	VitalQuesterConfig config;

	public PiratesTreasure(VitalQuesterConfig config)
	{
		this.config = config;

		tasks.clear();

		tasks.add(new StartQuest(config));
		tasks.add(new BuyRum(config));
		tasks.add(new PickBananas(config));
		tasks.add(new GetJob(config));
		tasks.add(new FillCrate(config));
		tasks.add(new EndJob(config));
		tasks.add(new GetApron(config));
		tasks.add(new GetJob2(config));
		tasks.add(new GetRum(config));
		tasks.add(new TalkToFrank(config));
		tasks.add(new LootChest(config));
		tasks.add(new WorkFalador(config));
	}

	@Override
	public boolean validate()
	{
		return (config.currentQuest().equals(QuestList.PIRATES_TREASURE) || config.automaticOptimal()) && Quests.getState(Quest.PIRATES_TREASURE) != QuestState.FINISHED;
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
