package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.*;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class ShearSheep implements ScriptTask
{
    private final WorldPoint sheep_point = new WorldPoint(3199, 3268, 0);

    VitalQuesterConfig config;

    public ShearSheep(VitalQuesterConfig config)
    {
        this.config = config;
    }

    NPCItemTask sheer_sheap = new NPCItemTask(2693, ItemID.WOOL, 20, false, "Shear", sheep_point);

    @Override
    public boolean validate()
    {
        return !sheer_sheap.taskCompleted() && Inventory.getCount(false, ItemID.WOOL) < 20 && !Inventory.contains(ItemID.BALL_OF_WOOL);
    }

    @Override
    public int execute() {

        if(!sheer_sheap.taskCompleted()) {

            return sheer_sheap.execute();
        }

        return -1;
    }
}
