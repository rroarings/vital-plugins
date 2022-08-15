package dev.vital.quester.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Widgets;
import net.unethicalite.client.Static;

public class HandleGrave implements ScriptTask
{
    VitalQuesterConfig config;

    public HandleGrave(VitalQuesterConfig config) {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var grave_icon = Widgets.get(164, 20);
        return config.handleGrave() && grave_icon != null && grave_icon.hasAction("Grave info");
    }

    WorldPoint dest = null;

    @Override
    public int execute() {

        var grave_npc = NPCs.getNearest(NpcID.GRAVE);
        if (grave_npc != null && Reachable.isInteractable(grave_npc) && LocalPlayer.get().getWorldLocation().distanceTo2D(grave_npc.getWorldLocation()) < 4) {
            Widget closeWorldMap = Widgets.get(595, 38);
            if (closeWorldMap != null && closeWorldMap.hasAction("Close"))
            {
                closeWorldMap.interact("Close");
            }
            else {
                grave_npc.interact("Loot");
            }
            return -5;
        }

        var map = Widgets.get(595, 5);
        var globe = Widgets.get(160, 53);

        if(map == null) {
            if(globe != null) {
                globe.interact("Floating World Map");
            }

            return -3;
        }

        var grave = Widgets.get(595, 9, 1);
        if(grave == null || grave.getName().equals("Gravestone")) {
            return -1;
        }

        if(grave.getSpriteId() == 2547) {
            grave.interact("Focus on");
        }
        else if(grave.getSpriteId() == 2520) {

            if(!Tools.isAnimating(5)) {
                Mouse.moved(grave.getClickPoint().getX(), grave.getClickPoint().getY(), Static.getClient().getCanvas(), System.currentTimeMillis());
                Time.sleepTick();
                dest = Static.getClient().getRenderOverview().getMouseLocation();
            }

            if(!Movement.isWalking() && dest != null) {
                Movement.walkTo(dest);
            }
        }

        return -1;
    }
}
