package dev.vital.quester.quests.enter_the_abyss.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class StartQuest implements ScriptTask
{
    private final WorldPoint zammy_mage_point = new WorldPoint(3106, 3559, 0);

    VitalQuesterConfig config;

    public StartQuest(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(13731) == 0;
    }

    DialogTask talk_to_mage = new DialogTask("Mage of Zamorak", zammy_mage_point, "Alright, I'll go.");

    @Override
    public int execute() {

        if(!talk_to_mage.taskCompleted()) {
            return talk_to_mage.execute();
        }

        return -1;
    }
}
