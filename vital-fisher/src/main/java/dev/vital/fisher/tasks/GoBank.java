package dev.vital.fisher.tasks;

import dev.vital.fisher.InventoryMethod;
import dev.vital.fisher.VitalFisher;
import dev.vital.fisher.VitalFisherConfig;
import net.runelite.api.Item;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Predicates;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;

public class GoBank implements ScriptTask
{
    VitalFisherConfig config;

    public GoBank(VitalFisherConfig config)
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

        return config.method().equals(InventoryMethod.BANK) && drop_items;
    }

    @Override
    public int execute()
    {
        if (Bank.isOpen())
        {
            Bank.depositAll(Predicates.ids(VitalFisher.items_to_drop));
            return -1;
        }

        var nearest_bank = BankLocation.getNearest();
        if (nearest_bank.getArea().contains(LocalPlayer.get().getWorldLocation()))
        {
            var bank_booth = TileObjects.getNearest(config.bankObject());
            var banker = NPCs.getNearest(config.bankObject());
            if (bank_booth != null)
            {
                bank_booth.interact(config.bankAction());
            }
            else if (banker != null)
            {
                banker.interact(config.bankAction());
            }

            return -5;
        }
        else if (!Movement.isWalking())
        {
            Movement.walkTo(VitalFisher.bank_location);
        }

        return -1;
    }
}