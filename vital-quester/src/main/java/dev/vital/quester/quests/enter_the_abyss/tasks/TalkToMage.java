package dev.vital.quester.quests.enter_the_abyss.tasks;

import dev.vital.quester.tasks.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class TalkToMage implements ScriptTask
{
    private final WorldPoint zammy_mage_point2 = new WorldPoint(3258, 3386, 0);

    VitalQuesterConfig config;

    public TalkToMage(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(13733) == 0;
    }

    DialogTask talk_to_mage = new DialogTask("Mage of Zamorak",  zammy_mage_point2,
            "Where do you get your runes from?", "Maybe I could make it worth your while?", "Yes, but I can still help you as well.", "Deal");

    @Override
    public int execute() {

        if(!talk_to_mage.taskCompleted()) {
            return talk_to_mage.execute();
        }

        return -1;
    }
}
