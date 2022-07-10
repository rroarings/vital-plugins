package net.unethicalite.thieving;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalthievingconfig")
public interface VitalThievingConfig extends Config
{
	@ConfigItem(
			keyName = "thievingType",
			name = "Thieving Type",
			description = "",
			position = 0
	)
	default ThievingType thievingType() { return ThievingType.TEA_STALL; }

	@ConfigItem(
			keyName = "foodID",
			name = "Food ID",
			description = "",
			position = 0
	)
	default int foodID() { return 1993; }

	@ConfigItem(
			keyName = "minDelay",
			name = "Minimum Delay(ms)",
			description = ""
	)
	default int minDelay() { return 130; }

	@ConfigItem(
			keyName = "maxDelay",
			name = "Maximum Delay(ms)",
			description = ""
	)
	default int maxDelay() { return 230; }
}
