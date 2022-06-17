package dev.vital.garden;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalgardenconfig")
public interface VitalGardenConfig extends Config
{
	@ConfigItem(
			keyName = "autumnGarden",
			name = "Autumn Garden",
			description = "",
			position = 0
	)
	default boolean autumnGarden() { return false; }
}
