package dev.vital.quester.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.widgets.Widgets;

public class HandleQuestComplete implements ScriptTask
{
    VitalQuesterConfig config;

    public HandleQuestComplete(VitalQuesterConfig config) {
        this.config = config;
    }
    @Override
    public boolean validate()
    {
        var widget = Widgets.get(WidgetInfo.QUEST_COMPLETED);
        return widget != null && widget.isVisible();
    }

    @Override
    public int execute() {

        return -1;
    }
}
