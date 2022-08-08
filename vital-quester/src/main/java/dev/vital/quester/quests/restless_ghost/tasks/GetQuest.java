package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.DialogTask;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;
import net.unethicalite.api.quests.Quests;
import dev.vital.quester.tools.Tools;

public class GetQuest implements ScriptTask
{
    private final WorldPoint father_aereck_point = new WorldPoint(3240, 3206, 0);

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

    DialogTask talk_to_aereck = new DialogTask("Father Urhney",  father_aereck_point,
            "Father Aereck sent me to talk to you.", "He's got a ghost haunting his graveyard.");

    @Override
    public int execute() {

        if (!talk_to_aereck.taskCompleted()) {
           return talk_to_aereck.execute();
        }

        return -1;
    }
}
