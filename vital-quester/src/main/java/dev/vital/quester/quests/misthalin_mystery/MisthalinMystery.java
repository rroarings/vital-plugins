package dev.vital.quester.quests.misthalin_mystery;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.misthalin_mystery.tasks.ClickJewels;
import dev.vital.quester.quests.misthalin_mystery.tasks.FillBucket;
import dev.vital.quester.quests.misthalin_mystery.tasks.FuckMirrors;
import dev.vital.quester.quests.misthalin_mystery.tasks.GetBucketAndTalk;
import dev.vital.quester.quests.misthalin_mystery.tasks.GetEmeraldKey;
import dev.vital.quester.quests.misthalin_mystery.tasks.GetKey;
import dev.vital.quester.quests.misthalin_mystery.tasks.GetRubyKey;
import dev.vital.quester.quests.misthalin_mystery.tasks.GetSapphireKey;
import dev.vital.quester.quests.misthalin_mystery.tasks.GetTinderBox;
import dev.vital.quester.quests.misthalin_mystery.tasks.GoToIsland;
import dev.vital.quester.quests.misthalin_mystery.tasks.LightFuse;
import dev.vital.quester.quests.misthalin_mystery.tasks.OpenDoor1;
import dev.vital.quester.quests.misthalin_mystery.tasks.OpenDoor2;
import dev.vital.quester.quests.misthalin_mystery.tasks.OpenDoor3;
import dev.vital.quester.quests.misthalin_mystery.tasks.OpenDoor4;
import dev.vital.quester.quests.misthalin_mystery.tasks.OpenDoor5;
import dev.vital.quester.quests.misthalin_mystery.tasks.PickupNote1;
import dev.vital.quester.quests.misthalin_mystery.tasks.PickupNote2;
import dev.vital.quester.quests.misthalin_mystery.tasks.PickupNote3;
import dev.vital.quester.quests.misthalin_mystery.tasks.PlayPiano;
import dev.vital.quester.quests.misthalin_mystery.tasks.RunAway;
import dev.vital.quester.quests.misthalin_mystery.tasks.StartQuest;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class MisthalinMystery implements ScriptTask
{
	static List<ScriptTask> tasks = new ArrayList<>();
	VitalQuesterConfig config;

	public MisthalinMystery(VitalQuesterConfig config)
	{
		this.config = config;

		tasks.clear();

		tasks.add(new StartQuest(config));
		tasks.add(new GoToIsland(config));
		tasks.add(new GetBucketAndTalk(config));
		tasks.add(new FillBucket(config));
		tasks.add(new GetKey(config));
		tasks.add(new OpenDoor1(config));
		tasks.add(new PickupNote1(config));
		tasks.add(new GetRubyKey(config));
		tasks.add(new OpenDoor2(config));
		tasks.add(new GetTinderBox(config));
		tasks.add(new GetTinderBox(config));
		tasks.add(new LightFuse(config));
		tasks.add(new RunAway(config));
		tasks.add(new OpenDoor3(config));
		tasks.add(new PickupNote2(config));
		tasks.add(new PlayPiano(config));
		tasks.add(new GetEmeraldKey(config));
		tasks.add(new OpenDoor4(config));
		tasks.add(new PickupNote3(config));
		tasks.add(new OpenDoor5(config));
		tasks.add(new ClickJewels(config));
		tasks.add(new GetSapphireKey(config));
		tasks.add(new FuckMirrors(config));
	}

	@Override
	public boolean validate()
	{
		return (config.currentQuest().equals(QuestList.MISTHALIN_MYSTERY) || config.automaticOptimal()) && Quests.getState(Quest.MISTHALIN_MYSTERY) != QuestState.FINISHED;
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
