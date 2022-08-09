package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class Fish implements ScriptTask
{
    private final WorldPoint gielinor_guide_point = new WorldPoint(3094, 3107, 0);

    VitalQuesterConfig config;

    public Fish(VitalQuesterConfig config)
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
                return widget_child.getText().contains("This is your inventory.");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        if(!Inventory.contains(ItemID.RAW_SHRIMPS_2514) && !Tools.isAnimating(5)) {
            NPCs.getNearest(3317).interact("Net");
        }

        return -5;
    }
}
