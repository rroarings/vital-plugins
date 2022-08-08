package dev.vital.quester.quests.rune_mysteries.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.Varbits;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class AcceptPackage implements ScriptTask
{
    private final WorldPoint sedridor_point = new WorldPoint(3104, 9571, 0);

    VitalQuesterConfig config;

    public AcceptPackage(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(13723) == 0;
    }

    DialogTask accept_package = new DialogTask("Archmage Sedridor", sedridor_point,
            "Okay, here you are.", "Go ahead.", "Yes, certainly.");

    @Override
    public int execute()
    {
        if(!accept_package.taskCompleted()) {
            return accept_package.execute();
        }

        return -1;
    }
}
