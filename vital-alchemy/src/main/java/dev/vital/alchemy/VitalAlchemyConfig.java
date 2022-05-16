package dev.vital.alchemy;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalalchemyconfig")
public interface VitalAlchemyConfig extends Config
{
	@ConfigItem(
			keyName = "Item",
			name = "Item Name",
			description = "ID of the noted item you want to alch.",
			position = 1
	)
	default String Item()
	{
		return "Item";
	}

	@ConfigItem(
			keyName = "postCastDelayMin",
			name = "Minimum Post Cast Delay",
			description = "Minimum amount of milliseconds to wait after casting. 0 = default",
			position = 2
	)
	default int postCastDelayMin()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "postCastDelayMax",
			name = "Maximum Post Cast Delay",
			description = "Maximum amount of milliseconds to wait after casting. 0 = default",
			position = 3
	)
	default int postCastDelayMax()
	{
		return 0;
	}
}
