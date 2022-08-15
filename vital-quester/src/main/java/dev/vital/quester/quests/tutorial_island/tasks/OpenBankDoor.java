package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class OpenBankDoor implements ScriptTask
{
    VitalQuesterConfig config;

    public OpenBankDoor(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Polls are run");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        var door = TileObjects.getFirstAt(new WorldPoint(3125, 3124, 0), "Door");
        if(door != null) {
            door.interact("Open");
        }

        return -5;
    }
}
