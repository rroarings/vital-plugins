package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.BasicTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class ReturnSkull implements ScriptTask
{
    VitalQuesterConfig config;

    public ReturnSkull(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getVarp(QuestVarPlayer.QUEST_THE_RESTLESS_GHOST.getId()) == 4;
    }

    BasicTask open_coffin = new BasicTask(() -> {

        var coffin = TileObjects.getFirstAt(new WorldPoint(3249, 3192, 0), "Coffin");
        if (coffin != null) {
            if(coffin.hasAction("Search")) {
                coffin.interact("Search");
            }
            else if(coffin.hasAction("Open")) {
                coffin.interact("Open");
            }
        }

        return -5;
    });

    @Override
    public int execute()
    {
        if(!open_coffin.taskCompleted()) {
            return open_coffin.execute();
        }

        return -1;
    }
}
