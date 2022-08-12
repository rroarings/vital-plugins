package dev.vital.quester.quests.rune_mysteries.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

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
        return Vars.getBit(13720) == 0;
    }

    DialogTask start_quest = new DialogTask("Duke Horacio", duke_point, "Have you any quests for me?");

    @Override
    public int execute() {

        if(!start_quest.taskCompleted()) {
            return start_quest.execute();
        }

        return -1;
    }
}
