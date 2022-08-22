package dev.vital.bankstander.tasks;

import dev.vital.bankstander.Mode;
import dev.vital.bankstander.VitalBankStanderConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Production;

public class JugsOfWine implements ScriptTask
{
    VitalBankStanderConfig config;

    public JugsOfWine(VitalBankStanderConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return this.config.mode().equals(Mode.JUGS_OF_WINE);
    }

    @Override
    public int execute()
    {
        if (Tools.isAnimating(config.animationDelta()))
        {
            return -1;
        }

        if (Production.isOpen())
        {
            Keyboard.sendSpace();
            return -3;
        }

        if (Inventory.getCount(false, ItemID.JUG_OF_WINE) == 14 || Inventory.getCount(false, ItemID.UNFERMENTED_WINE) == 14)
        {
            if (Tools.openNearestBank())
            {
                Bank.depositInventory();
            }

            return -1;
        }

        if (Inventory.contains(ItemID.JUG_OF_WATER) && Inventory.contains(ItemID.GRAPES))
        {
            if (Bank.isOpen())
            {
                Bank.close();
                return -1;
            }

            Inventory.getFirst(ItemID.JUG_OF_WATER).useOn(Inventory.getFirst(ItemID.GRAPES));
            return -3;
        }
        else
        {
            if (Tools.openNearestBank())
            {
                if (Inventory.getCount(false, ItemID.JUG_OF_WATER) != 14)
                {
                    Bank.withdraw(ItemID.JUG_OF_WATER, 14, Bank.WithdrawMode.ITEM);
                    Time.sleepTicksUntil(() -> Inventory.getCount(false, ItemID.JUG_OF_WATER) == 14, 5);
                }
                else if (Inventory.getCount(false, ItemID.GRAPES) != 14)
                {
                    Bank.withdraw(ItemID.GRAPES, 14, Bank.WithdrawMode.ITEM);
                    Time.sleepTicksUntil(() -> Inventory.getCount(false, ItemID.GRAPES) == 14, 5);
                }
                return 100;
            }
        }

        return -1;
    }
}
