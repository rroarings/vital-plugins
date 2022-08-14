package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class OpenBank implements ScriptTask
{
    VitalQuesterConfig config;

    public OpenBank(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Follow the path and you will come to the front");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        var booth = TileObjects.getNearest("Bank booth");
        if(booth != null) {

            booth.interact("Use");
        }
        return -5;
    }
}
