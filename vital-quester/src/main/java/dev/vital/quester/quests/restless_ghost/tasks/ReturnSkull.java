package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class ReturnSkull implements ScriptTask
{
    private final WorldPoint ghost_point = new WorldPoint(3248, 3192, 0);

    VitalQuesterConfig config;

    public ReturnSkull(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Inventory.contains(ItemID.GHOSTS_SKULL);
    }

    BasicTask open_coffin = new BasicTask(() -> {

        if(Tools.interactWith("Coffin", "Open", ghost_point, Tools.EntityType.TILE_OBJECT) == -5) {
            return 0;
        }

        return -5;
    });

    @Override
    public int execute()
    {
        if(!open_coffin.taskCompleted()) {
            return open_coffin.execute();
        }
        else {
            return Tools.interactWith("Coffin", "Search", ghost_point, Tools.EntityType.TILE_OBJECT);
        }
    }
}
