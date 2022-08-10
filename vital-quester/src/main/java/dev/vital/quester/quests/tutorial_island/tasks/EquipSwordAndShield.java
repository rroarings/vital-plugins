package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.unethicalite.api.entities.TileObjects;
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
            Inventory.getFirst(ItemID.BRONZE_SWORD).interact("Equip");
        }
        else if(!Equipment.contains(ItemID.WOODEN_SHIELD)) {
            Inventory.getFirst(ItemID.WOODEN_SHIELD).interact("Equip");
        }

        return -5;
    }
}
