package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;

public class GetKey implements ScriptTask
{
    WorldPoint manor_door = new WorldPoint(1636, 4823, 0);
    VitalQuesterConfig config;

    public GetKey(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 25;
    }

    BasicTask work_barrel = new BasicTask(() -> {
       if(Inventory.contains(ItemID.BUCKET_OF_WATER) && !Inventory.contains(ItemID.MANOR_KEY_21052)) {

            TileObjects.getNearest("Barrel").interact("Search");
            return -5;
        }
       else if(Inventory.contains(ItemID.MANOR_KEY_21052)) {
            return 0;
       }

       return -1;
    });

    BasicTask go_in_manor = new BasicTask(() -> {
        if(Inventory.contains(ItemID.MANOR_KEY_21052)) {
            return Tools.interactWith("Large door","Open", manor_door, Tools.EntityType.TILE_OBJECT);
        }

        return -1;
    });

    @Override
    public int execute() {

        if (!work_barrel.taskCompleted()) {
            return work_barrel.execute();
        }
        else if (!go_in_manor.taskCompleted()) {
            return go_in_manor.execute();
        }

        return -1;
    }
}
