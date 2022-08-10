package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.BasicTask;
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

    BasicTask spin_wool = new BasicTask(() ->
    {
        if(!Tools.isAnimating(5)) {
            if (!Production.isOpen()) {
                return Tools.interactWith("Spinning wheel", "Spin", spinning_wheel_point, Tools.EntityType.TILE_OBJECT);
            }

            Production.chooseOption(1);
        }

        if(Inventory.getCount(false, ItemID.BALL_OF_WOOL) == 20) {
            return 0;
        }
        return -1;
    });

    @Override
    public boolean validate()
    {
        return !spin_wool.taskCompleted() && Inventory.getCount(false, ItemID.BALL_OF_WOOL) < 20;
    }

    @Override
    public int execute() {

        if(!spin_wool.taskCompleted()) {
            return spin_wool.execute();
        }

        return -1;
    }
}
