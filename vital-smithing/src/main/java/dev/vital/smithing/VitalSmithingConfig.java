package dev.vital.smithing;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalsmithingconfig")
public interface VitalSmithingConfig extends Config
{
	@ConfigItem(
			keyName = "barID",
			name = "Bar ID",
			description = "The ID of the bars you want to smith.",
			position = 0
	)
	default int barID()
	{
		return 2353;
	}


}
