package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.quests.Quests;

public class StartQuest implements ScriptTask
{
    private final WorldPoint veos_point = new WorldPoint(3228, 3241, 0);

    VitalQuesterConfig config;

    public StartQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Quests.getState(Quest.X_MARKS_THE_SPOT) == QuestState.NOT_STARTED;
    }

    DialogTask talk_to_veos = new DialogTask("Veos",  veos_point,
            "Yes,", "I'm looking for a quest.", "Okay, thanks Veos.");

    @Override
    public int execute() {

        if (!talk_to_veos.taskCompleted()) {
            return talk_to_veos.execute();
        }

        return -1;
    }
}
