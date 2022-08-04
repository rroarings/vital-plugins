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

public class GetJob implements ScriptTask
{
    private final WorldPoint zambo_location = new WorldPoint(2928, 3144, 0);

    VitalQuesterConfig config;

    public GetJob(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask buy_rum = new VitalTask(() ->
    {
        if(!Tools.interactWith("Zambo", "Trade", zambo_location, Tools.EntityType.NPC)) {
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
        return !buy_rum.taskCompleted();
    }

    @Override
    public int execute() {

        if(!buy_rum.execute()) {
            return -5;
        }

        return -1;
    }
}
