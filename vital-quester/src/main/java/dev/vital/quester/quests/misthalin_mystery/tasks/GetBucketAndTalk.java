package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.ItemTask;
import dev.vital.quester.tasks.WalkTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;

public class GetBucketAndTalk implements ScriptTask
{
    private final WorldPoint bucket_point = new WorldPoint(1619, 4816, 0);

    VitalQuesterConfig config;

    public GetBucketAndTalk(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 15;
    }

    ItemTask get_bukcet = new ItemTask(ItemID.BUCKET, 1, false, bucket_point);

    WalkTask walk_task = new WalkTask(new WorldPoint(1615, 4828, 0));

    @Override
    public int execute() {

        if (!get_bukcet.taskCompleted()) {
            return get_bukcet.execute();
        }
        else if (!walk_task.taskCompleted()) {
            return walk_task.execute();
        }

        return -1;
    }
}
