package dev.vital.fisher;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalfisherconfig")
public interface VitalFisherConfig extends Config
{
    @ConfigItem(
            keyName = "description",
            name = "Description",
            description = "",
            position = 0
    )
    default String description() { return "Currently only angler fish are supported."; }
}
