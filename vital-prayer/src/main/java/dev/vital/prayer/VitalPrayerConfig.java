package dev.vital.prayer;

import net.runelite.client.config.Button;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalprayerconfig")
public interface VitalPrayerConfig extends Config
{
	@ConfigItem(
			keyName = "description",
			name = "Description",
			description = "",
			position = 0
	)
	default String description() { return "This plugin will unnote and use bones on the altar in the wilderness, if any other player is " +
			"present it will attempt to hop to a f2p world and then run to a safe spot before switching back" +
			" to a members world."; }

	@ConfigItem(
			keyName = "boneID",
			name = "Bone ID",
			description = "ex: 535 = Dragon Bones",
			position = 0
	)
	default int boneID() { return 535; }

	@ConfigItem(
			keyName = "notedBoneID",
			name = "Noted Bone ID",
			description =  "ex: 536 = Noted Dragon Bones",
			position = 5
	)
	default int notedBoneID() { return 536; }

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			position = 30
	)
	default Button startStopPlugin() { return new Button(); }
	/*@ConfigItem(
			keyName = "animationDelta",
			name = "Animation Delta",
			description = "How many ticks to wait before considered not mining.",
			position = 5
	)
	default int animationDelta() { return 6; }*/
}
