package dev.vital.fisher;

import net.runelite.client.config.Button;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("vitalfisherconfig")
public interface VitalFisherConfig extends Config
{
	@ConfigItem(
			keyName = "method",
			name = "Method",
			description = "",
			position = 5
	)
	default InventoryMethod method()
	{
		return InventoryMethod.DROP;
	}

	@ConfigItem(
			keyName = "itemsToDrop",
			name = "Drop / Bank IDs",
			description = "ex: shrimp and anchovies",
			position = 10
	)
	default String itemsToDrop()
	{
		return "317,321";
	}

	@ConfigSection(
			keyName = "delay",
			name = "Delay Configuration",
			description = "",
			position = 15
	)
	String delay = "delay";

	@ConfigItem(
			keyName = "minDelay",
			name = "Minimum delay",
			description = "minimum amount of seconds to wait before interacting with another fish spot",
			position = 20,
			section = "delay"
	)
	default int minDelay()
	{
		return 7;
	}

	@ConfigItem(
			keyName = "maxDelay",
			name = "Maximum delay",
			description = "maximum amount of seconds to wait before interacting with another fish spot",
			position = 25,
			section = "delay"
	)
	default int maxDelay()
	{
		return 21;
	}

	@ConfigItem(
			keyName = "dropDelay",
			name = "Drop delay",
			description = "Amount of milliseconds between dropping items",
			position = 26,
			section = "delay"
	)
	default int dropDelay()
	{
		return 80;
	}

	@ConfigSection(
			keyName = "fishing",
			name = "Fishing Configuration",
			description = "",
			position = 30
	)
	String fishing = "fishing";

	@ConfigItem(
			keyName = "fishingAction",
			name = "Fishing action",
			description = "Action used to interact with your fishing spot",
			position = 35,
			section = "fishing"
	)
	default String fishingAction()
	{
		return "Lure";
	}

	@ConfigItem(
			keyName = "fishingLocation",
			name = "Fishing location",
			description = "A tile within the general location of your fishing spots",
			position = 40,
			section = "fishing"
	)
	default String fishingLocation()
	{
		return "1647,3559,0";
	}

	@ConfigSection(
			keyName = "cooking",
			name = "Cooking Configuration",
			description = "",
			position = 45
	)
	String cooking = "cooking";

	@ConfigItem(
			keyName = "cookFish",
			name = "Cook fish",
			description = "",
			position = 50,
			section = "cooking"
	)
	default boolean cookFish()
	{
		return false;
	}

	@ConfigItem(
			keyName = "cookingObject",
			name = "Cooking object",
			description = "Object used to cook with",
			position = 55,
			section = "cooking"
	)
	default String cookingObject()
	{
		return "Fire";
	}

	@ConfigItem(
			keyName = "cookingLocation",
			name = "Cooking location",
			description = "A tile within the general location of your cooking spot",
			position = 60,
			section = "cooking"
	)
	default String cookingLocation()
	{
		return "1639,3571,0";
	}

	@ConfigSection(
			keyName = "banking",
			name = "Banking Configuration",
			description = "",
			position = 65
	)
	String banking = "banking";

	@ConfigItem(
			keyName = "bankObject",
			name = "Bank object",
			description = "Object used to bank with",
			position = 70,
			section = "banking"
	)
	default String bankObject()
	{
		return "Bank booth";
	}

	@ConfigItem(
			keyName = "bankAction",
			name = "Bank action",
			description = "Action used to interact with your bank object",
			position = 75,
			section = "banking"
	)
	default String bankAction()
	{
		return "Bank";
	}

	@ConfigItem(
			keyName = "bankLocation",
			name = "Bank location",
			description = "A tile within the general location of your banking spot",
			position = 80,
			section = "banking"
	)
	default String bankingLocation()
	{
		return "1,1,1";
	}

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			position = 85
	)
	default Button startStopPlugin()
	{
		return new Button();
	}
}
