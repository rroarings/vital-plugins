package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class ShearSheep implements ScriptTask
{
    private final WorldPoint farmer_fred_point = new WorldPoint(3195, 3269, 0);

    VitalQuesterConfig config;

    public ShearSheep(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask sheer_sheap = new VitalTask(() ->
    {
        if (!Tools.interactWith(2693, "Shear", farmer_fred_point, Tools.EntityType.TILE_ITEM)) {
            return false;
        }

        return Inventory.contains(20, ItemID.WOOL);
    });

    @Override
    public boolean validate()
    {
        return !sheer_sheap.taskCompleted() && Inventory.getCount(false, ItemID.WOOL) < 20 && !Inventory.contains(ItemID.BALL_OF_WOOL);
    }

    @Override
    public int execute() {

        if(!sheer_sheap.execute()) {

            return -3;
        }

        return -1;
    }
}
