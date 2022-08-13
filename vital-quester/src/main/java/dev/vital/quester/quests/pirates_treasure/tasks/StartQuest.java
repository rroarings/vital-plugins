package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class StartQuest implements ScriptTask
{
    private final WorldPoint pirate_point = new WorldPoint(3054, 3253, 0);

    VitalQuesterConfig config;

    public StartQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getVarp(QuestVarPlayer.QUEST_PIRATES_TREASURE.getId()) == 0;
    }

    DialogTask talk_to_frank = new DialogTask("Redbeard Frank", pirate_point,
            "I'm in search of treasure.", "Yes.");

    @Override
    public int execute() {

        if(!talk_to_frank.taskCompleted()) {
            return talk_to_frank.execute();
        }

        return -1;
    }
}
