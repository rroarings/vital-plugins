package dev.vital.magic;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalmagicconfig")
public interface VitalMagicConfig extends Config
{
	@ConfigItem(
			keyName = "alchemyEnabled",
			name = "Alchemy",
			description = "Cast Low Alchemy or High Alchemy.",
			position = 0
	)
	default boolean alchemyEnabled() { return false; }

	@ConfigItem(
			keyName = "alchemyWhileMoving",
			name = "Alchemy while moving",
			description = "Enable to cast alchemy while moving.",
			position = 1
	)
	default boolean alchemyWhileMoving() { return false; }

	@ConfigItem(
			keyName = "alchemyItem",
			name = "Item ID",
			description = "ID of the item to cast on.",
			position = 2
	)
	default int alchemyItem() { return 0; }

	@ConfigItem(
			keyName = "minimumDelay",
			name = "Minimum delay",
			description = "Minimum amount of milliseconds to wait after casting.",
			position = 3
	)
	default int minimumDelay() { return 0; }

	@ConfigItem(
			keyName = "maximumDelay",
			name = "Maximum delay",
			description = "Maximum amount of milliseconds to wait after casting.",
			position = 4
	)
	default int maximumDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMinDelay",
			name = "Minimum tick delay",
			description = "Minimum amount of ticks to wait after casting.",
			position = 5
	)
	default int tickMinDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMaxDelay",
			name = "Maximum tick delay",
			description = "Maximum amount of ticks to wait after casting.",
			position = 6
	)
	default int tickMaxDelay() { return 0; }
}
