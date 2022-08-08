package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class GetSkull implements ScriptTask
{
    private final WorldPoint altar_point = new WorldPoint(3111, 9559, 0);

    VitalQuesterConfig config;

    public GetSkull(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.GHOSTS_SKULL);
    }

    @Override
    public int execute() {

        switch(Tools.interactWith("Altar", "Search", altar_point, Tools.EntityType.TILE_OBJECT)) {
            case -5: {
                //try to avoid the skeleton
                Time.sleepTicksUntil(() -> Inventory.contains(ItemID.GHOSTS_SKULL), 20);
                if (Inventory.contains(ItemID.GHOSTS_SKULL)) {
                    Movement.walk(altar_point);
                }
            }
            default: {
                break;
            }
        }

        return -1;
    }
}
