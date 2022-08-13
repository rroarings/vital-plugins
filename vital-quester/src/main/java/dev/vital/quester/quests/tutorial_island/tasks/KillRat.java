package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.widgets.Widgets;

public class KillRat implements ScriptTask
{
    VitalQuesterConfig config;

    public KillRat(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        var widget  = Widgets.get(263, 1);
        if(widget != null) {
            var widget_child = widget. getChild(0);
            if(widget_child != null) {
                return widget_child.getText().contains("It's time to slay some rats!") || widget_child.getText().contains("While you are fighting");
            }
        }
        return false;
    }

    @Override
    public int execute()
    {
        var rat = NPCs.getNearest(x -> (x.getInteracting() == null || x.getInteracting() == LocalPlayer.get()) && x.getName().equals("Giant rat"));
        if(rat != null && !Tools.isAnimating(5)) {
            rat.interact("Attack");
        }

        return -5;
    }
}
