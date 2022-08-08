package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class GetShears implements ScriptTask
{
    private final WorldPoint farmer_fred_point = new WorldPoint(3190, 3273, 0);

    VitalQuesterConfig config;

    public GetShears(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.SHEARS) && Inventory.getCount(false, ItemID.WOOL) < 20 && !Inventory.contains(ItemID.BALL_OF_WOOL);
    }

    @Override
    public int execute() {

        return Tools.interactWith("Shears", "Take", farmer_fred_point, Tools.EntityType.TILE_ITEM);
    }
}
