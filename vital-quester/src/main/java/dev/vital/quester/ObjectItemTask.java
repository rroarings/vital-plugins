package dev.vital.quester;

import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.SceneEntity;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;

public class ObjectItemTask {

    boolean task_completed;
    int object_id;
    int item_id;
    int quantity;
    boolean stack;
    String action;
    WorldPoint point;
    WorldPoint exact_point;

    public ObjectItemTask(int object_id, int item_id, int quantity, boolean stack, String action, WorldPoint point) {
        this.task_completed = false;
        this.object_id = object_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.stack = stack;
        this.action = action;
        this.point = point;
        this.exact_point = null;
    }
    public ObjectItemTask(int object_id, int item_id, int quantity, boolean stack, String action, WorldPoint point, WorldPoint exact_point) {
        this.task_completed = false;
        this.object_id = object_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.stack = stack;
        this.action = action;
        this.point = point;
        this.exact_point = exact_point;
    }
    public int execute() {

        if(Inventory.getCount(this.stack, this.item_id) == this.quantity) {
            this.task_completed = true;
            return -1;
        }

        SceneEntity entity;
        if(this.exact_point == null) {
            entity = TileObjects.getNearest(this.object_id);
        }
        else {
            entity = TileObjects.getFirstAt(this.exact_point, x -> x.hasAction(this.action) && x.getId() == this.object_id);
        }

        if(entity != null && Reachable.isInteractable(entity)) {
            entity.interact(this.action);
            return -5;
        }
        else if(!Movement.isWalking()){
            Movement.walkTo(this.point);
        }

        return -1;
    }

    public boolean taskCompleted() {
        return task_completed;
    }
}