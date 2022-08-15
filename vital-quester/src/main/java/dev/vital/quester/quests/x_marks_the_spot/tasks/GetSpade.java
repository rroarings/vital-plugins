package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class GetSpade implements ScriptTask
{
    private final WorldPoint shop_keeper_point = new WorldPoint(3213, 3247, 0);

    VitalQuesterConfig config;

    public GetSpade(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.SPADE);
    }
    BasicTask sell_bronze_axe = new BasicTask(() ->
            Tools.sellTo("Shop keeper", shop_keeper_point, ItemID.BRONZE_AXE, 0, false));

    @Override
    public int execute() {

        if(Inventory.getCount(true, ItemID.COINS_995) < 3 && Inventory.contains(ItemID.BRONZE_AXE)) {

            if (!sell_bronze_axe.taskCompleted()) {
                return sell_bronze_axe.execute();
            }
        }

        if(!Inventory.contains(ItemID.SPADE) && Inventory.getCount(true, ItemID.COINS_995) >= 3) {
            return Tools.purchaseFrom("Shop keeper", shop_keeper_point, ItemID.SPADE, 1, false);
        }

        return -2;
    }
}