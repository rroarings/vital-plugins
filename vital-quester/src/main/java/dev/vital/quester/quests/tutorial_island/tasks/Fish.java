package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.tasks.CameraTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Inventory;
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
