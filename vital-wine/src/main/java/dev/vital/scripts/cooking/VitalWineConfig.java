package dev.vital.scripts.cooking;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitalwineconfig")
public interface VitalWineConfig extends Config
{

	@ConfigItem(
			keyName = "username",
			name = "Username",
			description = "Username",
			position = 0
	)
	default String username()
	{
		return "Username";
	}

	@ConfigItem(
			keyName = "password",
			name = "Password",
			description = "Password",
			secret = true,
			position = 1
	)
	default String password()
	{
		return "Password";
	}

	@ConfigItem(
			keyName = "auth",
			name = "Authenticator",
			description = "Authenticator",
			secret = true,
			position = 2
	)
	default String auth()
	{
		return "Authenticator";
	}

	@ConfigItem(
			keyName = "bankPin",
			name = "Bank Pin",
			description = "Bank Pin (ex: 1234)",
			position = 5
	)
	default String bankPin() {

		return "1234";
	}
}
