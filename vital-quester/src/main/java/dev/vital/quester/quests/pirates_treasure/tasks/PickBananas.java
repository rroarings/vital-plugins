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

public class PickBananas implements ScriptTask
{
    private final WorldPoint tree_point = new WorldPoint(2916, 3155, 0);

    VitalQuesterConfig config;

    public PickBananas(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask pick_bananas = new VitalTask(() ->
    {
        if(!Tools.interactWith("Banana tree", "Pick", tree_point, Tools.EntityType.NPC)) {
            return false;
        }

        int count = Inventory.getCount(false, ItemID.KARAMJAN_RUM);
        Shop.buyOne(ItemID.KARAMJAN_RUM);
        Time.sleepTicksUntil(() -> count != Inventory.getCount(false, ItemID.KARAMJAN_RUM), 5);

        return Inventory.contains(ItemID.KARAMJAN_RUM);
    });

    @Override
    public boolean validate()
    {
        return !pick_bananas.taskCompleted();
    }

    @Override
    public int execute() {

        if(!pick_bananas.execute()) {
            return -5;
        }

        return -1;
    }
}
