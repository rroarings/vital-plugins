package dev.vital.quester.quests.cooks_assistant.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;

public class GetFlour implements ScriptTask
{
    public enum Steps {
        GET_GRAIN,
        USE_HOPPER,
        USE_CONTROLS,
        GET_FLOUR
    }
    Steps step = Steps.GET_GRAIN;
    private final WorldPoint pot_point = new WorldPoint(3209, 3213, 0);
    private final WorldPoint grain_point = new WorldPoint(3162, 3291, 0);
    private final WorldPoint hopper_point = new WorldPoint(3165, 3307, 2);
    private final WorldPoint flour_bin_point = new WorldPoint(3165, 3306, 0);


    VitalQuesterConfig config;

    public GetFlour(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Inventory.contains(ItemID.POT_OF_FLOUR);
    }

    ItemTask get_pot = new ItemTask(ItemID.POT, 1, false, pot_point);
    ObjectItemTask get_wheat = new ObjectItemTask(ItemID.GRAIN, 1, false, "Pick", pot_point);

    BasicTask use_hopper = new BasicTask(() ->
    {
        if(Vars.getVarcInt(57) == 89707) {
            if(Inventory.contains(ItemID.GRAIN)) {
                return Tools.interactWith(24961, "Fill", hopper_point, Tools.EntityType.TILE_OBJECT);
            }
        }
        else {
            return 0;
        }

        return -1;
    });

    @Override
    public int execute()
    {

        if(!get_pot.taskCompleted()) {
            return get_pot.execute();
        }
        else if(!get_wheat.taskCompleted()) {
            return get_wheat.execute();
        }
        else if(!use_hopper.taskCompleted()) {
            return use_hopper.execute();
        }

        if(!Inventory.contains(ItemID.POT)) {

            if(Tools.interactWith("Pot", "Take", pot_point, Tools.EntityType.TILE_ITEM) == -5) {
                return -5;
            }

            return -1;
        }
        else if(step.equals(Steps.GET_GRAIN)) {
            if(Tools.interactWith("Wheat", "Pick", grain_point, Tools.EntityType.TILE_OBJECT) == -5) {
                step = Steps.USE_HOPPER;
                return -5;
            }
        }
        else if(step.equals(Steps.USE_HOPPER)) {
            if(Inventory.contains(ItemID.GRAIN)) {
                if (Tools.interactWith("Hopper", "Fill", hopper_point, Tools.EntityType.TILE_OBJECT) == -5) {
                    step = Steps.USE_CONTROLS;
                    return -6;
                }
            }
        }
        else if(step.equals(Steps.USE_CONTROLS)) {
            if (Tools.interactWith("Hopper controls", "Operate", hopper_point, Tools.EntityType.TILE_OBJECT) == -5) {
                step = Steps.GET_FLOUR;
                return -8;
            }
        }
        else if(step.equals(Steps.GET_FLOUR)) {
            if (Tools.interactWith("Flour bin", "Empty", flour_bin_point, Tools.EntityType.TILE_OBJECT) == -5) {
                return -7;
            }
        }
        return -1;
    }
}
