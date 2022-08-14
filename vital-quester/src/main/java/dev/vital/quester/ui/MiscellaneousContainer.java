package dev.vital.quester.ui;

import dev.vital.quester.VitalQuesterConfig;
import net.runelite.client.config.ConfigManager;

public class MiscellaneousContainer extends PanelContainer
{
    public MiscellaneousContainer(VitalQuesterConfig config, ConfigManager configManager)
    {
        super("Miscellaneous", config, configManager);
    }

    @Override
    protected void rebuild()
    {
        removeAll();

        add(createCheckBox("Handle genie", "handleGenie"), "wrap");
        add(createCheckBox("Handle lamps", "handleLamp"), "wrap");

        revalidate();
    }
}
