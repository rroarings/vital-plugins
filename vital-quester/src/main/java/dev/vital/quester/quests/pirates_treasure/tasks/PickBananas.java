package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class PickBananas implements ScriptTask
{
    private final WorldPoint tree_point = new WorldPoint(2916, 3155, 0);

    VitalQuesterConfig config;

    public PickBananas(VitalQuesterConfig config)
    {
        this.config = config;
    }

    BasicTask pick_bananas = new BasicTask(() ->
    {
        Tools.interactWith("Banana Tree", "Pick", tree_point, Tools.EntityType.TILE_OBJECT);

        return Inventory.getCount(ItemID.BANANA) >= 10;
    });

    @Override
    public boolean validate()
    {
        return !pick_bananas.taskCompleted();
    }

    @Override
    public int execute() {

        if(!pick_bananas.execute()) {
            return -2;
        }

        return -1;
    }
}
