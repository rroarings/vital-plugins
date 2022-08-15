package dev.vital.quester.quests.dorics_quest.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Tab;
import net.unethicalite.api.widgets.Tabs;

import java.util.List;

public class LevelMining implements ScriptTask
{
    private final WorldPoint mining_point = new WorldPoint(3231, 3298, 0);

    VitalQuesterConfig config;

    public LevelMining(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !config.useGrandExchange() && Skills.getLevel(Skill.MINING) < 15 && Tools.localHas(x -> x.getName().contains("pickaxe"));
    }
    private static final List<Integer> GAME_OBJECT_OPCODES = List.of(0, 1, 4, 5, 8, 9, 12, 13, 16, 17, 20, 21, 24, 25,
            2, 3, 6, 7, 10, 11, 14, 15, 18, 19, 22, 23, 26, 27);

    @Override
    public int execute()
    {
        if(Inventory.isFull()) {
            if(!Tabs.isOpen(Tab.INVENTORY)) {
                Tabs.open(Tab.INVENTORY);
                return -1;
            }
            for(var slot : GAME_OBJECT_OPCODES) {
                Inventory.getItem(slot).drop();
                Time.sleep(Rand.nextInt(120, 300));
            }

            return -1;
        }

        return -1;
    }
}
