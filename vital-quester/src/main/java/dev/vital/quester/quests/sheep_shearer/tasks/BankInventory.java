package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.tasks.BasicTask;
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
import net.unethicalite.api.widgets.Widgets;

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

      if(!excluded_items.isEmpty()) {
            excluded_items = List.of(config.sheepShearerExcludedItems().split(","));
            if(excluded_items.stream().allMatch(Inventory::contains) && excluded_items.size() == 28 - Inventory.getFreeSlots()) {
                return 0;
            }
        }

       if(Bank.isOpen()) {

           var close_bank = Widgets.get(664, 29, 0);
           if (close_bank != null && close_bank.hasAction("Close")) {
               close_bank.interact("Close");
               return -3;
           }

           if(excluded_items.isEmpty()) {
               Bank.depositInventory();
           }
           else {
               Bank.depositAllExcept(Predicates.names(excluded_items));
           }

           return -2;
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
        if(config.sheepShearerBankInventory() && Inventory.isEmpty() && !bank_items.taskCompleted()) {
            bank_items.setCompletionFlag(true);
        }

        return config.sheepShearerBankInventory() && !bank_items.taskCompleted() && !Inventory.isEmpty();
    }

    @Override
    public int execute() {

        return bank_items.execute();
    }
}
