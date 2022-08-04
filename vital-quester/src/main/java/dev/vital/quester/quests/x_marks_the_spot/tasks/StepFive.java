package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
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
        return Inventory.contains(ItemID.TREASURE_SCROLL_23070);
    }

    @Override
    public int execute() {

       if(!Tools.interactWith(8484, "Talk-to", veos_point_2, Tools.EntityType.NPC)) {
           return -5;
       }

        return -1;
    }
}