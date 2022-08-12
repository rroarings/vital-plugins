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

public class MakeDough implements ScriptTask
{
    VitalQuesterConfig config;

    public MakeDough(VitalQuesterConfig config)
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
                return widget_child.getText().contains("This is the base for many meals.");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        Inventory.getFirst(ItemID.POT_OF_FLOUR_2516).useOn(Inventory.getFirst(ItemID.BUCKET_OF_WATER));

        return -2;
    }
}
