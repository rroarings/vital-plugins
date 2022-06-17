package dev.vital.quester;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalquesterconfig")
public interface VitalQuesterConfig extends Config
{
	@ConfigItem(
			keyName = "automatic",
			name = "Automatic",
			description = "Automatically complete quests in optimal order.",
			position = 5
	)
	default boolean automatic() { return true; }

	@ConfigItem(
			keyName = "questName",
			name = "Quest name",
			description = "Manualy set a quest to complete.",
			position = 10
	)
	default String questName() { return "Sheep Shearer"; }

	@ConfigItem(
			keyName = "members",
			name = "Members",
			description = "Weather or not you are a member.",
			position = 15
	)
	default boolean members() { return true; }
}
