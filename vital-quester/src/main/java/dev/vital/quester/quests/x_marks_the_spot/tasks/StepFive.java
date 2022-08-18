package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class StepFive implements ScriptTask
{
    private final WorldPoint veos_point_2 = new WorldPoint(3054, 3246, 0);

    VitalQuesterConfig config;

    public StepFive(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Inventory.contains(ItemID.ANCIENT_CASKET);
    }

    DialogTask talk_to_veos = new DialogTask("Veos",  veos_point_2,
            (String)null);

    @Override
    public int execute() {

       if(!talk_to_veos.taskCompleted()) {
           return talk_to_veos.execute();
       }

        return -1;
    }
}