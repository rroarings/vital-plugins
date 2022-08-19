package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.quests.QuestVarbits;
import net.unethicalite.api.widgets.Widgets;

public class ClickJewels implements ScriptTask
{
    VitalQuesterConfig config;

    public ClickJewels(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 100;
    }
    BasicTask cut_fireplace = new BasicTask(() -> {

        var jewel_menu = Widgets.get(555, 2);
        if(jewel_menu != null) {

            var step = Vars.getBit(4050);
            if(step == 0) {
                Mouse.click(Widgets.get(555, 19).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 1) {
                Mouse.click(Widgets.get(555, 3).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 2) {
                Mouse.click(Widgets.get(555, 11).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 3) {
                Mouse.click(Widgets.get(555, 23).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 4) {
                Mouse.click(Widgets.get(555, 7).getClickPoint().getAwtPoint(), true);
            }
            else if(step == 5) {
                Mouse.click(Widgets.get(555, 15).getClickPoint().getAwtPoint(), true);
            return 0;
            }
            return -3;
        }

        var fireplace = TileObjects.getNearest("Fireplace");
        if (fireplace.hasAction("Search")) {
            fireplace.interact("Search");
            return -3;
        }

        return -5;
    });

    @Override
    public int execute() {

        if (!cut_fireplace.taskCompleted()) {
            return cut_fireplace.execute();
        }
        return -1;
    }
}
