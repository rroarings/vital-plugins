package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tasks.NPCItemTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class ShearSheep implements ScriptTask
{
    private final WorldPoint sheep_point = new WorldPoint(3199, 3268, 0);

    private final WorldArea sheep_point2 = new WorldArea(3192, 3256, 23, 18, 0);

    VitalQuesterConfig config;

    public ShearSheep(VitalQuesterConfig config)
    {
        this.config = config;
    }

    NPCItemTask sheer_sheap = new NPCItemTask(2693, ItemID.WOOL, 20, false, "Shear", sheep_point);

    @Override
    public boolean validate()
    {
        return !sheer_sheap.taskCompleted() && Inventory.getCount(false, ItemID.WOOL) < 20 && !Inventory.contains(ItemID.BALL_OF_WOOL);
    }

    @Override
    public int execute() {

        if(!sheer_sheap.taskCompleted()) {

            if(sheep_point2.contains(LocalPlayer.get())) {
                return sheer_sheap.execute();
            }
            else if(!Movement.isWalking()) {
                Movement.walkTo(sheep_point);
            }
        }

        return -1;
    }
}
