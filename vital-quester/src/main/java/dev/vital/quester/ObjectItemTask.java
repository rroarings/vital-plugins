package dev.vital.quester;

import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.SceneEntity;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;

public class ObjectItemTask {
    public interface TaskFunction {
        int execute();
    }

    boolean task_completed;
    int id;
    int quantity;
    boolean stack;
    String action;
    WorldPoint point;
    WorldPoint exact_point;

    public ObjectItemTask(int id, int quantity, boolean stack, String action, WorldPoint point) {
        this.task_completed = false;
        this.id = id;
        this.quantity = quantity;
        this.stack = stack;
        this.action = action;
        this.point = point;
        this.exact_point = null;
    }
    public ObjectItemTask(int id, int quantity, boolean stack, String action, WorldPoint point, WorldPoint exact_point) {
        this.task_completed = false;
        this.id = id;
        this.quantity = quantity;
        this.stack = stack;
        this.action = action;
        this.point = point;
        this.exact_point = exact_point;
    }
    public int execute() {

        SceneEntity entity;
        if(this.exact_point == null) {
            entity = TileObjects.getNearest(this.id);
        }
        else {
            entity = TileObjects.getFirstAt(this.exact_point, x -> x.hasAction(this.action) && x.getId() == this.id);
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
        return Inventory.getCount(this.stack, this.id) == this.quantity;
    }
}