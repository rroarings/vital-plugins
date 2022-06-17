package dev.vital.guardians;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Button;

@ConfigGroup("vitalguardiansconfig")
public interface VitalGuardiansConfig extends Config
{
	@ConfigItem(
			keyName = "guardianFragments",
			name = "Guardian Fragments",
			description = "Amount of fragments to mine.",
			position = 0
	)
		default int guardianFragments() { return 100; }

	@ConfigItem(
			keyName = "minGuardianFragments",
			name = "Minimum Guardian Fragments",
			description = "When to mine for more fragments",
			position = 1
	)
	default int minGuardianFragments() { return 26; }

	@ConfigItem(
			keyName = "riftManualStart",
			name = "Rift Manual Start",
			description = "Useful for when you enable the plugin while the rift has already started",
			position = 2
	)
	default Button riftManualStart() { return new Button(); }
}
