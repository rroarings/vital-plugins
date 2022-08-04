package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.quests.Quests;

public class StartQuest implements ScriptTask
{
    private final WorldPoint veos_point = new WorldPoint(3228, 3241, 0);

    VitalQuesterConfig config;

    public StartQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Quests.getState(Quest.X_MARKS_THE_SPOT) == QuestState.NOT_STARTED;
    }

    @Override
    public int execute() {

        if (!Tools.interactWith("Veos", "Talk-to", veos_point, Tools.EntityType.NPC)) {
            return -5;
        }

        Tools.startQuest("X Marks the Spot");
        Tools.selectOptions("I'm looking for a quest.", "Okay, thanks Veos.");

        return -1;
    }
}
