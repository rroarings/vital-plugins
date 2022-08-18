package dev.vital.scripts.cooking;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.scripts.cooking.tasks.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.events.LoginStateChanged;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.plugins.Script;
import net.unethicalite.client.Static;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@PluginDescriptor(name = "vital-wine", enabledByDefault = false)
@Extension
@Slf4j
public class VitalWine extends Script
{
	@Inject
	private VitalWineConfig config;
	List<ScriptTask> tasks = new ArrayList<>();

	@Override
	public void onStart(String... args)
	{
		tasks.add(new CookingGuild(config));
		tasks.add(new GoUpstairs(config));
		tasks.add(new Gather(config));
		tasks.add(new GoDownstairs(config));
		tasks.add(new FillJugs(config));
		tasks.add(new MakeWine(config));
		tasks.add(new GoBank(config));
	}

	@Override
	public void onStop()
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

		return -1;
	}

	@Inject
	private ScheduledExecutorService executor;

	@Inject
	private Client client;

	@Provides
	public VitalWineConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalWineConfig.class);
	}

	@Subscribe
	private void onGameStateChanged(GameStateChanged e)
	{
		if (e.getGameState() == GameState.LOGIN_SCREEN && Static.getClient().getLoginIndex() == 0)
		{
			executor.schedule(() -> client.setLoginIndex(2), 2000, TimeUnit.MILLISECONDS);
			executor.schedule(this::login, 2000, TimeUnit.MILLISECONDS);
		}
	}

	@Subscribe
	private void onLoginStateChanged(LoginStateChanged e)
	{
		switch (e.getIndex())
		{
			case 2:
				login();
				break;
		}
	}

	private void login()
	{
		client.setUsername(config.username());
		client.setPassword(config.password());
		Keyboard.sendEnter();
		Keyboard.sendEnter();
	}
}
