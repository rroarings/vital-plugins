package dev.unethicalite.scripts.cooking;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.unethicalite.api.events.LoginStateChanged;
import dev.unethicalite.api.game.Game;
import dev.unethicalite.api.input.Keyboard;
import dev.unethicalite.api.plugins.Script;
import dev.unethicalite.scripts.cooking.tasks.CookingGuild;
import dev.unethicalite.scripts.cooking.tasks.FillJugs;
import dev.unethicalite.scripts.cooking.tasks.Gather;
import dev.unethicalite.scripts.cooking.tasks.GoBank;
import dev.unethicalite.scripts.cooking.tasks.GoDownstairs;
import dev.unethicalite.scripts.cooking.tasks.GoUpstairs;
import dev.unethicalite.scripts.cooking.tasks.MakeWine;
import dev.unethicalite.scripts.cooking.tasks.ScriptTask;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// This annotation is required in order for the client to detect it as a plugin/script.
@PluginDescriptor(name = "vital-wine", enabledByDefault = false)
@Extension
public class VitalWine extends Script
{
	private static final ScriptTask[] TASKS = new ScriptTask[]{
			new CookingGuild(),
			new GoUpstairs(),
			new Gather(),
			new GoDownstairs(),
			new FillJugs(),
			new MakeWine(),
			new GoBank()
	};

	/**
	 * Gets executed whenever a script starts.
	 * Can be used to for example initialize script settings, or perform tasks before starting the loop logic.
	 *
	 * @param args any script arguments passed to the script, separated by spaces.
	 */
	@Override
	public void onStart(String... args)
	{
	}

	/**
	 * Any logic passed inside this method will be repeatedly executed by an internal loop that calls this method.
	 *
	 * @return the amount of milliseconds to sleep after each loop iteration.
	 */
	@Override
	protected int loop()
	{
		// Here I use task-based logic. You can also just write the entire script logic
		for (ScriptTask task : TASKS)
		{
			if (task.validate())
			{
				// Perform the task and store the sleep value
				int sleep = task.execute();
				// If this task blocks the next task, return the sleep value and the internal loop will sleep for this amount of time
				if (task.blocking())
				{
					return sleep;
				}
			}
		}

		return 1000;
	}

	@Inject
	private VitalWineConfig config;

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
		if (e.getGameState() == GameState.LOGIN_SCREEN && Game.getClient().getLoginIndex() == 0)
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
