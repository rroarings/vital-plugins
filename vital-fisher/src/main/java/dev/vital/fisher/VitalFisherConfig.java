package dev.vital.fisher;

import net.runelite.client.config.Button;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalfisherconfig")
public interface VitalFisherConfig extends Config
{
	@ConfigItem(
			keyName = "dropFish",
			name = "Drop fish",
			description = "",
			hidden = true
	)
	default boolean dropFish()
	{
		return true;
	}

	@ConfigItem(
			keyName = "minDelay",
			name = "Post interaction delay",
			description = "minimum amount of seconds to wait before interacting with another fish spot",
			position = 0
	)
	default int minDelay()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "maxDelay",
			name = "Post interaction delay",
			description = "maximum amount of seconds to wait before interacting with another fish spot",
			position = 0
	)
	default int maxDelay()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "fishLocation",
			name = "General location",
			description = "a tile with the general location of your fishing spots",
			position = 2
	)
	default String fishLocation()
	{
		return "1,1,1";
	}

	@ConfigItem(
			keyName = "bankLocation",
			name = "Bank location",
			description = "",
			position = 5,
			hidden = true
	)
	default String bankLocation()
	{
		return "1,1,1";
	}

	@ConfigItem(
			keyName = "itemsToDrop",
			name = "Items to drop",
			description = "",
			position = 10
	)
	default String itemsToDrop()
	{
		return "Trout,Salmon";
	}

	@ConfigItem(
			keyName = "action",
			name = "Action",
			description = "Action used to fish the desired spot",
			position = 15
	)
	default String action()
	{
		return "Harpoon";
	}

	@ConfigItem(
			keyName = "startStopPlugin",
			name = "Start / Stop",
			description = "",
			position = 20
	)
	default Button startStopPlugin()
	{
		return new Button();
	}
}
