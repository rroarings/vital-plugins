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

public class GetTinderBox implements ScriptTask
{
    WorldPoint shelves_point = new WorldPoint(1646, 4827, 0);
    VitalQuesterConfig config;

    public GetTinderBox(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 50;
    }

    ObjectItemTask get_tinderbox = new ObjectItemTask(30146, ItemID.TINDERBOX, 1, false,"Take-tinderbox",shelves_point);

    BasicTask light_candles = new BasicTask(() -> {

        var painting = TileObjects.getNearest("Unlit candle");
        if(painting != null) {
            Inventory.getFirst(ItemID.TINDERBOX).useOn(painting);
            return -5;
        }
        else{
            return 0;
        }
    });

    @Override
    public int execute() {

        if (!get_tinderbox.taskCompleted()) {
            return get_tinderbox.execute();
        }
        else if (!light_candles.taskCompleted()) {
            return light_candles.execute();
        }

        return -1;
    }
}
