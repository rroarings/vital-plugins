package dev.vital.quester.quests.enter_the_abyss.tasks;

import dev.vital.quester.tasks.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class TalkToAubury implements ScriptTask
{
    private final WorldPoint aubury_point = new WorldPoint(3253, 3401, 0);

    VitalQuesterConfig config;

    public TalkToAubury(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(2313) == 0;
    }

    DialogTask talk_to_aubury = new DialogTask("Aubury", aubury_point, "Can you teleport me to the Rune Essence Mine?");

    @Override
    public int execute() {

       if(!talk_to_aubury.taskCompleted()) {
           return talk_to_aubury.execute();
       }

        return -1;
    }
}
