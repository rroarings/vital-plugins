package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.quests.Quests;

public class StartQuest implements ScriptTask
{
    private final WorldPoint pirate_point = new WorldPoint(3054, 3253, 0);

    VitalQuesterConfig config;

    public StartQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return config.currentQuest().equals(QuestList.PIRATES_TREASURE) && Quests.getState(Quest.PIRATES_TREASURE) == QuestState.NOT_STARTED;
    }

    DialogTask talk_to_frank = new DialogTask("Redbeard Frank", pirate_point,
            "I'm in search of treasure.", "Yes.");

    @Override
    public int execute() {

        if(!talk_to_frank.taskCompleted()) {
            return talk_to_frank.execute();
        }

        return -1;
    }
}
