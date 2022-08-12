package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class TalkToFrank implements ScriptTask
{
    private final WorldPoint pirate_point = new WorldPoint(3054, 3253, 0);

    VitalQuesterConfig config;

    public TalkToFrank(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Inventory.contains(ItemID.KARAMJAN_RUM);
    }

    DialogTask talk_to_frank = new DialogTask("Redbeard Frank", pirate_point,
            (String)null);

    @Override
    public int execute() {

        if (!talk_to_frank.taskCompleted()) {
           return talk_to_frank.execute();
        }

        return -1;
    }
}
