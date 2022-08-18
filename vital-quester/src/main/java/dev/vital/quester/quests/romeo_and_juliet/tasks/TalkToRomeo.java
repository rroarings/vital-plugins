package dev.vital.quester.quests.romeo_and_juliet.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarPlayer;

public class TalkToRomeo implements ScriptTask
{
    private final WorldPoint romeo_point = new WorldPoint(3215, 3418, 0);

    VitalQuesterConfig config;

    public TalkToRomeo(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getVarp(QuestVarPlayer.QUEST_ROMEO_AND_JULIET.getId()) == 20;
    }

    DialogTask talk_to_romeo = new DialogTask("Romeo",  romeo_point,
            "Ok, thanks.");

    @Override
    public int execute() {

        if (!talk_to_romeo.taskCompleted()) {
            return talk_to_romeo.execute();
        }

        return -1;
    }
}
