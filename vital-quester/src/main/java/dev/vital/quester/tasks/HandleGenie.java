package dev.vital.quester.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;

public class HandleGenie implements ScriptTask
{
    VitalQuesterConfig config;

    public HandleGenie(VitalQuesterConfig config) {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return config.handleGenie() && NPCs.getNearest(x -> x.getInteracting() == LocalPlayer.get() && x.getName().equals("Genie")) != null;
    }

    @Override
    public int execute() {

        var genie = NPCs.getNearest(x -> x.getInteracting() == LocalPlayer.get() && x.getName().equals("Genie"));
        if (genie != null) {
            genie.interact("Talk-to");
            return -5;
        }

        return -1;
    }
}
