package dev.vital.quester.handlers;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class HandleLamp implements ScriptTask
{
    VitalQuesterConfig config;

    public HandleLamp(VitalQuesterConfig config) {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return config.handleLamp() && Inventory.getFirst(x -> x.hasAction("Rub") && x.getName().contains("lamp")) != null;
    }

    @Override
    public int execute() {

        var choose_skill_lamp_menu = Widgets.get(240, 0);
        if(choose_skill_lamp_menu != null) {
            var wanted_skill_widget = choose_skill_lamp_menu.getChild(config.lampSkill().ordinal() + 2);
            if(wanted_skill_widget != null) {
                wanted_skill_widget.interact(config.lampSkill().getName());
                return -5;
            }
            return -1;
        }

        var item = Inventory.getFirst(x -> x.hasAction("Rub") && x.getName().contains("lamp"));
        if(item != null) {
            item.interact("Rub");
            return -5;
        }

        return -1;
    }
}
