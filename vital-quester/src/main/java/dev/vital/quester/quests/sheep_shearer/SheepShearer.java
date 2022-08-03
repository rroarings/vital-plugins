package dev.vital.quester.quests.sheep_shearer;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.sheep_shearer.tasks.*;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class SheepShearer implements ScriptTask
{
    VitalQuesterConfig config;

    static List<ScriptTask> tasks = new ArrayList<>();

    public SheepShearer(VitalQuesterConfig config) {
        this.config = config;

        tasks.clear();

        tasks.add(new GetShears(config));
        tasks.add(new ShearSheep(config));
        tasks.add(new SpinWool(config));
        tasks.add(new TalkToFred(config));
    }

    @Override
    public boolean validate()
    {
        return config.currentQuest().equals(QuestList.SHEEP_SHEARER) && Quests.getState(Quest.SHEEP_SHEARER) != QuestState.FINISHED;
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
