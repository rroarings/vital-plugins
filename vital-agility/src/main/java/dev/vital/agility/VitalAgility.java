package dev.vital.agility;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.LoopedPlugin;
import org.pf4j.Extension;

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

	@Override
	protected int loop()
	{
		if(!Game.isLoggedIn() || LocalPlayer.get().isMoving())
		{
			return Rand.nextInt(1000, 1200);
		}

		var local = LocalPlayer.get();
		if(local.isAnimating())
		{
			return Rand.nextInt(1000, 1200);
		}

		var location = local.getWorldLocation();
		var ladder = TileObjects.getNearest(17385);
		if(ladder != null)
		{
			ladder.interact("Climb-up");
			event = Events.STONE;
			return Rand.nextInt(2200, 3000);
		}
		else if(location.equals(pipe1) || location.equals(pipe2) || location.equals(pipe3))
		{
			event = Events.PIPE;
		}
		else if(location.equals(new WorldPoint(3004,3950,0)))
		{
			event = Events.ROPE;
		}
		else if(location.equals(new WorldPoint(3005,3958,0)))
		{
			event = Events.STONE;
		}
		else if(location.equals(new WorldPoint(2996,3960,0)))
		{
			event = Events.LOG;
		}
		else if(location.equals(new WorldPoint(2994,3945,0)))
		{
			event = Events.ROCK;
		}

		switch(event)
		{
			case PIPE: TileObjects.getNearest("Obstacle pipe").interact("Squeeze-through"); break;
			case ROPE: TileObjects.getFirstAt(3005, 3952, 0, 23132).interact("Swing-on"); break;
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
