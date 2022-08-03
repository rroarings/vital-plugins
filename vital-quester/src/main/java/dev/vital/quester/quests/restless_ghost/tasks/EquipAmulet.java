package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;

public class EquipAmulet implements ScriptTask
{
    VitalQuesterConfig config;

    public EquipAmulet(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Equipment.contains(ItemID.GHOSTSPEAK_AMULET);
    }

    @Override
    public int execute()
    {
        if(Inventory.contains(ItemID.GHOSTSPEAK_AMULET)) {
            Inventory.getFirst(ItemID.GHOSTSPEAK_AMULET).interact("Wear");
        }

        return -1;
    }
}
