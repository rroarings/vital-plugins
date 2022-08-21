package dev.vital.fisher.tasks;

import dev.vital.fisher.InventoryMethod;
import dev.vital.fisher.VitalFisher;
import dev.vital.fisher.VitalFisherConfig;
import net.runelite.api.Item;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;

public class GoDrop implements ScriptTask
{
    VitalFisherConfig config;

    public GoDrop(VitalFisherConfig config)
    {
        this.config = config;
    }

    boolean drop_items = false;

    @Override
    public boolean validate()
    {
        if (Inventory.isFull())
        {
            drop_items = true;
        }
        else if (Inventory.query().results().stream().noneMatch((Item item) -> VitalFisher.items_to_drop.stream().anyMatch(x -> x == item.getId())))
        {
            drop_items = false;
        }

        return config.method().equals(InventoryMethod.DROP) && drop_items;
    }

    @Override
    public int execute()
    {
        Inventory.query().results().forEach((Item item) -> {
            if (VitalFisher.items_to_drop.stream().anyMatch(x -> x == item.getId()))
            {
                item.drop();
                Time.sleep(150, 300);
            }
        });

        return -1;
    }
}
