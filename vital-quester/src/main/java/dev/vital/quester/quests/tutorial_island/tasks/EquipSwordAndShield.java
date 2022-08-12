package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class EquipSwordAndShield implements ScriptTask
{
    VitalQuesterConfig config;

    public EquipSwordAndShield(VitalQuesterConfig config)
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
                return widget_child.getText().contains("To unequip an item");
            }
        }
        return false;
     }

    @Override
    public int execute()
    {
        if(!Equipment.contains(ItemID.BRONZE_SWORD)) {
            Mouse.click(Widgets.get(149, 0 ,8).getClickPoint().getAwtPoint(), true);
        }
        else if(!Equipment.contains(ItemID.WOODEN_SHIELD)) {
            Mouse.click(Widgets.get(149, 0 ,10).getClickPoint().getAwtPoint(), true);
        }

        return -5;
    }
}
