package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.WalkTask;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.Tile;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.TileObjects;
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

    WalkTask walk1 = new WalkTask(new WorldPoint(8022, 5083, 0));

   BasicTask move_shit = new BasicTask(() -> {

      //var wardrobe_south = TileObjects.getFirstAt()
       var wardrobe_south = TileObjects.getFirstAt(new WorldPoint(8024,5081,0), 30141);
       if(wardrobe_south != null)
       {
            use renderGraphicsObjects to get id of animation thing
       }
       return -1;
   });

    @Override
    public int execute() {

        if (!walk1.taskCompleted()) {
            return walk1.execute();
        }
        else if(!move_shit.taskCompleted()) {
            return move_shit.execute();
        }

        return -1;
    }
}
