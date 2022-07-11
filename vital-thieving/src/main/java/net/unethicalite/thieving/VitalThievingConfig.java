package net.unethicalite.thieving;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalthievingconfig")
public interface VitalThievingConfig extends Config
{
	@ConfigItem(
			keyName = "thievingType",
			name = "Thieving Type",
			description = "",
			position = 0
	)
	default ThievingType thievingType() { return ThievingType.STALL_TEA; }

	@ConfigItem(
			keyName = "dropItems",
			name = "Drop Stolen Items",
			description = "",
			position = 1
	)
	default boolean dropItems() { return true; }

	@ConfigItem(
			keyName = "foodID",
			name = "Food ID",
			description = "ID of the food you want to eat",
			position = 2
	)
	default int foodID() { return 1993; }

	@ConfigItem(
			keyName = "thievingLevel",
			name = "Thieving Level",
			description = "Thieving level to stop at",
			position = 3
	)
	default int thievingLevel() { return 99; }
}
