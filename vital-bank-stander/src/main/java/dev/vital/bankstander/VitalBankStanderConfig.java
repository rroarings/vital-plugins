package dev.vital.bankstander;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("vitalbankstanderconfig")
public interface VitalBankStanderConfig extends Config
{
	@ConfigSection(
			keyName = "glassBlow",
			name = "Glass Configuration",
			description = "",
			position = 0
	)
	String glassBlowConfig = "glassBlowConfig";
	@ConfigSection(
			keyName = "needleAndThreadConfig",
			name = "Needle & Thread Configuration",
			description = "",
			position = 10
	)
	String needleAndThreadConfig = "needleAndThreadConfig";
	// fletching
	@ConfigSection(
			keyName = "bowAndStringConfig",
			name = "Bow & String Configuration",
			description = "",
			position = 50
	)
	String bowAndStringConfig = "bowAndStringConfig";
	// jugs
	@ConfigSection(
			keyName = "jugsConfig",
			name = "Jugs",
			description = "",
			position = 55
	)
	String jugsConfig = "jugsConfig";
	@ConfigSection(
			keyName = "delayConfig",
			name = "Delay Configuration",
			description = "",
			position = 60
	)
	String delayConfig = "delayConfig";

	@ConfigItem(
			keyName = "glass",
			name = "Glass",
			description = "Blow glass",
			position = 5,
			section = "glassBlowConfig"
	)
	default boolean glass()
	{
		return false;
	}

	@ConfigItem(
			keyName = "needleAndThread",
			name = "Needle & Thread",
			description = "Crafting involving a needle and thread",
			position = 15,
			section = "needleAndThreadConfig"
	)
	default boolean needleAndThread()
	{
		return false;
	}

	@ConfigItem(
			keyName = "firstMaterial",
			name = "First Material",
			description = "The first material you want to use.",
			position = 20,
			section = "needleAndThreadConfig"
	)
	default int firstMaterial()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "firstMaterialAmount",
			name = "First Material Amount",
			description = "The first material amount you want to use.",
			position = 25,
			section = "needleAndThreadConfig"
	)
	default int firstMaterialAmount()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "secondMaterial",
			name = "Second Material",
			description = "The second material you want to use.",
			position = 30,
			section = "needleAndThreadConfig"
	)
	default int secondMaterial()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "secondMaterialAmount",
			name = "Second Material Amount",
			description = "The second material amount you want to use.",
			position = 35,
			section = "needleAndThreadConfig"
	)
	default int secondMaterialAmount()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "depositMaterial",
			name = "Final product ID",
			description = "The ID of the item that was made.",
			position = 40,
			section = "needleAndThreadConfig"
	)
	default int depositMaterial()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "bowAndString",
			name = "Bow & String",
			description = "Crafting involving a bow & string",
			position = 51,
			section = "bowAndStringConfig"
	)
	default boolean bowAndString()
	{
		return false;
	}

	@ConfigItem(
			keyName = "unfinishedBowID",
			name = "Unfinished bow ID",
			description = "The type of bow to use.",
			position = 52,
			section = "bowAndStringConfig"
	)
	default int unfinishedBowID()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "jugs",
			name = "Jugs of water and X",
			description = "Crafting involving a jug of water and X",
			position = 56,
			section = "jugsConfig"
	)
	default boolean jugs()
	{
		return false;
	}

	//delay configs

	@ConfigItem(
			keyName = "secondaryMat",
			name = "2nd material ID",
			description = "The id of the item to use on the jug of water.",
			position = 57,
			section = "jugsConfig"
	)
	default int secondaryMat()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "minimumDelay",
			name = "Minimum delay",
			description = "Minimum amount of milliseconds to wait after casting.",
			position = 61,
			section = "needleAndThreadConfig"
	)
	default int minimumDelay()
	{
		return 120;
	}

	@ConfigItem(
			keyName = "maximumDelay",
			name = "Maximum delay",
			description = "Maximum amount of milliseconds to wait after casting.",
			position = 62
	)
	default int maximumDelay()
	{
		return 300;
	}

	@ConfigItem(
			keyName = "tickMinDelay",
			name = "Minimum tick delay",
			description = "Minimum amount of ticks to wait after casting.",
			position = 63
	)
	default int tickMinDelay()
	{
		return 45;
	}

	@ConfigItem(
			keyName = "tickMaxDelay",
			name = "Maximum tick delay",
			description = "Maximum amount of ticks to wait after casting.",
			position = 64
	)
	default int tickMaxDelay()
	{
		return 55;
	}

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
