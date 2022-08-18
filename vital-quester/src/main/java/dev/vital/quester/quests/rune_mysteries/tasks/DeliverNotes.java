package dev.vital.quester.quests.rune_mysteries.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;

public class DeliverNotes implements ScriptTask
{
    private final WorldPoint sedridor_point = new WorldPoint(3104, 9571, 0);

    VitalQuesterConfig config;

    public DeliverNotes(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(13726) == 0;
    }

    DialogTask deliver_notes = new DialogTask("Archmage Sedridor", sedridor_point, (String) null);

    @Override
    public int execute()
    {
        if(!deliver_notes.taskCompleted()) {
            return deliver_notes.execute();
        }

        return -1;
    }
}
