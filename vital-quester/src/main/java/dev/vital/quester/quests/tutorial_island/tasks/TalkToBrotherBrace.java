package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToBrotherBrace implements ScriptTask
{
    private final WorldPoint brother_brace_point = new WorldPoint(3127, 3107, 0);

    VitalQuesterConfig config;

    public TalkToBrotherBrace(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Follow the path to the chapel");
            }
        }
        return false;
    }

    DialogTask talk_to_brother_brace = new DialogTask("Brother Brace", brother_brace_point, (String)null);


    @Override
    public int execute()
    {
        if(!talk_to_brother_brace.taskCompleted()) {
            return talk_to_brother_brace.execute();
        }

        return -1;
    }
}
