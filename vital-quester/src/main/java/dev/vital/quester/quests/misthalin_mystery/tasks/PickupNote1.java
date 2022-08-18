package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;

public class PickupNote1 implements ScriptTask
{
    WorldPoint note_point = new WorldPoint(1635, 4838, 0);
    VitalQuesterConfig config;

    public PickupNote1(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 35;
    }

    BasicTask pickup_note = new BasicTask(() -> {
        if(Inventory.contains(ItemID.NOTES_21056)) {
            Inventory.getFirst(ItemID.NOTES_21056).interact("Read");
        }
        else{
            Tools.interactWith(2266, "Take", note_point, Tools.EntityType.TILE_OBJECT);
        }
        return 0;
    });

    @Override
    public int execute() {

        if (!pickup_note.taskCompleted()) {
            return pickup_note.execute();
        }

        return -1;
    }
}
