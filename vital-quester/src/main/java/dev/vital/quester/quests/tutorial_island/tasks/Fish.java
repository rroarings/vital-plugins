package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.tasks.CameraTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.VarClientInt;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Tab;
import net.unethicalite.api.widgets.Tabs;
import net.unethicalite.api.widgets.Widgets;

public class Fish implements ScriptTask
{
    VitalQuesterConfig config;

    public Fish(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var widget  = Widgets.get(263, 1);
        if(widget != null) {
            var widget_child = widget. getChild(0);
            if(widget_child != null) {
                return widget_child.getText().contains("This is your inventory.");
            }
        }
        return false;
    }

    CameraTask camera_task = new CameraTask(Rand.nextInt(0, 4));

    @Override
    public int execute()
    {
        var camera_distance = Vars.getVarcInt(VarClientInt.CAMERA_ZOOM_RESIZABLE_VIEWPORT);
        if(camera_distance > 200) {

            if(!Tabs.isOpen(Tab.OPTIONS)) {
                Tabs.open(Tab.OPTIONS);
                return -1;
            }

            var display_menu = Widgets.get(116, 112);
            if(display_menu != null && display_menu.hasAction("Display")) {
                display_menu.interact("Display");
                return -1;
            }

            var slider = Widgets.get(116, 92);
            if(slider != null) {
                Mouse.click(slider.getClickPoint().getAwtPoint(), true);
                return -1;
            }

            return -1;
        }

        if(!camera_task.taskCompleted()) {
            camera_task.moveLeft();
            return -1;
        }
        if(!Inventory.contains(ItemID.RAW_SHRIMPS_2514) && !Tools.isAnimating(5)) {
            NPCs.getNearest(3317).interact("Net");
        }

        return -5;
    }
}
