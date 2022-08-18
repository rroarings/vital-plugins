package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.ObjectItemTask;
import dev.vital.quester.tasks.WalkTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;

public class SearchFireplace implements ScriptTask
{
    WorldPoint note_point = new WorldPoint(1635, 4838, 0);
    VitalQuesterConfig config;

    public SearchFireplace(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 105;
    }

    ObjectItemTask get_next = new ObjectItemTask(29659,21055, 1, false, "Search", new WorldPoint(1643, 4833, 0));

    BasicTask pickup_note = new BasicTask(() -> {
        if(Inventory.contains(ItemID.NOTES_21056)) {
            Inventory.getFirst(ItemID.NOTES_21056).interact("Read");
            return 0;
        }
        else{
            Tools.interactWith(2266, "Take", note_point, Tools.EntityType.TILE_OBJECT);
            return -5;
        }
    });
    WalkTask walk = new WalkTask(new WorldPoint(1627, 4829, 0));

    @Override
    public int execute() {

        if (!pickup_note.taskCompleted()) {
            return pickup_note.execute();
        }
        else if (!get_next.taskCompleted()) {
            return get_next.execute();
        }

        return -1;
    }
}
