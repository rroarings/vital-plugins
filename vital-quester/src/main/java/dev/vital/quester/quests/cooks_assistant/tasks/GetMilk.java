package dev.vital.quester.quests.cooks_assistant.tasks;

import dev.vital.quester.ItemTask;
import dev.vital.quester.ObjectItemTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class GetMilk implements ScriptTask
{
    private final WorldPoint bucket_point = new WorldPoint(3225, 3294, 0);
    private final WorldPoint dairy_cow_point = new WorldPoint(3253, 3274, 0);

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

    ItemTask get_bukcet = new ItemTask(ItemID.BUCKET, 1, false, bucket_point);
    ObjectItemTask get_milk = new ObjectItemTask(8689, ItemID.BUCKET_OF_MILK, 1, false, "Milk", dairy_cow_point);

    @Override
    public int execute()
    {
        if(!get_bukcet.taskCompleted()) {
            return get_bukcet.execute();
        }
        else if(!get_milk.taskCompleted()) {
            return get_milk.execute();
        }

        return -1;
    }
}
