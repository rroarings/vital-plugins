package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class StepFour implements ScriptTask
{
    private final WorldPoint dig_four_point = new WorldPoint(3078, 3259, 0);

    VitalQuesterConfig config;

    public StepFour(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Inventory.contains(ItemID.TREASURE_SCROLL_23070);
    }

    @Override
    public int execute() {

        if(!LocalPlayer.get().getWorldLocation().equals(dig_four_point) && !Movement.isWalking()) {
            Movement.walkTo(dig_four_point);
            return -1;
        }

        Inventory.getFirst(ItemID.SPADE).interact("Dig");

        return -1;
    }
}