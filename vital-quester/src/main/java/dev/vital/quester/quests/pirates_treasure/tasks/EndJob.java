package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
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

    VitalTask end_job = new VitalTask(() ->
    {
        if(!Tools.interactWith("Luthas", "Talk-to", luthas_location, Tools.EntityType.NPC)) {
            return false;
        }

        return true;
    });

    @Override
    public boolean validate()
    {
        return !end_job.taskCompleted();
    }

    @Override
    public int execute() {

        if(!end_job.execute()) {
            return -5;
        }

        return -1;
    }
}
