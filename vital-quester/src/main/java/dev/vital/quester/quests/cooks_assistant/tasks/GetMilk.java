package dev.vital.quester.quests.cooks_assistant.tasks;

import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.tools.Tools;

public class GetMilk implements ScriptTask
{
    private final WorldPoint bucket_point = new WorldPoint(3225, 3294, 0);
    private final WorldPoint dairy_cow_point = new WorldPoint(3225, 3294, 0);

    VitalQuesterConfig config;

    public GetMilk(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.BUCKET_OF_MILK);
    }

    @Override
    public int execute()
    {
        if(!Inventory.contains(ItemID.BUCKET)) {

            if(Tools.interactWith("Bucket", "Take", bucket_point, Tools.EntityType.TILE_ITEM)) {
                return -5;
            }
        }
        else {
            if(Tools.interactWith("Dairy cow", "Milk", dairy_cow_point, Tools.EntityType.TILE_OBJECT)) {
                return -5;
            }
        }

        return -1;
    }
}
