package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class MakeFire implements ScriptTask
{
    VitalQuesterConfig config;

    public MakeFire(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Now that you have some logs,");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        if(Inventory.contains(ItemID.LOGS_2511)) {
            Inventory.getFirst(ItemID.LOGS_2511).useOn(Inventory.getFirst(ItemID.TINDERBOX));
        }

        return -10;
    }
}
