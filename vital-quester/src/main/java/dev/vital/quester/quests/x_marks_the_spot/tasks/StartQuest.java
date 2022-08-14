package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;

public class    StartQuest implements ScriptTask
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
        return Vars.getBit(QuestVarbits.QUEST_X_MARKS_THE_SPOT.getId()) == 0 || Vars.getBit(QuestVarbits.QUEST_X_MARKS_THE_SPOT.getId()) == 1;
    }


    DialogTask talk_to_veos = new DialogTask("Veos",  veos_point,
            "Yes.","Yes,", "I'm looking for a quest.", "Okay, thanks Veos.");

    @Override
    public int execute() {

        if (!talk_to_veos.taskCompleted()) {
            return talk_to_veos.execute();
        }

        return -1;
    }
}
