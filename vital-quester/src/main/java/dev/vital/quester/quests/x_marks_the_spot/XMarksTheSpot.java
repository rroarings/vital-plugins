package dev.vital.quester.quests.x_marks_the_spot;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.x_marks_the_spot.tasks.GetAxe;
import dev.vital.quester.quests.x_marks_the_spot.tasks.GetSpade;
import dev.vital.quester.quests.x_marks_the_spot.tasks.StartQuest;
import dev.vital.quester.quests.x_marks_the_spot.tasks.StepFive;
import dev.vital.quester.quests.x_marks_the_spot.tasks.StepFour;
import dev.vital.quester.quests.x_marks_the_spot.tasks.StepOne;
import dev.vital.quester.quests.x_marks_the_spot.tasks.StepThree;
import dev.vital.quester.quests.x_marks_the_spot.tasks.StepTwo;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class XMarksTheSpot implements ScriptTask
{
	static List<ScriptTask> tasks = new ArrayList<>();
	VitalQuesterConfig config;

	public XMarksTheSpot(VitalQuesterConfig config)
	{
		this.config = config;

		tasks.clear();

		tasks.add(new GetAxe(config));
		tasks.add(new GetSpade(config));
		tasks.add(new StartQuest(config));
		tasks.add(new StepOne(config));
		tasks.add(new StepTwo(config));
		tasks.add(new StepThree(config));
		tasks.add(new StepFour(config));
		tasks.add(new StepFive(config));
	}

	@Override
	public boolean validate()
	{
		return (config.currentQuest().equals(QuestList.X_MARKS_THE_SPOT) || config.automaticOptimal()) && Quests.getState(Quest.X_MARKS_THE_SPOT) != QuestState.FINISHED;
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
