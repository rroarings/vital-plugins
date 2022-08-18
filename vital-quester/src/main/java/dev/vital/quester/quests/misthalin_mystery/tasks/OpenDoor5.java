package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.WalkTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;
import net.unethicalite.api.widgets.Widgets;

public class OpenDoor5 implements ScriptTask
{
    WorldPoint door_point = new WorldPoint(1631, 4837, 0);
    WorldPoint door_point2 = new WorldPoint(1640, 4828, 0);

    VitalQuesterConfig config;

    public OpenDoor5(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 95;
    }
    BasicTask open_door = new BasicTask(() -> {
        if(Tools.interactWith(30117, "Open", door_point, Tools.EntityType.TILE_OBJECT) == -5) {
            return 0;
        }

        return -5;
    });

    WalkTask walk = new WalkTask(new WorldPoint(1643, 4833, 0));

    BasicTask cut_fireplace = new BasicTask(() -> {

        var jewel_menu = Widgets.get(555, 2);
        if(jewel_menu != null) {

            var step = Vars.getBit(4060);
            if(step == 0) {
                Mouse.click(Widgets.get(555, 19).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 1) {
                Mouse.click(Widgets.get(555, 3).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 2) {
                Mouse.click(Widgets.get(555, 11).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 3) {
                Mouse.click(Widgets.get(555, 23).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 4) {
                Mouse.click(Widgets.get(555, 7).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 5) {
                Mouse.click(Widgets.get(555, 15).getClickPoint().getAwtPoint(), true);
            }
            return -3;
        }

        var fireplace = TileObjects.getNearest("Fireplace");
        if (fireplace.hasAction("Search")) {
            fireplace.interact("Search");
        } else {
            Inventory.getFirst(ItemID.KNIFE).useOn(TileObjects.getNearest("Fireplace"));
        }

        return 0;
    });

    @Override
    public int execute() {

        if (!open_door.taskCompleted()) {
            return open_door.execute();
        }
        else if (!walk.taskCompleted()) {
            return walk.execute();
        }
        else if (!cut_fireplace.taskCompleted()) {
            return cut_fireplace.execute();
        }
        return -1;
    }
}
