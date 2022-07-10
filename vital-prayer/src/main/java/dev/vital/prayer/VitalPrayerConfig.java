package dev.vital.prayer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalprayerconfig")
public interface VitalPrayerConfig extends Config
{
	@ConfigItem(
			keyName = "miningAnimation",
			name = "Mining animation ID",
			description = "",
			position = 0
	)
	default int miningAnimation() { return 6758; }

	@ConfigItem(
			keyName = "animationDelta",
			name = "Animation Delta",
			description = "How many ticks to wait before considered not mining.",
			position = 5
	)
	default int animationDelta() { return 6; }
}
