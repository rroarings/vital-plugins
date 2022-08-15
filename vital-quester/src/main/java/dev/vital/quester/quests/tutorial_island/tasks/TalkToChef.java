package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToChef implements ScriptTask
{
    private final WorldPoint master_chef_point = new WorldPoint(3076, 3085, 0);

    VitalQuesterConfig config;

    public TalkToChef(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Talk to the chef indicated");
            }
        }
        return false;
    }

    DialogTask talk_to_cook = new DialogTask("Master Chef", master_chef_point, (String)null);


    @Override
    public int execute()
    {
        if(!talk_to_cook.taskCompleted()) {
            return talk_to_cook.execute();
        }

        return -1;
    }
}
