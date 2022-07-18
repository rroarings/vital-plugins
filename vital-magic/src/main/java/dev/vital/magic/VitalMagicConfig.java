package dev.vital.magic;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("vitalmagicconfig")
public interface VitalMagicConfig extends Config
{
	@ConfigSection(
			keyName = "HighAlchemy",
			name = "High Alchemy Configuration",
			description = "",
			position = 0
	)
	String needleAndThreadConfig = "needleAndThreadConfig";
	@ConfigItem(
			keyName = "alchemyEnabled",
			name = "Alchemy",
			description = "Cast Low Alchemy or High Alchemy.",
			position = 2,
			section = "highAlchemy"
	)
	default boolean alchemyEnabled() { return false; }

	@ConfigItem(
			keyName = "teleportAlch",
			name = "Teleport while alching",
			description = "Teleport will be casted after casting high alch.",
			position = 3,
			section = "highAlchemy"
	)
	default boolean teleportAlch() { return false; }

	/*@ConfigItem(
			keyName = "enchantBolt",
			name = "Enchant bolt while alching",
			description = "Enchant will be casted before casting high alch.",
			position = 4,
			section = "highAlchemy"
	)
	default boolean enchantBolt() { return false; }*/

	@ConfigItem(
			keyName = "alchemyWhileMoving",
			name = "Alchemy while moving",
			description = "Enable to cast alchemy while moving.",
			position = 5,
			section = "highAlchemy"
	)
	default boolean alchemyWhileMoving() { return false; }

	@ConfigItem(
			keyName = "alchemyItem",
			name = "Item ID",
			description = "ID of the item to cast on.",
			position = 6,
			section = "highAlchemy"
	)
	default int alchemyItem() { return 0; }

	@ConfigItem(
			keyName = "teleDelayMin",
			name = "Teleport delay min",
			description = "The delay after casting alchemy and before casting teleport.",
			position = 7,
			section = "delayConfig"
	)
	default int teleDelayMin() { return 0; }

	@ConfigItem(
			keyName = "teleDelayMax",
			name = "Teleport delay max",
			description = "The delay after casting alchemy and before casting teleport.",
			position = 8,
			section = "delayConfig"
	)
	default int teleDelayMax() { return 0; }

	@ConfigItem(
			keyName = "enchantMin",
			name = "Enchant delay min",
			description = "The delay after casting alchemy and before casting teleport.",
			position = 9,
			section = "delayConfig"
	)
	default int enchantMin() { return 0; }

	@ConfigItem(
			keyName = "enchantMax",
			name = "Enchant delay max",
			description = "The delay after casting alchemy and before casting teleport.",
			position = 10,
			section = "delayConfig"
	)
	default int enchantMax() { return 0; }

	@ConfigSection(
			keyName = "delayConfig",
			name = "Delay Configuration",
			description = "",
			position = 12
	)
	String delayConfig = "delayConfig";

	@ConfigItem(
			keyName = "minimumDelay",
			name = "Minimum delay",
			description = "Minimum amount of milliseconds to wait after casting.",
			position = 14,
			section = "delayConfig"
	)
	default int minimumDelay() { return 0; }

	@ConfigItem(
			keyName = "maximumDelay",
			name = "Maximum delay",
			description = "Maximum amount of milliseconds to wait after casting.",
			position = 16,
			section = "delayConfig"
	)
	default int maximumDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMinDelay",
			name = "Minimum tick delay",
			description = "Minimum amount of ticks to wait after casting.",
			position = 18,
			section = "delayConfig"
	)
	default int tickMinDelay() { return 0; }

	@ConfigItem(
			keyName = "tickMaxDelay",
			name = "Maximum tick delay",
			description = "Maximum amount of ticks to wait after casting.",
			position = 20,
			section = "delayConfig"
	)
	default int tickMaxDelay() { return 0; }


}
