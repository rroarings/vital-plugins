package dev.vital.quester.quests.restless_ghost;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.cooks_assistant.tasks.TalkToCook;
import dev.vital.quester.quests.restless_ghost.tasks.EquipAmulet;
import dev.vital.quester.quests.restless_ghost.tasks.TalkToGhost;
import dev.vital.quester.quests.restless_ghost.tasks.GetAmulet;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class RestlessGhost implements ScriptTask
{
	VitalQuesterConfig config;

	static List<ScriptTask> tasks = new ArrayList<>();

	public RestlessGhost(VitalQuesterConfig config) {
		this.config = config;

		tasks.clear();

		tasks.add(new EquipAmulet(config));
		tasks.add(new GetAmulet(config));
		tasks.add(new TalkToGhost(config));
		tasks.add(new TalkToCook(config));
	}

	@Override
	public boolean validate()
	{
		return config.currentQuest().equals(QuestList.COOKS_ASSISTANT) && Quests.getState(Quest.COOKS_ASSISTANT) != QuestState.FINISHED;
	}

	@Override
	public int execute() {

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
