package dev.vital.quester.quests.cooks_assistant;

import dev.vital.quester.QuestList;
import dev.vital.quester.quests.cooks_assistant.tasks.GetEgg;
import dev.vital.quester.quests.cooks_assistant.tasks.GetFlour;
import dev.vital.quester.quests.cooks_assistant.tasks.GetMilk;
import dev.vital.quester.quests.cooks_assistant.tasks.TalkToCook;
import dev.vital.quester.tools.Tools;
import net.unethicalite.api.quests.QuestVarPlayer;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;

import java.util.ArrayList;
import java.util.List;

public class CooksAssistant implements ScriptTask
{
	VitalQuesterConfig config;

	static List<ScriptTask> tasks = new ArrayList<>();

	public CooksAssistant(VitalQuesterConfig config) {
		this.config = config;

		tasks.clear();

		tasks.add(new TalkToCook(config));
		tasks.add(new GetEgg(config));
		tasks.add(new GetMilk(config));
		tasks.add(new GetFlour(config));
	}

	@Override
	public boolean validate()
	{
		return config.currentQuest().equals(QuestList.COOKS_ASSISTANT) && Tools.getQuestProgress(QuestVarPlayer.QUEST_COOKS_ASSISTANT) != 2;
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
