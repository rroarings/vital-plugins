package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.widgets.Widgets;

public class ChooseName3 implements ScriptTask
{
    VitalQuesterConfig config;

    public ChooseName3(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var widget  = Widgets.get(558, 13);
        if(widget != null) {

            return widget.getText().contains("Great!");
        }

        return false;
    }

    @Override
    public int execute()
    {
        var random_name  = Widgets.get(558, 18);
        if(random_name != null) {

            random_name.interact("Set name");
        }

        return -5;
    }
}
