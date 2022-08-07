package dev.vital.quester.quests.rune_mysteries.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class DeliverPackage implements ScriptTask
{
    private final WorldPoint aubury_point = new WorldPoint(3253, 3401, 0);

    VitalQuesterConfig config;

    public DeliverPackage(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(13725) == 0;
    }

    @Override
    public int execute()
    {
        if(!Tools.interactWith("Aubury", "Talk-to", aubury_point, Tools.EntityType.NPC)) {
            return -5;
        }

        Tools.selectOptions("I've been sent here with a package for you.");

        return -1;
    }
}
