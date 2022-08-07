package dev.vital.quester.quests.rune_mysteries.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class DeliverNotes implements ScriptTask
{
    private final WorldPoint sedridor_point = new WorldPoint(3104, 9571, 0);

    VitalQuesterConfig config;

    public DeliverNotes(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(13726) == 0;
    }

    @Override
    public int execute()
    {
        if(!Tools.interactWith("Archmage Sedridor", "Talk-to", sedridor_point, Tools.EntityType.NPC)) {
            return -5;
        }

        return -1;
    }
}
