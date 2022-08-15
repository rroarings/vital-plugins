package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class WorkFalador implements ScriptTask
{
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

    BasicTask dig = new BasicTask(() ->
    {
        if(!LocalPlayer.get().getWorldLocation().equals(new WorldPoint(2999, 3383, 0))) {
            if (!Movement.isWalking()) {
                Movement.walkTo(2999, 3383, 0);
            }
        }
        else {
            Inventory.getFirst(ItemID.SPADE).interact("Dig");
            return 0;
        }
        return -1;
    });

    BasicTask dig2 = new BasicTask(() ->
    {
        if(!LocalPlayer.get().getWorldLocation().equals(new WorldPoint(2999, 3383, 0))) {
            if (!Movement.isWalking()) {
                Movement.walkTo(2999, 3383, 0);
            }
        }
        else {
            Inventory.getFirst(ItemID.SPADE).interact("Dig");
            return 0;
        }

        return -1;
    });

    BasicTask wait = new BasicTask(() ->
    {
        if (LocalPlayer.get().getWorldLocation().equals(new WorldPoint(3010, 3383, 0))) {
            Time.sleepTicks(6);
            return 0;
        }
        else {
            Movement.walkTo(3010, 3383, 0);
        }

        return -1;
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
