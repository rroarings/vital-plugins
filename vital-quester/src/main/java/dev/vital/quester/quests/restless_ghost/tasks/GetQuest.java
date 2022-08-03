package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.VitalTask;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;
import net.unethicalite.api.quests.Quests;
import dev.vital.quester.tools.Tools;

public class GetQuest implements ScriptTask
{
    private final WorldPoint father_point = new WorldPoint(3240, 3206, 0);

    VitalQuesterConfig config;

    public GetQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Quests.getState(Quest.THE_RESTLESS_GHOST) == QuestState.NOT_STARTED;
    }

    @Override
    public int execute() {

        if (!Tools.interactWith("Father Aereck", "Talk-to", father_point, Tools.EntityType.NPC)) {

            return -5;
        }

        Tools.startQuest("The Restless Ghost");
        Tools.selectOptions("I'm looking for a quest!");

        return -1;
    }
}
