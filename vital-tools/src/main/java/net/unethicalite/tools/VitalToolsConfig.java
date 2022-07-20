package net.unethicalite.tools;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vitaltoolsconfig")
public interface VitalToolsConfig extends Config
{
	@ConfigItem(
			keyName = "autoEat",
			name = "Auto Eat",
			description = "",
			position = 0
	)
	default boolean autoEat() { return false; }

	@ConfigItem(
			keyName = "autoEatPerc",
			name = "Auto Eat Percent",
			description = "",
			position = 5
	)
	default int autoEatPerc() { return 50; }

	@ConfigItem(
			keyName = "autoRelog",
			name = "Login on disconnect",
			description = "",
			position = 10
	)
	default boolean autoRelog() { return false; }

	@ConfigItem(
			keyName = "loginDelay",
			name = "Login Delay",
			description = "Seconds",
			position = 11
	)
	default int loginDelay() { return 3000; }

	@ConfigItem(
			keyName = "username",
			name = "Username",
			description = "Username",
			position = 15
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
			position = 20
	)
	default String password()
	{
		return "Password";
	}
	/*@ConfigSection(
			keyName = "breakHandler",
			position = 0,
			name = "Break Handler",
			description = "",
			closedByDefault = true
	)
	String displayedInfo = "Settings";*/

	/*@ConfigItem(
			keyName = "ids",
			name = "IDs",
			description = "Show ids",
			section = displayedInfo,
			position = 1
	)
	default boolean ids()
	{
		return true;
	}*/
}
