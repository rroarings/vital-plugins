package dev.vital.quester.quests.x_marks_the_spot;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.x_marks_the_spot.tasks.*;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class XMarksTheSpot implements ScriptTask
{
    VitalQuesterConfig config;

    static List<ScriptTask> tasks = new ArrayList<>();

    public XMarksTheSpot(VitalQuesterConfig config) {
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
