package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

public class GetAmulet implements ScriptTask
{
    private final WorldPoint father_urhney_point = new WorldPoint(3147, 3175, 0);

    VitalQuesterConfig config;

    public GetAmulet(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !Tools.localHas(ItemID.GHOSTSPEAK_AMULET);
    }

    DialogTask talk_to_urhney = new DialogTask("Father Urhney",  father_urhney_point,
            "Father Aereck sent me to talk to you.", "He's got a ghost haunting his graveyard.");

    @Override
    public int execute()
    {
       if(!talk_to_urhney.taskCompleted()) {
           return talk_to_urhney.execute();
       }

        return -1;
    }
}
