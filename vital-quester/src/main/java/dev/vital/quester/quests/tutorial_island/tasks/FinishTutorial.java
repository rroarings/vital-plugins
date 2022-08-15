package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class FinishTutorial implements ScriptTask
{
    private final WorldPoint magic_instructor_point = new WorldPoint(3140, 3087, 0);

    VitalQuesterConfig config;

    public FinishTutorial(VitalQuesterConfig config)
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
                return widget_child.getText().contains("You're nearly finished with the tutorial");
            }
        }
        return false;
    }

    DialogTask talk_to_magic_instructor = new DialogTask("Magic Instructor", magic_instructor_point, "Yes, send me to the mainland", "No, I'm not planning to do that.", "Yes.");

    @Override
    public int execute()
    {
        if(!talk_to_magic_instructor.taskCompleted()) {
            return talk_to_magic_instructor.execute();
        }

        return -1;
    }
}
