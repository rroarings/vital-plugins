package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.widgets.Widgets;

public class SmithDagger implements ScriptTask
{
    private final WorldPoint master_chef_point = new WorldPoint(3076, 3085, 0);

    VitalQuesterConfig config;

    public SmithDagger(VitalQuesterConfig config)
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
                return widget_child.getText().contains("To smith you'll need a hammer")
                        || widget_child.getText().contains("Now you have the smithing menu open");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        var widget = Widgets.get(312, 9);
        if(widget != null) {

            widget.interact("Smith");
        }
        else {
            TileObjects.getNearest("Anvil").interact("Smith");
            return -5;
        }

        return -6;
    }
}
