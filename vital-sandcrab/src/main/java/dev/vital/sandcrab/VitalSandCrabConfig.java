package dev.vital.sandcrab;

import net.runelite.client.config.Button;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalsandcrabconfig")
public interface VitalSandCrabConfig extends Config
{
	//@ConfigSection(
	//		keyName = "needleAndThreadConfig",
	//		name = "Needle & Thread Configuration",
	//		description = "",
	//		position = 0
	//)
	//String needleAndThreadConfig = "needleAndThreadConfig";

	//@ConfigItem(
	//		keyName = "autoMats",
	//		name = "Automatically Get Materials",
	//		description = "Automatial get materials from the bank.",
	//		position = 5//,
	//		//section = "needleAndThreadConfig"
	//)
	//default boolean autoMats() { return true; }

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			position = 50
	)
	default Button startStopPlugin() { return new Button(); }
}
