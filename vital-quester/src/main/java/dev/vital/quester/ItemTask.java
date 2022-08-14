package dev.vital.quester;

import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;

public class ItemTask {

    boolean task_completed;
    int id;
    int quantity;
    boolean stack;
    WorldPoint point;

    public ItemTask(int id, int quantity, boolean stack, WorldPoint point) {
        this.task_completed = false;
        this.id = id;
        this.quantity = quantity;
        this.stack = stack;
        this.point = point;
    }

    public int execute() {

        if(Inventory.getCount(this.stack, this.id) == this.quantity) {
            task_completed = true;
            return -1;
        }

        var item = TileItems.getNearest(id);
        if(item != null && Reachable.isInteractable(item)) {
            item.interact("Take");
            return -5;
        }
        else if(!Movement.isWalking()){
            Movement.walkTo(point);
        }

        return -1;
    }

    public boolean taskCompleted() {
        return this.task_completed;
    }
}
