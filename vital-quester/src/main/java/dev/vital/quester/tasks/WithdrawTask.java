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

public class WithdrawTask {

    static class WithdrawItems {
        public int id;
        public int amount;
        public boolean stack;
        public Bank.WithdrawMode mode;
        public boolean worked;
        WithdrawItems(int id, int amount, boolean stack, Bank.WithdrawMode mode) {
            this.id = id;
            this.amount = amount;
            this.stack = stack;
            this.mode = mode;
            this.worked = false;
        }
    }

    boolean task_completed;
    List<WithdrawItems> items;

    public WithdrawTask(List<WithdrawItems> items) {
        this.task_completed = false;
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

        if(this.items.stream().allMatch(x -> x.worked)) {
            Bank.close();
            Time.sleepTicks(2);
            return 0;
        }

        if(Bank.isOpen()) {

            if(handleBankTips()) {
                return -1;
            }

            for(var item : this.items) {

                var needed_amount = Math.abs(Inventory.getCount(item.stack, item.id) - item.amount);
                if(Inventory.getCount(item.stack, item.id) < item.amount && Bank.contains(item.id) && Bank.getCount(item.stack, item.id) >= needed_amount) {
                    Bank.withdraw(item.id, needed_amount, item.mode);
                    Time.sleepTick();
                }

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
