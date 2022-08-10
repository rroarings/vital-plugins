package dev.vital.quester.quests.tutorial_island;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.x_marks_the_spot.tasks.*;
import net.unethicalite.api.game.GameSettings;

import java.util.ArrayList;
import java.util.List;

public class TutorialIsland implements ScriptTask
{
    VitalQuesterConfig config;

    static List<ScriptTask> tasks = new ArrayList<>();

    public TutorialIsland(VitalQuesterConfig config) {
        this.config = config;

        tasks.clear();

        tasks.add(new StartQuest(config));
    }

    @Override
    public boolean validate()
    {
        return config.currentQuest().equals(QuestList.TUTORIAL_ISLAND);
    }


    @Override
    public int execute() {

        if(GameSettings.Display.getCurrentMode() != GameSettings.Display.FIXED) {
            GameSettings.Display.setMode(GameSettings.Display.FIXED);
            return -5;
        }

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
