package dev.vital.quester.quests.romeo_and_juliet.tasks;

import dev.vital.quester.DialogTask;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class TalkToApothecary implements ScriptTask
{
    private final WorldPoint apothecary_point = new WorldPoint(3254, 3497, 0);

    VitalQuesterConfig config;

    public TalkToApothecary(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarPlayer.QUEST_ROMEO_AND_JULIET.getId()) == 40;
    }

    DialogTask talk_to_apothecary = new DialogTask("Apothecary", apothecary_point.getWorldLocation(),
            "Talk about something else.", "Talk about Romeo & Juliet.");

    @Override
    public int execute() {

        if (!talk_to_apothecary.taskCompleted()) {
            return talk_to_apothecary.execute();
        }

        return -1;
    }
}
