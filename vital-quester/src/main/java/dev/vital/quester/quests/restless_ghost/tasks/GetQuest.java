package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class GetQuest implements ScriptTask
{
    private final WorldPoint father_aereck_point = new WorldPoint(3240, 3206, 0);

    VitalQuesterConfig config;

    public GetQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getVarp(QuestVarPlayer.QUEST_THE_RESTLESS_GHOST.getId()) == 0;
    }

    DialogTask talk_to_aereck = new DialogTask("Father Aereck",  father_aereck_point,
            "I'm looking for a quest!", "Yes.");

    @Override
    public int execute() {

        if (!talk_to_aereck.taskCompleted()) {
           return talk_to_aereck.execute();
        }

        return -1;
    }
}
