package dev.vital.birdhouse;

import net.runelite.client.config.Button;
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
			keyName = "autoMats",
			name = "Automatically Get Materials",
			description = "Automatial get materials from the bank.",
			position = 5//,
			//section = "needleAndThreadConfig"
	)
	default boolean autoMats() { return true; }

	@ConfigItem(
			keyName = "returnToGE",
			name = "Return To GE",
			description = "Returns to the GE and banks all items after a run.",
			position = 10//,
			//section = "needleAndThreadConfig"
	)
	default boolean returnToGE() { return true; }

	@ConfigItem(
			keyName = "autoLogOut",
			name = "Auto Log Out",
			description = "Use login on disconnect from vital-tools with a delay to achieve a full birdhouse run bot.",
			position = 10//,
			//section = "needleAndThreadConfig"
	)
	default boolean autoLogOut() { return false; }

	@ConfigItem(
			keyName = "seedID",
			name = "Seed ID",
			description = "The ID of the seed you want to use.",
			position = 15//,
			//section = "needleAndThreadConfig"
	)
	default int seedID() { return 5311; }

	@ConfigItem(
			keyName = "logID",
			name = "Log ID",
			description = "The ID of the log you want to use.",
			position = 20//,
			//section = "needleAndThreadConfig"
	)
	default int logID() { return 6332; }

	@ConfigItem(
			keyName = "returnTeleport",
			name = "Return Teleport",
			description = "The ID of the teleport to return from Fossil Island.",
			position = 25
			//section = "needleAndThreadConfig"
	)
	default int returnTeleport() { return 8013; }

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			position = 30
	)
	default Button startStopPlugin() { return new Button(); }
}
