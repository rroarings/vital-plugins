package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Production;

public class SpinWool implements ScriptTask
{
    private final WorldPoint spinning_wheel_point = new WorldPoint(3209, 3213, 1);

    VitalQuesterConfig config;

    public SpinWool(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask spin_wool = new VitalTask(() ->
    {
        if(!Tools.isAnimating(5)) {
            if (!Production.isOpen()) {
                Tools.interactWith("Spinning wheel", "Spin", spinning_wheel_point, Tools.EntityType.TILE_OBJECT);
                return false;
            }

            Production.chooseOption(1);
        }

        return Inventory.getCount(false, ItemID.BALL_OF_WOOL) >= 20;
    });

    @Override
    public boolean validate()
    {
        return !spin_wool.taskCompleted() && Inventory.getCount(false, ItemID.BALL_OF_WOOL) < 20;
    }

    @Override
    public int execute() {

        if(!spin_wool.execute()) {
            return -5;
        }

        return -1;
    }
}
