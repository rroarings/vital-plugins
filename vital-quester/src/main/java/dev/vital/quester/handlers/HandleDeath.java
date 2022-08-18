package dev.vital.quester.handlers;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.TileObjects;

public class HandleDeath implements ScriptTask
{
    private final WorldPoint death_point = new WorldPoint(10702, 1991, 0);

    VitalQuesterConfig config;

    public HandleDeath(VitalQuesterConfig config) {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return config.handleDeath() && LocalPlayer.get().getWorldLocation().getRegionID() == 42783 || LocalPlayer.get().getWorldLocation().getRegionID() == 26655;
    }

    DialogTask talk_to_death = new DialogTask("Death",  death_point,
            "How do I pay a gravestone fee?", "How long do I have to return to my gravestone?",
            "How do I know what will happen to my items when I die?", "I think I'm done here.");

    @Override
    public int execute() {

        if(!talk_to_death.taskCompleted()) {
            return talk_to_death.execute();
        }

        TileObjects.getNearest("Portal").interact("Use");

        return -1;
    }
}
