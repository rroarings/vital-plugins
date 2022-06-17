package dev.vital.construction;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalconstructionconfig")
public interface VitalConstructionConfig extends Config
{
	@ConfigItem(
			keyName = "mahoganyHomes",
			name = "Combine",
			description = "Interact X with X",
			position = 0
	)
	default boolean mahoganyHomes() { return false; }

	@ConfigItem(
			keyName = "minimumDelay",
			name = "Minimum delay",
			description = "Minimum amount of milliseconds to wait after casting.",
			position = 5
	)
	default int minimumDelay() { return 0; }

	@ConfigItem(
			keyName = "maximumDelay",
			name = "Maximum delay",
			description = "Maximum amount of milliseconds to wait after casting.",
			position = 6
	)
	default int maximumDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMinDelay",
			name = "Minimum tick delay",
			description = "Minimum amount of ticks to wait after casting.",
			position = 7
	)
	default int tickMinDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMaxDelay",
			name = "Maximum tick delay",
			description = "Maximum amount of ticks to wait after casting.",
			position = 8
	)
	default int tickMaxDelay() { return 0; }
}
