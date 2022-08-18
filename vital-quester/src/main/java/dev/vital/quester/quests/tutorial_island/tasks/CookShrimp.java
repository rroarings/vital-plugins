package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class CookShrimp implements ScriptTask
{
    VitalQuesterConfig config;

    public CookShrimp(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Now it's time to get cooking.");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        if(Inventory.contains(ItemID.RAW_SHRIMPS_2514) && !Tools.isAnimating(5)) {
            Inventory.getFirst(ItemID.RAW_SHRIMPS_2514).useOn(TileObjects.getNearest("Fire"));
        }

        return -5;
    }
}
