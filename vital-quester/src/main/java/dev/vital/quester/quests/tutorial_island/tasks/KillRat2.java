package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Tab;
import net.unethicalite.api.widgets.Tabs;
import net.unethicalite.api.widgets.Widgets;

public class KillRat2 implements ScriptTask
{
    VitalQuesterConfig config;

    public KillRat2(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Now you have a bow");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        if(!Tabs.isOpen(Tab.INVENTORY)) {
            Tabs.open(Tab.INVENTORY);
            return -2;
        }
        if(!Equipment.contains(ItemID.SHORTBOW)) {
            Mouse.click(Widgets.get(149, 0 ,10).getClickPoint().getAwtPoint(), true);
        }
        else if(!Equipment.contains(ItemID.BRONZE_ARROW)) {
            Mouse.click(Widgets.get(149, 0 ,11).getClickPoint().getAwtPoint(), true);
        }
        else {
            var rat = NPCs.getNearest(x -> (x.getInteracting() == null || x.getInteracting() == LocalPlayer.get()) && x.getName().equals("Giant rat"));
            if(rat != null && !Tools.isAnimating(5)) {
                rat.interact("Attack");
            }
        }

        return -5;
    }
}
