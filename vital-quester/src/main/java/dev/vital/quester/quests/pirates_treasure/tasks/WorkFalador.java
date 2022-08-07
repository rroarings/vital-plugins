package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.movement.Movement;

public class WorkFalador implements ScriptTask
{
    private final WorldPoint pirate_point = new WorldPoint(3054, 3253, 0);

    VitalQuesterConfig config;

    public WorkFalador(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Inventory.contains(ItemID.PIRATE_MESSAGE);
    }

    VitalTask dig = new VitalTask(() ->
    {
        if(!Movement.isWalking()) {
            if(Movement.walkTo(2999,3383, 0)) {
                if(LocalPlayer.get().getWorldLocation().equals(new WorldPoint(2999,3383, 0))) {
                    Inventory.getFirst(ItemID.SPADE).interact("Dig");
                    return true;
                }
            }
        }

        return false;
    });
    VitalTask dig2 = new VitalTask(() ->
    {
        if(!Movement.isWalking()) {
            if(Movement.walkTo(2999,3383, 0)) {
                if(LocalPlayer.get().getWorldLocation().equals(new WorldPoint(2999,3383, 0))) {
                    Inventory.getFirst(ItemID.SPADE).interact("Dig");
                    return true;
                }
            }
        }

        return false;
    });
    VitalTask wait = new VitalTask(() ->
    {
        if (LocalPlayer.get().getWorldLocation().equals(new WorldPoint(3005, 3383, 0))) {
            Time.sleepTicks(6);
            return true;
        }
        else {
            Movement.walkTo(3005, 3383, 0);
        }

        return false;
    });

    @Override
    public int execute() {

        if(!dig.taskCompleted()) {
            dig.execute();
        }
        else if(!wait.taskCompleted()) {

            wait.execute();
        }
        else if(!dig2.taskCompleted()) {
            dig2.execute();
        }


        return -1;
    }
}
