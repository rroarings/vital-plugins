package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.ObjectItemTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class GetSkull implements ScriptTask
{
    private final WorldPoint altar_point = new WorldPoint(3119, 9565, 0);

    VitalQuesterConfig config;

    public GetSkull(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getVarp(QuestVarPlayer.QUEST_THE_RESTLESS_GHOST.getId()) == 3;
    }

    ObjectItemTask get_skull = new ObjectItemTask(2146, ItemID.GHOSTS_SKULL, 1, false, "Search", altar_point);

    @Override
    public int execute() {

        if(!get_skull.taskCompleted()) {
            return get_skull.execute();
        }

        return -1;
    }
}
