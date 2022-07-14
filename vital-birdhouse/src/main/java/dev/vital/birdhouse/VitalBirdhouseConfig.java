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

	@ConfigItem(
			keyName = "returnTeleport",
			name = "Return Teleport",
			description = "The ID of the teleport to return from Fossil Island.",
			position = 15//,
			//section = "needleAndThreadConfig"
	)
	default int returnTeleport() { return 8013; }

	@ConfigItem(
			keyName = "autoMats",
			name = "Automatically Get Materials",
			description = "",
			position = 15//,
			//section = "needleAndThreadConfig"
	)
	default boolean autoMats() { return true; }
}
