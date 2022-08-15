package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToAccountGuide implements ScriptTask
{
    private final WorldPoint master_chef_point = new WorldPoint(3126, 3124, 0);

    VitalQuesterConfig config;

    public TalkToAccountGuide(VitalQuesterConfig config)
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
                return widget_child.getText().contains("The guide here will tell you");
            }
        }
        return false;
    }

    DialogTask talk_to_account_guide = new DialogTask("Account Guide", master_chef_point, (String)null);


    @Override
    public int execute()
    {
        if(!talk_to_account_guide.taskCompleted()) {
            return talk_to_account_guide.execute();
        }

        return -1;
    }
}
