package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.*;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.widgets.Widgets;

public class OpenSettings implements ScriptTask
{
    VitalQuesterConfig config;

    public OpenSettings(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Please click on the flashing spanner icon found at the bottom right of your screen.");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        var widget = Widgets.get(164,40);
        if(widget != null) {
            Mouse.click(widget.getClickPoint().getAwtPoint(), true);
        }

        return -2;
    }
}
