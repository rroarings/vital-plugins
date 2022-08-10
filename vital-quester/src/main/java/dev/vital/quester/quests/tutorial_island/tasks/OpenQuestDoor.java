package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Widgets;

public class OpenQuestDoor implements ScriptTask
{
    VitalQuesterConfig config;
    WorldPoint quest_door = new WorldPoint(3086, 3126, 0);

    public OpenQuestDoor(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var widget  = Widgets.get(263, 1);
        if(widget != null) {
            var widget_child = widget. getChild(0);
            if(widget_child != null) {
                return widget_child.getText().contains("Follow the path to the next guide. When");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        var door = TileObjects.getFirstAt(quest_door, "Door");
        if(door != null && Reachable.isInteractable(door)) {
            door.interact("Open");
            return -5;
        }
       else if(!Movement.isWalking()){
           Movement.walkTo(quest_door);
        }
       
        return -1;
    }
}
