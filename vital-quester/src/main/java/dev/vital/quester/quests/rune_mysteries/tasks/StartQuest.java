package dev.vital.quester.quests.rune_mysteries.tasks;

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
    private final WorldPoint duke_point = new WorldPoint(3210, 3221, 1);

    VitalQuesterConfig config;

    public StartQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return config.currentQuest().equals(QuestList.RUNE_MYSTERIES) && Quests.getState(Quest.RUNE_MYSTERIES) == QuestState.NOT_STARTED;
    }

    @Override
    public int execute() {

        if (!Tools.interactWith("Duke Horacio", "Talk-to", duke_point, Tools.EntityType.NPC)) {
            return -5;
        }

        Tools.startQuest(Quest.RUNE_MYSTERIES.getName());
        Tools.selectOptions("Have you any quests for me?");

        return -1;
    }
}
