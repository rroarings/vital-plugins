package dev.vital.agility;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.commons.Time;
import dev.unethicalite.api.entities.NPCs;
import dev.unethicalite.api.entities.TileObjects;
import dev.unethicalite.api.game.Game;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import dev.unethicalite.api.movement.Reachable;
import dev.unethicalite.api.plugins.LoopedPlugin;
import dev.unethicalite.api.query.entities.TileObjectQuery;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(name = "vital-agility", enabledByDefault = false)
@Extension
@Slf4j
public class VitalAgility extends LoopedPlugin
{
	WorldPoint pipe1 = new WorldPoint(2995,3933,0);
	WorldPoint pipe2 = new WorldPoint(2994,3933,0);
	WorldPoint pipe3 = new WorldPoint(2993,3933,0);
	enum Events {
		PIPE,
		ROPE,
		STONE,
		LOG,
		ROCK
	}
	Events event = Events.PIPE;
	@Inject
	private VitalAgilityConfig config;

	public int tick_delay = 0;
	public int tick_count = 0;
	public boolean tick_delay_begin = false;

	@Override
	protected int loop()
	{
		if(!Game.isLoggedIn() || Movement.isWalking()) {

			return Rand.nextInt(1000, 1200);
		}

		var local = LocalPlayer.getEntity();
		if(local.isAnimating()) {

			return Rand.nextInt(1000, 1200);
		}

		var location = local.getWorldLocation();
		var ladder = TileObjects.getNearest(17385);
		if(ladder != null) {
			ladder.interact("Climb-up");
			return Rand.nextInt(2200, 3000);
		}
		else if(location.equals(pipe1) || location.equals(pipe2) || location.equals(pipe3)){

			event = Events.PIPE;
		}
		else if(location.equals(new WorldPoint(3004,3950,0))){

			Movement.walkTo(new WorldPoint(3005,3950,0));
			event = Events.ROPE;
			return Rand.nextInt(1200, 2200);
		}
		else if(location.equals(new WorldPoint(3005,3958,0))){

			event = Events.STONE;
		}
		else if(location.equals(new WorldPoint(2996,3960,0))){

			event = Events.LOG;
		}
		else if(location.equals(new WorldPoint(2994,3945,0))){

			event = Events.ROCK;
		}

		switch(event) {
			case PIPE: TileObjects.getNearest("Obstacle pipe").interact("Squeeze-through"); break;
			case ROPE: TileObjects.getNearest("Ropeswing").interact("Swing-on"); break;
			case STONE: TileObjects.getNearest("Stepping stone").interact("Cross"); break;
			case LOG: TileObjects.getNearest("Log balance").interact("Walk-across"); break;
			case ROCK: TileObjects.getNearest("Rocks").interact("Climb"); break;
			default: break;
		}

		return Rand.nextInt(config.minimumDelay(), config.maximumDelay());
	}

	@Provides
	VitalAgilityConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalAgilityConfig.class);
	}
}
