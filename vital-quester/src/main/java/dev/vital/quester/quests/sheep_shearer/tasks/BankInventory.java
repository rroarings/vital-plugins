package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.BasicTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Predicates;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;

import java.util.ArrayList;
import java.util.List;

public class BankInventory implements ScriptTask
{
    VitalQuesterConfig config;

    public BankInventory(VitalQuesterConfig config)
    {
        this.config = config;
    }

    List<String> excluded_items = new ArrayList<>();

    BasicTask bank_items = new BasicTask(() -> {
       if(Bank.isOpen()) {
            Bank.depositAllExcept(Predicates.names(excluded_items));
        }

        var nearest_bank = BankLocation.getNearest();
        if(nearest_bank.getArea().contains(LocalPlayer.get().getWorldLocation())) {
            var bank_booth = TileObjects.getNearest("Bank booth");
            var banker = NPCs.getNearest("Banker");
            if(bank_booth != null) {
                bank_booth.interact("Bank");
            }
            else if(banker != null) {
                banker.interact("Bank");
            }

            return -5;
        }
        else if(!Movement.isWalking()) {
            Movement.walkTo(nearest_bank);
        }

        return -1;
    });

    @Override
    public boolean validate()
    {
        if(!config.sheepShearerBankInventory()) {
            return false;
        }

        excluded_items = List.of(config.sheepShearerExcludedItems().split(","));
        return !bank_items.taskCompleted() && !Inventory.isEmpty() && (!excluded_items.isEmpty() && (!excluded_items.stream().allMatch(Inventory::contains) || excluded_items.size() != 28 - Inventory.getFreeSlots()));
    }

    @Override
    public int execute() {

        return bank_items.execute();
    }
}
