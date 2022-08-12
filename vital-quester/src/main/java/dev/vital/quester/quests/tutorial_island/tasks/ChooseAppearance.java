package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.widgets.Widgets;

import java.util.ArrayList;
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

        var name = Game.getGameAccount().getUsername();
        name = name.concat(name);

        for(var letter : name.toCharArray()) {
            stuff.add(new AppearanceWidget(Character.getNumericValue(letter)));
        }

        return stuff;
    }

    List<AppearanceWidget> stuff = new ArrayList<>();

    @Override
    public int execute()
    {
        if(stuff.stream().allMatch(x -> x.completed)) {

            var widget = Widgets.get(679, 68);
            if(widget != null) {
                widget.interact("Confirm");
            }

            return -5;
        }

       int widget_begin = 0;
        if(stuff.isEmpty()) {
            stuff = generateSeedFromName();
        }
        else {
            for(var appearance : stuff) {
                widget_begin = widget_begin + 4;

                var widget = Widgets.get(679, 13 + widget_begin);
                widget_begin = widget_begin + 4;
                if(appearance.completed) {
                    continue;
                }

                while(appearance.current_times < appearance.wanted_times) {
                    widget.interact("Select");
                    appearance.current_times++;
                    Time.sleep(Rand.nextInt(120, 220));
                }

                appearance.completed = true;
            }
        }
        return -5;
    }
}
