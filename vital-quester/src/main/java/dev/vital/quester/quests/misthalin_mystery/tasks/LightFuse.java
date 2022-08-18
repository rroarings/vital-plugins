package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.ObjectItemTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;

public class LightFuse implements ScriptTask
{
    WorldPoint shelves_point = new WorldPoint(1646, 4827, 0);
    VitalQuesterConfig config;

    public LightFuse(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 55;
    }

    BasicTask light_candles = new BasicTask(() -> {

        var barrel = TileObjects.getNearest("Barrel");
        if(barrel != null) {
            Inventory.getFirst(ItemID.TINDERBOX).useOn(barrel);
            return 0;
        }
        else{
            return -5;
        }
    });

    @Override
    public int execute() {

        if (!light_candles.taskCompleted()) {
            return light_candles.execute();
        }

        return -1;
    }
}
