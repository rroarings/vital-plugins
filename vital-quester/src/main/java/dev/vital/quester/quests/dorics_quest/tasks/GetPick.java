package dev.vital.quester.quests.dorics_quest.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.Skill;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;

public class GetPick implements ScriptTask
{
    VitalQuesterConfig config;

    public GetPick(VitalQuesterConfig config)
    {
        this.config = config;
    }

    BasicTask bank_items = new BasicTask(() -> {

        if(Inventory.contains(x->x.getName().contains("pickaxe"))) {
            Inventory.getFirst(x->x.getName().contains("pickaxe")).interact("Wield");
            return -1;
        }

        if(Bank.isOpen() && Bank.contains(x -> x.getName().contains("pickaxe"))) {
            Bank.withdraw(Bank.getFirst(x -> x.getName().contains("pickaxe")).getId(),1, Bank.WithdrawMode.DEFAULT);
            return -1;
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
        return !config.useGrandExchange() && Skills.getLevel(Skill.MINING) < 15 && !Tools.localHas(x -> x.getName().contains("pickaxe"));
    }

    @Override
    public int execute() {

        return bank_items.execute();
    }
}
