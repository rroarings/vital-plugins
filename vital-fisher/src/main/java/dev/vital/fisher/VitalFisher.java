package dev.vital.fisher;

import com.google.inject.Provides;
import dev.vital.fisher.tasks.GoBank;
import dev.vital.fisher.tasks.GoCook;
import dev.vital.fisher.tasks.GoDrop;
import dev.vital.fisher.tasks.GoFish;
import dev.vital.fisher.tasks.ScriptTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.Script;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(name = "vital-fisher", enabledByDefault = false)
@Extension
public class VitalFisher extends Script
{
	@Inject
	VitalFisherConfig config;

	static List<ScriptTask> tasks = new ArrayList<>();

	public static WorldPoint cook_location;
	public static WorldPoint fish_location;
	public static WorldPoint bank_location;
	public static List<Integer> items_to_drop = new ArrayList<>();

	boolean plugin_enabled = false;

	public List<Integer> stringToIntList(String string)
	{
		return (string == null || string.trim().equals("")) ? List.of(0) :
				Arrays.stream(string.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
	}

	void setConfig()
	{
		var fish_location_list = stringToIntList(config.fishingLocation());
		if (fish_location_list.size() == 3)
		{
			fish_location = new WorldPoint(fish_location_list.get(0), fish_location_list.get(1), fish_location_list.get(2));
		}

		var bank_location_list = stringToIntList(config.bankingLocation());
		if (bank_location_list.size() == 3)
		{
			bank_location = new WorldPoint(bank_location_list.get(0), bank_location_list.get(1), bank_location_list.get(2));
		}

		var cook_location_list = stringToIntList(config.cookingLocation());
		if (cook_location_list.size() == 3)
		{
			cook_location = new WorldPoint(cook_location_list.get(0), cook_location_list.get(1), cook_location_list.get(2));
		}

		items_to_drop = stringToIntList(config.itemsToDrop());
	}

	@Override
	public void onStart(String... args)
	{
		plugin_enabled = false;

		tasks.clear();

		tasks.add(new GoCook(config));
		tasks.add(new GoBank(config));
		tasks.add(new GoDrop(config));
		tasks.add(new GoFish(config));

		setConfig();
	}

	@Override
	protected int loop()
	{
		if (Game.isLoggedIn() && plugin_enabled)
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
	public void onConfigButtonClicked(ConfigButtonClicked e)
	{
		if (!e.getGroup().equals("vitalfisherconfig"))
		{
			return;
		}

		if ("startStopPlugin".equals(e.getKey()))
		{
			plugin_enabled = !plugin_enabled;
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e)
	{
		if (!e.getGroup().equals("vitalfisherconfig"))
		{
			return;
		}

		setConfig();
	}

	@Provides
	VitalFisherConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalFisherConfig.class);
	}
}
