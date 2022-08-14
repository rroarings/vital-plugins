package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.widgets.Widgets;

import java.util.ArrayList;
import java.util.Arrays;
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
            if(widget != null) {
                return widget.getText().contains("Character Creator");
            }
        }
        return false;
    }
    public class AppearanceWidget {

       public boolean completed;
        public int wanted_times;
        public int current_times;
        AppearanceWidget(int i) {
            this.completed = false;
            this.wanted_times = i;
            this.current_times = 0;
        }
    }

    List<AppearanceWidget> generateSeedFromName() {

        List<AppearanceWidget> stuff = new ArrayList<>();

        var name = LocalPlayer.getUsername();
        if(name.length() < 12) {
            name = name.concat(name);
        }
        name = name.substring(0, Math.min(name.length(), 12));

        for(var letter : name.toCharArray()) {
            stuff.add(new AppearanceWidget(Character.getNumericValue(letter)));
        }

        return stuff;
    }

    List<Integer> widgets_list = List.of(13, 17, 21, 25, 29, 33, 37, 44, 48, 52, 56, 60);

    @Override
    public int execute() {
       var stuff = generateSeedFromName();

        int widget_begin = 0;

        for (var appearance : stuff) {

            if (appearance.completed) {
                continue;
            }

            var widget = Widgets.get(679, widgets_list.get(widget_begin));

            while (appearance.current_times < appearance.wanted_times) {
                widget.interact("Select");
                appearance.current_times++;
                Time.sleep(Rand.nextInt(200, 1200));
            }

            appearance.completed = true;
            widget_begin++;
        }

        if (stuff.stream().allMatch(x -> x.completed)) {

            var widget = Widgets.get(679, 68);
            if (widget != null) {
                 widget.interact("Confirm");
            }

            return -5;
        }

        return -5;
    }
}
