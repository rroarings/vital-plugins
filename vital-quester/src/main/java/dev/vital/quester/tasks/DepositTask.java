package dev.vital.quester.tasks;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;
import net.unethicalite.api.widgets.Widgets;

import java.util.List;

public class DepositTask {

    static class DepositItems {
        public int id;
        public int amount;
        public boolean stack;
        public boolean worked;
        DepositItems(int id, int amount, boolean stack) {
            this.id = id;
            this.amount = amount;
            this.stack = stack;
            this.worked = false;
        }
        DepositItems() {
            this.id = 0;
            this.amount = 0;
            this.stack = false;
            this.worked = false;
        }
    }


    boolean task_completed;
    public boolean deposit_all;
    List<DepositItems> items;

    public DepositTask(List<DepositItems> items) {
        this.task_completed = false;
        this.deposit_all = items == null;
        this.items = items;
    }

    private boolean handleBankTips() {

        var close_bank = Widgets.get(664, 29, 0);
        if (close_bank != null && close_bank.hasAction("Close")) {
            close_bank.interact("Close");
            return true;
        }

        return false;
    }

    public int execute() {

        if(this.items != null && this.items.stream().allMatch(x -> x.worked)) {
            return 0;
        }

        if(Bank.isOpen()) {

            if(handleBankTips()) {
                return -1;
            }

            if(this.deposit_all) {
                Bank.depositInventory();
                return -1;
            }

            for(var item : this.items) {
                if(Inventory.contains(item.id) && Inventory.getCount(item.stack, item.id) == item.amount) {
                    Bank.deposit(item.id, item.amount);
                }
                Time.sleepTick();
                item.worked = true;
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
    }

    public boolean taskCompleted() {
        return this.task_completed;
    }
}
