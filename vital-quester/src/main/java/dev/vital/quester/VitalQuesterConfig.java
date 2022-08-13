package dev.vital.quester;

import net.runelite.client.config.Button;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalquesterconfig")
public interface VitalQuesterConfig extends Config
{
	@ConfigItem(
			keyName = "currentQuest",
			name = "Quest",
			description = "Set a quest to complete.",
			position = 10
	)
	default QuestList currentQuest() { return QuestList.COOKS_ASSISTANT; }

	@ConfigItem(
			keyName = "automaticOptimal",
			name = "Automatic Optimal Questing",
			description = "Completes quest in the order they appear in the quest helper when set to optimal.",
			position = 10
	)
	default boolean automaticOptimal() { return false; }

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			position = 30
	)
	default Button startStopPlugin() { return new Button(); }
}
