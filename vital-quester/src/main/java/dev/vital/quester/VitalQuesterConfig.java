package dev.vital.quester;

import net.runelite.api.Skill;
import net.runelite.client.config.*;

@ConfigGroup("vitalquesterconfig")
public interface VitalQuesterConfig extends Config
{
	String CONFIG_GROUP = "vitalquesterconfig";
	@ConfigSection(
			keyName = "questSection",
			name = "Quest Configurations",
			description = "",
			position = 0,
			hidden = true
	)
	String questSection = "questSection";

	@ConfigItem(
			keyName = "automaticOptimal",
			name = "Automatic Optimal Questing",
			description = "Completes quest in the order they appear in the quest helper when set to optimal.",
			position = 5,
			section = "questSection",
			hidden = true
	)
	default boolean automaticOptimal() { return false; }

	@ConfigItem(
			keyName = "currentQuest",
			name = "Quest",
			description = "Set a quest to complete.",
			position = 10,
			section = "questSection",
			hidden = true
	)
	default QuestList currentQuest() { return QuestList.COOKS_ASSISTANT; }

	@ConfigSection(
			keyName = "miscellaneous",
			name = "Miscellaneous",
			description = "",
			position = 15,
			hidden = true
	)
	String miscellaneous = "miscellaneous";

	@ConfigItem(
			keyName = "handleGenie",
			name = "Handle Genie",
			description = "Automatically accept lamp from genie and use on a skill.",
			position = 20,
			section = "miscellaneous",
			hidden = true
	)
	default boolean handleGenie() { return false; }

	@ConfigItem(
			keyName = "handleLamp",
			name = "Handle Lamp",
			description = "Automatically accept lamp from genie and use on a skill.",
			position = 25,
			section = "miscellaneous",
			hidden = true
	)
	default boolean handleLamp() { return false; }

	@ConfigItem(
			keyName = "lampSkill",
			name = "Lamp Skill",
			description = "Automatically accept lamp from genie and use on a skill.",
			position = 30,
			section = "miscellaneous",
			hidden = true
	)
	default Skill lampSkill() { return Skill.HITPOINTS; }

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			position = 35,
			hidden = true
	)
	default boolean startStopPlugin() { return false; }
}
