package net.unethicalite.tools;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("entityinspector")
public interface VitalToolsConfig extends Config
{
	@ConfigItem(
			keyName = "autoRelog",
			name = "Login on disconnect",
			description = "",
			position = 0
	)
	default boolean autoRelog() { return false; }

	@ConfigItem(
			keyName = "username",
			name = "Username",
			description = "Username",
			position = 5
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
			position = 10
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
