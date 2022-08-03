package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;

public class TalkToGhost implements ScriptTask
{
    private final WorldPoint ghost_point = new WorldPoint(3248, 3192, 0);

    VitalQuesterConfig config;

    public TalkToGhost(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask talk_to_ghost = new VitalTask(() ->
    {
        if(!Tools.interactWith("Restless ghost", "Talk-to", ghost_point, Tools.EntityType.NPC)) {

            return false;
        }

       return Tools.selectOptions("Yep, now tell me what the problem is.");
    });

    @Override
    public boolean validate()
    {
        return !talk_to_ghost.taskCompleted();
    }

    VitalTask open_coffin = new VitalTask(() ->
            Tools.interactWith("Coffin", "Open", ghost_point, Tools.EntityType.TILE_OBJECT));

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
