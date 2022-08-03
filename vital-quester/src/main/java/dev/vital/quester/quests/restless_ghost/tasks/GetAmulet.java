package dev.vital.quester.quests.restless_ghost.tasks;

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

    @Override
    public int execute()
    {
        if(Tools.interactWith("Father Urhney", "Talk-to", father_urhney_point, Tools.EntityType.NPC)) {
            if(!Tools.selectOptions("Father Aereck sent me to talk to you.", "He's got a ghost haunting his graveyard.")) {
                return -1;
            }
        }

        return -1;
    }
}
