package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.WalkTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;
import net.unethicalite.api.widgets.Widgets;

public class GetSapphireKey implements ScriptTask
{
    VitalQuesterConfig config;

    public GetSapphireKey(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 105;
    }
    BasicTask cut_fireplace = new BasicTask(() -> {

        if(Inventory.contains(ItemID.SAPPHIRE_KEY_21055)) {
            return 0;
        }

        var fireplace = TileObjects.getNearest("Fireplace");
        if (fireplace.hasAction("Search")) {
            fireplace.interact("Search");
        }

        return -5;
    });
    WalkTask walk1 = new WalkTask(new WorldPoint(1627, 4829, 0));

    @Override
    public int execute() {

        if (!cut_fireplace.taskCompleted()) {
            return cut_fireplace.execute();
        }
        else if (!walk1.taskCompleted()) {
            return walk1.execute();
        }

        return -1;
    }
}
