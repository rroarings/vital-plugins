package dev.vital.quester.quests.cooks_assistant.tasks;

import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.tools.Tools;

public class GetEgg implements ScriptTask
{
    private final WorldPoint coop_point = new WorldPoint(3231, 3298, 0);

    VitalQuesterConfig config;

    public GetEgg(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.EGG);
    }

    @Override
    public int execute()
    {
       if(Tools.interactWith("Coop", "Search", coop_point, Tools.EntityType.TILE_OBJECT) == -5) {
           return -5;
       }

        return -1;
    }
}
