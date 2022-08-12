package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class GetRum implements ScriptTask
{
    private final WorldPoint rum_point = new WorldPoint(3010, 3206, 0);

    VitalQuesterConfig config;

    public GetRum(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.KARAMJAN_RUM);
    }

    @Override
    public int execute() {

        if (Tools.interactWith("Crate", "Search", rum_point, Tools.EntityType.TILE_OBJECT) == -5) {

            return -5;
        }

        return -1;
    }
}
