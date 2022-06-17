package dev.vital.birdhouse;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("vitalbirdhouse")
public interface VitalBirdhouseConfig extends Config
{
	//@ConfigSection(
	//		keyName = "needleAndThreadConfig",
	//		name = "Needle & Thread Configuration",
	//		description = "",
	//		position = 0
	//)
	//String needleAndThreadConfig = "needleAndThreadConfig";

	@ConfigItem(
			keyName = "seedID",
			name = "Seed ID",
			description = "The ID of the seed you want to use.",
			position = 5//,
			//section = "needleAndThreadConfig"
	)
	default int seedID() { return 5311; }

	@ConfigItem(
			keyName = "logID",
			name = "Log ID",
			description = "The ID of the log you want to use.",
			position = 10//,
			//section = "needleAndThreadConfig"
	)
	default int logID() { return 6332; }

	@ConfigSection(
			keyName = "delayConfig",
			name = "Delay Configuration",
			description = "",
			position = 60
	)
	String delayConfig = "delayConfig";

	@ConfigItem(
			keyName = "minimumDelay",
			name = "Minimum delay",
			description = "Minimum amount of milliseconds to wait after casting.",
			position = 61,
			section = "needleAndThreadConfig"
	)
	default int minimumDelay() { return 120; }

	@ConfigItem(
			keyName = "maximumDelay",
			name = "Maximum delay",
			description = "Maximum amount of milliseconds to wait after casting.",
			position = 62
	)
	default int maximumDelay() { return 300; }

	@ConfigItem(
			keyName = "tickMinDelay",
			name = "Minimum tick delay",
			description = "Minimum amount of ticks to wait after casting.",
			position = 63
	)
	default int tickMinDelay() { return 45; }

	@ConfigItem(
			keyName = "tickMaxDelay",
			name = "Maximum tick delay",
			description = "Maximum amount of ticks to wait after casting.",
			position = 64
	)
	default int tickMaxDelay() { return 55; }
}
