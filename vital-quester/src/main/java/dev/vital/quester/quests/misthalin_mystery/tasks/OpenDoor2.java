package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.ItemTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;

public class OpenDoor2 implements ScriptTask
{
    WorldPoint knife_point = new WorldPoint(1637, 4829, 0);
    WorldPoint door_point = new WorldPoint(1635, 4838, 0);
    VitalQuesterConfig config;

    public OpenDoor2(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 45;
    }

    ItemTask pickup_knife  = new ItemTask(ItemID.KNIFE, 1, false, knife_point);
    BasicTask open_door = new BasicTask(() -> {
        if(Tools.interactWith(30116, "Open", door_point, Tools.EntityType.TILE_OBJECT) == -5) {
            return 0;
        }

        return 0;
    });

    @Override
    public int execute() {

        if (!pickup_knife.taskCompleted()) {
            return pickup_knife.execute();
        }
        else if (!open_door.taskCompleted()) {
            return open_door.execute();
        }

        return -1;
    }
}
