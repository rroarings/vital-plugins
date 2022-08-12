package dev.vital.quester.quests.rune_mysteries;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.rune_mysteries.tasks.AcceptPackage;
import dev.vital.quester.quests.rune_mysteries.tasks.DeliverNotes;
import dev.vital.quester.quests.rune_mysteries.tasks.DeliverPackage;
import dev.vital.quester.quests.rune_mysteries.tasks.StartQuest;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class RuneMysteries implements ScriptTask
{
    VitalQuesterConfig config;

    static List<ScriptTask> tasks = new ArrayList<>();

    public RuneMysteries(VitalQuesterConfig config) {
        this.config = config;

        tasks.clear();

        tasks.add(new StartQuest(config));
        tasks.add(new AcceptPackage(config));
        tasks.add(new DeliverPackage(config));
        tasks.add(new DeliverNotes(config));
    }

    @Override
    public boolean validate()
    {
        return config.currentQuest().equals(QuestList.RUNE_MYSTERIES) && Quests.getState(Quest.RUNE_MYSTERIES) != QuestState.FINISHED;
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
