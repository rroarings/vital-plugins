package dev.vital.sandcrab;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.sandcrab.tasks.ScriptTask;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.LoopedPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PluginDescriptor(name = "vital-sandcrab", enabledByDefault = false)
@Extension
@Slf4j
public class VitalSandCrab extends LoopedPlugin {

	@Inject
	private VitalSandCrabConfig config;

	static List<ScriptTask> tasks = new ArrayList<>();

	public static boolean plugin_enabled = false;
	public static int no_target_ticks = 0;
	public static List<WorldPoint> spots = Arrays.asList(
			new WorldPoint(1751, 3425, 0),
			new WorldPoint(1768, 3409, 0),
			new WorldPoint(1749, 3412, 0)
	);

	@Override
	public void shutDown() {

		plugin_enabled = false;
	}

	@Override
	public void startUp() {

	}

	@Override
	protected int loop() {

		if(plugin_enabled && Game.isLoggedIn()) {

			for (ScriptTask task : tasks){

				if (task.validate()) {

					int sleep = task.execute();
					if (task.blocking()) {

						return sleep;
					}
				}
			}
		}

		return -1;
	}

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked e) {

		if (!e.getGroup().equals("vitalsandcrabconfig")) {
			return;
		}

		switch (e.getKey()) {
			case "startStopPlugin":
				plugin_enabled = !plugin_enabled;
				break;
		}
	}

	@Subscribe
	public void onGameTick(GameTick event) {

		if(LocalPlayer.get().getInteracting() == null && spots.stream().anyMatch(x -> LocalPlayer.get().getWorldLocation().equals(x))) {

			no_target_ticks++;
		}
		else {
			no_target_ticks = 0;
		}
	}

	@Provides
	VitalSandCrabConfig getConfig(ConfigManager configManager) {

		return configManager.getConfig(VitalSandCrabConfig.class);
	}
}
