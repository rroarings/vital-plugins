package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class GoToCombatInstructor implements ScriptTask
{
    VitalQuesterConfig config;

    public GoToCombatInstructor(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Congratulations, you've made your first weapon");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        TileObjects.getNearest("Gate").interact("Open");

        return -5;
    }
}
