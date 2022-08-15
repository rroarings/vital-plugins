package dev.vital.quester.quests.enter_the_abyss.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class TalkToSedridor implements ScriptTask
{
    private final WorldPoint sedridor_spot = new WorldPoint(3105, 9571, 0);

    VitalQuesterConfig config;

    public TalkToSedridor(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(2313) == 0;
    }

    DialogTask talk_to_mage = new DialogTask("Archmage Sedridor",  sedridor_spot, "Can you teleport me to the Rune Essence Mine?");

    @Override
    public int execute() {

        if(!talk_to_mage.taskCompleted()) {
            return talk_to_mage.execute();
        }

        return -1;
    }
}
