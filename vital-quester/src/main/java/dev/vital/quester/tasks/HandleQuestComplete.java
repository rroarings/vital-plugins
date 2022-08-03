package dev.vital.quester.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.widgets.Widgets;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static net.runelite.api.widgets.WidgetInfo.QUEST_COMPLETED;

public class HandleQuestComplete implements ScriptTask
{
    VitalQuesterConfig config;

    public HandleQuestComplete(VitalQuesterConfig config) {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var quest_completed_x = Widgets.get(QUEST_COMPLETED);
        return quest_completed_x != null && !quest_completed_x.isHidden();
    }

    @Override
    public int execute() {

        Widgets.get(153, 16).interact("Close");

        return -1;
    }
}
