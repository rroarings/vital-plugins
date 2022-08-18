package dev.vital.sandcrab;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.sandcrab.tasks.CrabClawIsle;
import dev.vital.sandcrab.tasks.KillCrabs;
import dev.vital.sandcrab.tasks.ScriptTask;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.LoopedPlugin;
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
	public static boolean should_find_new_spot = true;
	public static boolean plugin_enabled = false;
	public static int no_target_ticks = 0;
	public static int stack_ticks = 0;
	public static List<Spot> spots = Arrays.asList(
			new Spot(new WorldPoint(1764,3445,0), new WorldPoint(1795,3408,0)),
			new Spot(new WorldPoint(1758,3439,0), new WorldPoint(1796,3411,0)),
			new Spot(new WorldPoint(1786,3404,0), new WorldPoint(1767,3448,0))
	);

	@Override
	public void shutDown() {

		plugin_enabled = false;
	}

	@Override
	public void startUp() {

		tasks.clear();

		tasks.add(new CrabClawIsle(config));
		tasks.add(new KillCrabs(config));
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

		var local_player = LocalPlayer.get();
		if(Players.getAll(x -> !x.equals(local_player)).stream().anyMatch(x -> x.getWorldLocation().equals(local_player.getWorldLocation()))) {
			stack_ticks++;
		}
		else {

			stack_ticks = 0;
		}
		if(stack_ticks >= 10) {
			should_find_new_spot = true;
		}
		if(local_player.getInteracting() == null && spots.stream().anyMatch(x -> x.spot.equals(local_player.getWorldLocation()))) {

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
