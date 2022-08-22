package dev.vital.bankstander;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.bankstander.tasks.JugsOfWine;
import dev.vital.bankstander.tasks.ScriptTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.LoopedPlugin;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(name = "vital-bankstander", enabledByDefault = false)
@Extension
public class VitalBankStander extends LoopedPlugin
{
	static boolean plugin_enabled = false;
	static List<ScriptTask> tasks = new ArrayList<>();

	@Inject
	public VitalBankStanderConfig config;

	public VitalBankStander()
	{
	}

	@Override
	protected void startUp()
	{
		plugin_enabled = false;

		tasks.clear();

		tasks.add(new JugsOfWine(config));
	}

	@Override
	protected int loop()
	{
		var logged_in = Game.isLoggedIn();

		if (!logged_in && plugin_enabled)
		{
			plugin_enabled = false;
		}

		if (logged_in && plugin_enabled)
		{
			for (ScriptTask task : tasks)
			{
				if (task.validate())
				{
					int sleep = task.execute();
					if (task.blocking())
					{
						return sleep;
					}
				}
			}
		}

		return -1;
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		if (Game.isLoggedIn())
		{
			Tools.isAnimating(1);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e)
	{
		//if (!e.getGroup().equals("vitalbankstanderconfig") && !e.getKey().equals("currentQuest"))
		//{
		//	return;
		//}

		//plugin_enabled = false;
	}

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked e)
	{
		if (!e.getGroup().equals("vitalbankstanderconfig"))
		{
			return;
		}

		if ("startStopPlugin".equals(e.getKey()))
		{
			plugin_enabled = !plugin_enabled;
		}
	}

	@Provides
	VitalBankStanderConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalBankStanderConfig.class);
	}
}
