package dev.vital.quester.quests.romeo_and_juliet.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.ObjectItemTask;
import dev.vital.quester.tasks.WalkTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarPlayer;

public class GetBerries implements ScriptTask
{
    private final WorldPoint bush_point = new WorldPoint(3277, 3374, 0);

    VitalQuesterConfig config;

    public GetBerries(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getVarp(QuestVarPlayer.QUEST_ROMEO_AND_JULIET.getId()) == 40 && !Inventory.contains(ItemID.CADAVA_BERRIES);
    }

    ObjectItemTask get_berry = new ObjectItemTask(23625, ItemID.CADAVA_BERRIES, 1, false, "Pick-from", bush_point, bush_point);
    WalkTask walk_task1 = new WalkTask(new WorldPoint(3283, 3428, 0));

    @Override
    public int execute() {

        var local_player = LocalPlayer.get();
        if(local_player.getWorldLocation().getX() < 3266 && !walk_task1.taskCompleted()) {
            return walk_task1.execute();
        }
        else if (!get_berry.taskCompleted()) {
            return get_berry.execute();
        }

        return -1;
    }
}
