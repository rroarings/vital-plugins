package dev.vital.quester;

import net.runelite.api.Skill;
import net.runelite.client.config.*;

@ConfigGroup("vitalquesterconfig")
public interface VitalQuesterConfig extends Config
{
	String CONFIG_GROUP = "vitalquesterconfig";

	@ConfigItem(
			keyName = "automaticOptimal",
			name = "Automatic Optimal Questing",
			description = "Completes quest in the order they appear in the quest helper when set to optimal.",
			section = "questSection",
			hidden = true
	)
	default boolean automaticOptimal() { return false; }

	@ConfigItem(
			keyName = "currentQuest",
			name = "Quest",
			description = "Set a quest to complete.",
			section = "questSection",
			hidden = true
	)
	default QuestList currentQuest() { return QuestList.COOKS_ASSISTANT; }

	@ConfigItem(
			keyName = "handleGenie",
			name = "Handle Genie",
			description = "Automatically accept lamp from genie and use on a skill.",
			section = "miscellaneous",
			hidden = true
	)
	default boolean handleGenie() { return false; }

	@ConfigItem(
			keyName = "handleLamp",
			name = "Handle Lamp",
			description = "Automatically accept lamp from genie and use on a skill.",
			section = "miscellaneous",
			hidden = true
	)
	default boolean handleLamp() { return false; }

	@ConfigItem(
			keyName = "handleDeath",
			name = "Handle Death",
			description = "",
			section = "miscellaneous",
			hidden = true
	)
	default boolean handleDeath() { return false; }

	@ConfigItem(
			keyName = "handleGrave",
			name = "Handle Grave",
			description = "",
			section = "miscellaneous",
			hidden = true
	)
	default boolean handleGrave() { return false; }


	@ConfigItem(
			keyName = "lampSkill",
			name = "Lamp Skill",
			description = "Automatically accept lamp from genie and use on a skill.",
			section = "miscellaneous",
			hidden = true
	)
	default Skill lampSkill() { return Skill.HITPOINTS; }

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			hidden = true
	)
	default boolean startStopPlugin() { return false; }

	@ConfigItem(
			keyName = "sheepShearerBankInventory",
			name = "",
			description = "",
			hidden = true
	)
	default boolean sheepShearerBankInventory() { return false; }

	@ConfigItem(
			keyName = "sheepShearerExcludedItems",
			name = "",
			description = "",
			hidden = true
	)
	default String sheepShearerExcludedItems() { return "Shears,Spade"; }
}
