package dev.vital.prayer;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.prayer.tasks.DontPanic;
import dev.vital.prayer.tasks.Panic;
import dev.vital.prayer.tasks.SacraficeBones;
import dev.vital.prayer.tasks.ScriptTask;
import dev.vital.prayer.tasks.UnnoteBones;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.LoopedPlugin;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(name = "vital-prayer", enabledByDefault = false)
@Extension
@Slf4j
public class VitalPrayer extends LoopedPlugin
{

	public static int is_animating = 0;
	List<ScriptTask> tasks = new ArrayList<>();
	boolean plugin_enabled = false;
	@Inject
	private VitalPrayerConfig config;

	@Override
	public void startUp()
	{

		plugin_enabled = false;

		tasks.add(new Panic(config));
		tasks.add(new DontPanic(config));
		tasks.add(new UnnoteBones(config));
		tasks.add(new SacraficeBones(config));
	}

	@Override
	public void shutDown()
	{
		tasks.clear();
	}

	@Override
	protected int loop()
	{
		if (plugin_enabled && Game.isLoggedIn())
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

		return 10;
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		if (LocalPlayer.get().getAnimation() == 3705)
		{
			is_animating = 0;
		}
		else
		{
			is_animating++;
		}
	}

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked e)
	{
		if (!e.getGroup().equals("vitalprayerconfig"))
		{
			return;
		}

		if ("startStopPlugin".equals(e.getKey()))
		{
			plugin_enabled = !plugin_enabled;
		}
	}

	@Provides
	VitalPrayerConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalPrayerConfig.class);
	}
}
