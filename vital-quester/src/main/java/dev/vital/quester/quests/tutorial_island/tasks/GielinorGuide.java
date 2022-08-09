package dev.vital.quester.quests.cooks_assistant.tasks;

import dev.vital.quester.*;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.widgets.Widgets;

public class GielinorGuide implements ScriptTask
{
    private final WorldPoint gielinor_guide_point = new WorldPoint(3094, 3107, 0);

    VitalQuesterConfig config;

    public GielinorGuide(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return !talk_to_guide.taskCompleted();
    }

    DialogTask talk_to_guide = new DialogTask("Gielinor Guide", gielinor_guide_point, "I am an experienced player.");

    @Override
    public int execute()
    {
        return talk_to_guide.execute();
    }
}
