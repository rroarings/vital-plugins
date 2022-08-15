package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.tasks.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;

public class GetJob implements ScriptTask
{
    private final WorldPoint luthas_location = new WorldPoint(2938, 3152, 0);

    VitalQuesterConfig config;

    public GetJob(VitalQuesterConfig config)
    {
        this.config = config;
    }

    DialogTask get_job = new DialogTask("Luthas",  luthas_location, "Could you offer me employment on your plantation?");

    @Override
    public boolean validate()
    {
        return !get_job.taskCompleted();
    }

    @Override
    public int execute() {

        return get_job.execute();
    }
}
