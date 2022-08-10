package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class OpenChefDoor implements ScriptTask
{
    VitalQuesterConfig config;

    public OpenChefDoor(VitalQuesterConfig config)
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
                return widget_child.getText().contains("Follow the path until you get to the door");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        TileObjects.getFirstAt(new WorldPoint(3079, 3084, 0), "Door").interact("Open");

        return -5;
    }
}
