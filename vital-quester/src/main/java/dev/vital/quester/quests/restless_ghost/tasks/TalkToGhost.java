package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.tasks.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class TalkToGhost implements ScriptTask
{
    private final WorldPoint ghost_point = new WorldPoint(3248, 3192, 0);

    VitalQuesterConfig config;

    public TalkToGhost(VitalQuesterConfig config)
    {
        this.config = config;
    }

    DialogTask talk_to_ghost = new DialogTask("Restless ghost", ghost_point, "Yep, now tell me what the problem is.");

    @Override
    public boolean validate()
    {
        return Vars.getVarp(QuestVarPlayer.QUEST_THE_RESTLESS_GHOST.getId()) == 2;
    }

    BasicTask open_coffin = new BasicTask(() -> {

        if(NPCs.getNearest("Restless ghost") != null) {
            return 0;
        }

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
            open_coffin.execute();
        }

        if(!talk_to_ghost.taskCompleted()) {
            talk_to_ghost.execute();
        }

        return -1;
    }
}
