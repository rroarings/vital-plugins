package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.widgets.Widgets;

public class ChooseName2 implements ScriptTask
{
    VitalQuesterConfig config;

    public ChooseName2(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var widget  = Widgets.get(558, 13);
        if(widget != null) {

            return widget.getText().contains("Sorry, the display name");
        }
        return false;
    }

    @Override
    public int execute()
    {
        var random_name  = Widgets.get(558, 15);
        if(random_name != null) {

            Mouse.click(random_name.getClickPoint().getAwtPoint(), true);
        }
        return -5;
    }
}
