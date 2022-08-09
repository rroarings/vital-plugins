package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;

public class GetJob2 implements ScriptTask
{
    private final WorldPoint wydin_point = new WorldPoint(3014, 3204, 0);

    VitalQuesterConfig config;

    public GetJob2(VitalQuesterConfig config)
    {
        this.config = config;
    }

    BasicTask get_job2 = new BasicTask(() ->
    {
        if(Tools.interactWith("Wydin", "Talk-to", wydin_point, Tools.EntityType.NPC) == -5) {
            return -5;
        }

        if(Tools.selectOptions("Can I get a job here?")) {
            return 0;
        }
        return -1;
    });

    @Override
    public boolean validate()
    {
        return !get_job2.taskCompleted();
    }

    @Override
    public int execute() {

        return get_job2.execute();
    }
}
