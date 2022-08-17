package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;

public class StartQuest implements ScriptTask
{
    private final WorldPoint abigale_point = new WorldPoint(3237, 3150, 0);

    VitalQuesterConfig config;

    public StartQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) < 10;
    }

    DialogTask talk_to_abigale = new DialogTask("Abigale",  abigale_point,
            "Yes.");

    @Override
    public int execute() {

        if (!talk_to_abigale.taskCompleted()) {
            return talk_to_abigale.execute();
        }

        return -1;
    }
}
