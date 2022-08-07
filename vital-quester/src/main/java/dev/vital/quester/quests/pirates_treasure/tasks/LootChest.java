package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.movement.Movement;

public class LootChest implements ScriptTask
{
    private final WorldPoint rum_point = new WorldPoint(3219, 3395, 1);

    VitalQuesterConfig config;

    public LootChest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask loot_chest = new VitalTask(() ->
    {
        if(!Movement.isWalking()) {
           TileObjects.getFirstAt(1764,3858, 0,8975).interact("Chip");
           if(Movement.walkTo(rum_point)) {
               Inventory.getFirst(ItemID.CHEST_KEY).useOn(TileObjects.getNearest("Chest"));
               Time.sleepTicks(6);
               if(Inventory.contains(ItemID.PIRATE_MESSAGE)) {
                   Inventory.getFirst(ItemID.PIRATE_MESSAGE).interact("Read");
                   return true;
               }
           }
       }

        return false;
    });

    @Override
    public boolean validate()
    {
        return !loot_chest.taskCompleted();
    }

    @Override
    public int execute() {

        if (!loot_chest.execute()) {

            return -5;
        }

        return -1;
    }
}
