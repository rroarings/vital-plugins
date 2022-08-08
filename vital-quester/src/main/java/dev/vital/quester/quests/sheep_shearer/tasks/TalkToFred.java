package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.DialogTask;
import net.runelite.api.ItemID;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.Quests;
import dev.vital.quester.tools.Tools;

public class TalkToFred implements ScriptTask
{
    private final WorldPoint farmer_fred_point = new WorldPoint(3190, 3273, 0);

    VitalQuesterConfig config;

    public TalkToFred(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Inventory.getCount(false, ItemID.BALL_OF_WOOL) >= 20;
    }

    DialogTask talk_to_fred = new DialogTask("Fred the Farmer",  farmer_fred_point,
            "yes.", "I'm looking for a quest.", "Yes, okay. I can do that.");

    @Override
    public int execute() {

        if (!talk_to_fred.taskCompleted()) {

            return talk_to_fred.execute();
        }

        return -1;
    }
}
