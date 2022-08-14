package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.CameraTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.widgets.Widgets;

public class OpenMagic implements ScriptTask
{
    VitalQuesterConfig config;

    public OpenMagic(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Open up the magic interface");
            }
        }
        return false;
    }

    CameraTask camera_task = new CameraTask(Rand.nextInt(0, 4));

    @Override
    public int execute()
    {
        if(!camera_task.taskCompleted()) {
            camera_task.moveRight();
            return -2;
        }

        var widget = Widgets.get(164,57);
        if(widget != null) {
            widget.interact("Magic");
        }

        return -2;
    }
}
