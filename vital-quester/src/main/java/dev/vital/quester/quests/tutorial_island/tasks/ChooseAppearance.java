package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.widgets.Widgets;

import java.util.List;

public class ChooseAppearance implements ScriptTask
{
    VitalQuesterConfig config;

    public ChooseAppearance(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var widget  = Widgets.get(679, 3, 1);
        if(widget != null) {

            return widget.getText().contains("Character Creator");
        }
        return false;
    }

    List<Integer> widgets_list = List.of(13, 17, 21, 25, 29, 33, 37, 44, 48, 52, 56, 60);

    @Override
    public int execute() {

        for (var widgetz : widgets_list) {

            var widget = Widgets.get(679, widgetz);

            for (int i = 0; i < Rand.nextInt(0, 20); i++) {
                widget.interact("Select");
                Time.sleep(Rand.nextInt(140, 1200));
            }
        }

        var widget = Widgets.get(679, 68);
        if (widget != null) {
            widget.interact("Confirm");
        }

        return -5;
    }
}
