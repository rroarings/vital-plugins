package dev.vital.quester.ui;

import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;

public class MiscellaneousContainer extends PanelContainer
{
    public MiscellaneousContainer(VitalQuesterConfig config, ConfigManager configManager)
    {
        super("Misc", config, configManager);
    }

    @Override
    protected void rebuild()
    {
        removeAll();

        add(createCheckBox("Handle genie", "handleGenie"), "wrap");
        add(createCheckBox("Handle lamps", "handleLamp"), "wrap");
        add(createCheckBox("Handle death", "handleDeath"), "wrap");
        add(createCheckBox("Handle grave", "handleGrave"), "wrap");

        if(config.handleLamp()) {
            add(createComboBoxSection("Lamp skill", "lampSkill", Skill.class), "wrap");
        }

        revalidate();
    }
}
