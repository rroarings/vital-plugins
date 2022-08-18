package dev.vital.cat;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalcatconfig")
public interface VitalCatConfig extends Config
{
	@ConfigItem(
			keyName = "foodID",
			name = "Food ID",
			description = "ID of the food you want to use.",
			position = 5
	)
	default int foodID() { return 321; }
}
