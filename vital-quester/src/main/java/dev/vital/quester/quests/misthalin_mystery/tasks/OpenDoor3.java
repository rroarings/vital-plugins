package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;
import net.unethicalite.api.widgets.Dialog;

public class OpenDoor3 implements ScriptTask
{
    WorldPoint door_point = new WorldPoint(1635, 4838, 0);
    WorldPoint wall_point = new WorldPoint(1645, 4827, 0);
    WorldPoint dead_tree_point = new WorldPoint(1633, 4849, 0);

    VitalQuesterConfig config;

    public OpenDoor3(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 65;
    }
    BasicTask open_door = new BasicTask(() -> {
        if(Tools.interactWith(30116, "Open", door_point, Tools.EntityType.TILE_OBJECT) == -5) {
            return 0;
        }

        return -5;
    });

    BasicTask climb_wall = new BasicTask(() -> {
        if(Tools.interactWith("Damaged wall", "Climb", wall_point, Tools.EntityType.TILE_OBJECT) == -5) {
            return 0;
        }

        return -5;
    });

    BasicTask observe_dead_tree = new BasicTask(() -> {
        var sleep = Tools.interactWith(30150, "Observe", dead_tree_point, Tools.EntityType.TILE_OBJECT);
        if(sleep == -5) {
            return 0;
        }

        return sleep;
    });

    BasicTask interrupt = new BasicTask(() -> {
       if(Dialog.isViewingOptions()) {
           if(Dialog.chooseOption("Count Draynor")) {
               return 0;
           }
       }

       return -5;
    });

    @Override
    public int execute() {

        if (!open_door.taskCompleted()) {
            return open_door.execute();
        }
        else if (!climb_wall.taskCompleted()) {
            return climb_wall.execute();
        }
        else if (!observe_dead_tree.taskCompleted()) {
            return observe_dead_tree.execute();
        }
        else if (!interrupt.taskCompleted()) {
            return interrupt.execute();
        }

        return -1;
    }
}
