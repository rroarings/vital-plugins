package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;

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

            if (!Tools.interactWith("Shop keeper", "Trade", shop_keeper_point, Tools.EntityType.NPC)) {
                return -5;
            }

            if (Shop.isOpen()) {
                Shop.buyOne(ItemID.SPADE);
                return -5;
            }

            return -1;
        }

        return -1;
    }
}