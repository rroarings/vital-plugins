package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;

public class EndJob implements ScriptTask
{
    private final WorldPoint luthas_location = new WorldPoint(2938, 3152, 0);

    VitalQuesterConfig config;

    public EndJob(VitalQuesterConfig config)
    {
        this.config = config;
    }

    DialogTask talk_to_luthus = new DialogTask("Luthas", luthas_location, (String)null);

    @Override
    public boolean validate()
    {
        return !talk_to_luthus.taskCompleted();
    }

    @Override
    public int execute() {

        if(!talk_to_luthus.taskCompleted()) {
            return talk_to_luthus.execute();
        }

        return -1;
    }
}
