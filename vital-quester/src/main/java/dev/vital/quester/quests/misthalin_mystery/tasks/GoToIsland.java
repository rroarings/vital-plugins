package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.quests.QuestVarbits;

public class GoToIsland implements ScriptTask
{
    private final WorldPoint boat_point = new WorldPoint(3237, 3150, 0);
    private final WorldPoint bucket_point = new WorldPoint(1619, 4816, 0);

    VitalQuesterConfig config;

    public GoToIsland(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 10;
    }

    BasicTask talk_to_abigale = new BasicTask(() -> {
        if(LocalPlayer.get().getWorldLocation().getRegionID() != 6475) {

            Tools.interactWith(30108,"Board", boat_point, Tools.EntityType.TILE_OBJECT);
        }
        else {
            Movement.walkTo(bucket_point);
        }

        return -5;
    });

    @Override
    public int execute() {

        if (!talk_to_abigale.taskCompleted()) {
            return talk_to_abigale.execute();
        }

        return -1;
    }
}
