package dev.vital.aerial;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;

@ConfigGroup("vitalaerialconfig")
public interface VitalAerialConfig extends Config
{
	//@ConfigSection(
	//		keyName = "needleAndThreadConfig",
	//		name = "Needle & Thread Configuration",
	//		description = "",
	//		position = 0
	//)
	//String needleAndThreadConfig = "needleAndThreadConfig";

	/*@ConfigItem(
			keyName = "npcID",
			name = "NPC ID",
			description = "ID of the NPC you want to catch",
			position = 5//,
			//section = "needleAndThreadConfig"
	)
	default int npcID() { return 0; }

	@ConfigItem(
			keyName = "dropIDs",
			name = "ID's to drop",
			description = "",
			position = 10//,
			//section = "needleAndThreadConfig"
	)
	default String dropIDs() { return ""; }


	//delay configs

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
	default int tickMaxDelay() { return 55; }*/

	//@ConfigItem(
	//		keyName = "potions",
	//		name = "Potions",
	//		description = "Crafting potions",
	//		position = 2
	//	)
	//default boolean potions() { return false; }
	/*@ConfigItem(
			keyName = "firstItemID",
			name = "First ID",
			description = "The ID of the item you want to use.",
			position = 1
	)
	default int firstItemID() { return 0; }

	@ConfigItem(
			keyName = "firstItemIDAmount",
			name = "First ID quantity",
			description = "The amount you want to withdraw.",
			position = 2
	)
	default int firstItemIDAmount() { return 0; }

	@ConfigItem(
			keyName = "dontBankFirstItem",
			name = "Dont bank 1st item",
			description = "Doesnt bank the first item above.",
			position = 3
	)
	default boolean dontBankFirstItem() { return false; }

	@ConfigItem(
			keyName = "secondItemID",
			name = "Second ID",
			description = "The ID of the item you want to use.",
			position = 4
	)
	default int secondItemID() { return 0; }

	@ConfigItem(
			keyName = "secondItemIDAmount",
			name = "Second ID quantity",
			description = "The amount you want to withdraw.",
			position = 5
	)
	default int secondItemIDAmount() { return 0; }

	@ConfigItem(
			keyName = "items",
			name = "Item IDs to not bank",
			description = "Separate with comma, enable below option to not drop/bank these IDs.",
			position = 6,
			hide = "dropInventory",
			section = "dropConfig"
	)
	default String items() {
		return "";
	}

	@ConfigItem(
			keyName = "minimumDelay",
			name = "Minimum delay",
			description = "Minimum amount of milliseconds to wait after casting.",
			position = 7
	)
	default int minimumDelay() { return 0; }

	@ConfigItem(
			keyName = "maximumDelay",
			name = "Maximum delay",
			description = "Maximum amount of milliseconds to wait after casting.",
			position = 8
	)
	default int maximumDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMinDelay",
			name = "Minimum tick delay",
			description = "Minimum amount of ticks to wait after casting.",
			position = 9
	)
	default int tickMinDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMaxDelay",
			name = "Maximum tick delay",
			description = "Maximum amount of ticks to wait after casting.",
			position = 10
	)
	default int tickMaxDelay() { return 0; }*/
}
