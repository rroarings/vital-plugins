package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.*;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToCombatInstructor implements ScriptTask
{
    private final WorldPoint combat_instructor_point = new WorldPoint(3106, 9508, 0);

    VitalQuesterConfig config;

    public TalkToCombatInstructor(VitalQuesterConfig config)
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
                return widget_child.getText().contains("In this area you will find out about melee");
            }
        }
        return false;
    }

    DialogTask talk_to_combat_instructor = new DialogTask("Combat Instructor", combat_instructor_point, (String)null);


    @Override
    public int execute()
    {
        if(!talk_to_combat_instructor.taskCompleted()) {
            return talk_to_combat_instructor.execute();
        }

        return -1;
    }
}
