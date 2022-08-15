package dev.vital.quester.handlers;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.VarPlayer;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.widgets.Tab;
import net.unethicalite.api.widgets.Tabs;

public class HandleCombatStyle implements ScriptTask
{
    VitalQuesterConfig config;

    public HandleCombatStyle(VitalQuesterConfig config) {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Vars.getVarp(VarPlayer.ATTACK_STYLE.getId()) != config.preferedStyle().getIndex();
    }

    @Override
    public int execute() {

        if (!Tabs.isOpen(Tab.COMBAT)) {
            Tabs.open(Tab.COMBAT);
        }
        else {
            Combat.setAttackStyle(config.preferedStyle());
        }

        return -5;
    }
}
