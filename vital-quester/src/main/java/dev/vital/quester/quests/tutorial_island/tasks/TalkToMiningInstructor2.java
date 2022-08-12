package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.*;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToMiningInstructor2 implements ScriptTask
{
    private final WorldPoint quest_guide_point = new WorldPoint(3080, 9505, 0);

    VitalQuesterConfig config;

    public TalkToMiningInstructor2(VitalQuesterConfig config)
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
                return widget_child.getText().contains("You've made a bronze bar!");
            }
        }
        return false;
    }

    DialogTask talk_to_mining_instructor = new DialogTask("Mining Instructor", quest_guide_point, (String)null);

    @Override
    public int execute()
    {
        if(!talk_to_mining_instructor.taskCompleted()) {
            return talk_to_mining_instructor.execute();
        }

        return -1;
    }
}
