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
    VitalQuesterConfig vital_config;

    static List<ScriptTask> tasks = new ArrayList<>();

    public SheepShearer(VitalQuesterConfig vital_config) {
        this.vital_config = vital_config;

        tasks.clear();

        tasks.add(new BankInventory(vital_config));
        tasks.add(new GetShears(vital_config));
        tasks.add(new ShearSheep(vital_config));
        tasks.add(new SpinWool(vital_config));
        tasks.add(new TalkToFred(vital_config));
    }

    @Override
    public boolean validate()
    {
        return (vital_config.currentQuest().equals(QuestList.SHEEP_SHEARER) || vital_config.automaticOptimal()) && Quests.getState(Quest.SHEEP_SHEARER) != QuestState.FINISHED;
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
