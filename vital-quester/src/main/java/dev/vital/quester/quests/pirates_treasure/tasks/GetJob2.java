package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;

public class GetJob2 implements ScriptTask
{
    private final WorldPoint wydin_point = new WorldPoint(3014, 3204, 0);

    VitalQuesterConfig config;

    public GetJob2(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask get_job2 = new VitalTask(() ->
    {
        if(!Tools.interactWith("Wydin", "Talk-to", wydin_point, Tools.EntityType.NPC)) {
            return false;
        }

        return Tools.selectOptions("Can I get a job here?");
    });

    @Override
    public boolean validate()
    {
        return !get_job2.taskCompleted();
    }

    @Override
    public int execute() {

        if(!get_job2.execute()) {
            return -5;
        }

        return -1;
    }
}
