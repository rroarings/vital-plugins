package dev.vital.prayer;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.prayer.tasks.SacraficeBones;
import dev.vital.prayer.tasks.ScriptTask;
import dev.vital.prayer.tasks.UnnoteBones;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.LoopedPlugin;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(name = "vital-prayer", enabledByDefault = false)
@Extension
@Slf4j
public class VitalPrayer extends LoopedPlugin
{
	List<ScriptTask> tasks = new ArrayList<>();

	@Inject
	private VitalPrayerConfig config;

	@Override
	public void startUp()
	{
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

		return 1000;
	}

	@Provides
	VitalPrayerConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalPrayerConfig.class);
	}
}
