package dev.vital.quester.quests.cooks_assistant.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.ItemTask;
import dev.vital.quester.tasks.ObjectItemTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;

public class GetFlour implements ScriptTask
{
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
    ObjectItemTask get_wheat = new ObjectItemTask(15507, ItemID.GRAIN, 1, false, "Pick", grain_point);

    int initiali_grain_count = -1;
    BasicTask use_hopper = new BasicTask(() ->
    {
        int count = Inventory.getCount(false, ItemID.GRAIN);
        if(initiali_grain_count == -1 && count > 0) {
            initiali_grain_count = count;
        }

        if(initiali_grain_count != -1) {

            if (initiali_grain_count != Inventory.getCount(false, ItemID.GRAIN)) {
                return 0;
            }
            else {
                return Tools.interactWith(24961, "Fill", hopper_point, Tools.EntityType.TILE_OBJECT);
            }
        }

        return -1;
    });

    BasicTask use_controls = new BasicTask(() ->
    {
        if(Vars.getBit(4920) < 1) {
            return Tools.interactWith(24964, "Operate", hopper_point, Tools.EntityType.TILE_OBJECT);
        }
        else {
            return 0;
        }
    });

    BasicTask get_flour = new BasicTask(() ->
    {
        if(Vars.getBit(4920) > 0) {
            return Tools.interactWith("Flour bin", "Empty", flour_bin_point, Tools.EntityType.TILE_OBJECT);
        }
        else {
            return 0;
        }
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
        else if(!use_controls.taskCompleted()) {
            return use_controls.execute();
        }
        else if(!get_flour.taskCompleted()) {
            return get_flour.execute();
        }

        return -1;
    }
}
