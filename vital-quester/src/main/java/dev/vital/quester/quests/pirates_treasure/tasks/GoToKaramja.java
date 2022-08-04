package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.VitalTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;

public class GoToKaramja implements ScriptTask
{
    private final WorldPoint seaman_point = new WorldPoint(3025, 3218, 0);

    VitalQuesterConfig config;

    public GoToKaramja(VitalQuesterConfig config)
    {
        this.config = config;
    }

    VitalTask go_to_karamja = new VitalTask(() ->
    {
        if(!Tools.interactWith("Seaman Lorris", "Pay-fare", seaman_point, Tools.EntityType.NPC)) {

            return false;
        }

        return Tools.selectOptions("Yes please.");
    });

    @Override
    public boolean validate()
    {
        return !go_to_karamja.taskCompleted();
    }

    @Override
    public int execute() {

        if(!go_to_karamja.execute()) {
            return -5;
        }

        return -1;
    }
}