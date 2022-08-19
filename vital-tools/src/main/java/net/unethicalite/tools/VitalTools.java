package net.unethicalite.tools;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.events.LoginStateChanged;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.LoopedPlugin;
import org.pf4j.Extension;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@PluginDescriptor(name = "vital-tools", enabledByDefault = false)
@Extension
@Slf4j
public class VitalTools extends LoopedPlugin
{

	@Inject
	ScheduledExecutorService scheduledExecutorService;
	boolean has_logged_in = false;
	@Inject
	private Client client;
	@Inject
	private VitalToolsConfig config;

	@Override
	public void startUp()
	{

	}

	@Override
	public void shutDown()
	{

	}

	@Override
	protected int loop()
	{

		if (Game.isLoggedIn())
		{

			if (config.autoEat() && Combat.getHealthPercent() < config.autoEatPerc())
			{

				var item = Inventory.getFirst(x -> x.hasAction("Eat"));
				if (item != null)
				{

					item.interact("Eat");
				}
			}
		}

		return -1;
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{

		if (!has_logged_in && Game.isLoggedIn())
		{

			has_logged_in = true;
		}
	}

	void go()
	{
		if (!Game.isLoggedIn())
		{
			Keyboard.sendEnter();
			Keyboard.sendEnter();
		}
	}

	@Subscribe
	private void onLoginStateChanged(LoginStateChanged e)
	{

		if (config.autoRelog() && has_logged_in)
		{

			switch (e.getIndex())
			{

				case 0:
				{

					Keyboard.sendEnter();

					break;
				}
				case 24:
				{

					scheduledExecutorService.schedule(() -> Mouse.click(380, 300, true), 2, TimeUnit.SECONDS);

					break;
				}
				case 2:
				{

					client.setUsername(config.username());
					client.setPassword(config.password());
					scheduledExecutorService.schedule(() -> go(), config.loginDelay(), TimeUnit.SECONDS);

					break;
				}
				default:
				{

					break;
				}
			}
		}
	}

	@Provides
	VitalToolsConfig getConfig(ConfigManager configManager)
	{

		return configManager.getConfig(VitalToolsConfig.class);
	}
}
