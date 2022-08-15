package dev.vital.quester.quests.enter_the_abyss.tasks;

import dev.vital.quester.tasks.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class TalkToCromperty implements ScriptTask
{
    private final WorldPoint cromperty_point = new WorldPoint(2681, 3324, 0);

    VitalQuesterConfig config;

    public TalkToCromperty(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(2313) == 5;
    }

    DialogTask talk_to_cromperty = new DialogTask("Wizard Cromperty",  cromperty_point, "Can you teleport me to the Rune Essence Mine?");

    @Override
    public int execute() {

        if(!talk_to_cromperty.taskCompleted()) {
            return talk_to_cromperty.execute();
        }

        return -1;
    }
}
