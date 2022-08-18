package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.WalkTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;

public class FuckMirrors implements ScriptTask
{
    VitalQuesterConfig config;

    public FuckMirrors(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 111;
    }

    WalkTask walk1 = new WalkTask(new WorldPoint(8214, 1435, 0));

   BasicTask move_shit = new BasicTask(() -> {

     //  var wardrobe_south = TileObjects.getFirstAt()

       return -1;
   });

    @Override
    public int execute() {

        //var test = TileObjects.getNearest("w").
        if (!walk1.taskCompleted()) {
            return walk1.execute();
        }

        return -1;
    }
}
