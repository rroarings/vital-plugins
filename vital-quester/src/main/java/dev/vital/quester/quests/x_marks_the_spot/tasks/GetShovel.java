package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class GetShovel implements ScriptTask
{
    private final WorldPoint shop_keeper_point = new WorldPoint(3213, 3247, 0);

    VitalQuesterConfig config;

    public GetShovel(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.SPADE);
    }

    @Override
    public int execute() {

        if(!Inventory.contains(ItemID.SPADE) && Inventory.getCount(true, ItemID.COINS_995) >= 3) {
            return Tools.purchaseFrom("Shop keeper", shop_keeper_point, ItemID.SPADE, 1, false);
        }

        return -2;
    }
}