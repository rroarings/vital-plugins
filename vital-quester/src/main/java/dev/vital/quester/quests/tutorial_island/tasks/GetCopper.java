package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class GetCopper implements ScriptTask
{
    VitalQuesterConfig config;

    public GetCopper(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Now that you have some tine ore.");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        if(!Inventory.contains(ItemID.TIN_ORE) && !Tools.isAnimating(5)) {
            TileObjects.getFirstAt(new WorldPoint(3085, 9503, 0), "Rocks").interact("Mine");
        }

        return -5;
    }
}
