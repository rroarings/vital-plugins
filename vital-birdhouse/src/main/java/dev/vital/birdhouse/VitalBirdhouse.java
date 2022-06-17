package dev.vital.birdhouse;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.Tile;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.cannon.CannonPlugin;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

@PluginDescriptor(name = "vital-birdhouse", enabledByDefault = false)
@Extension
@Slf4j
public class VitalBirdhouse extends LoopedPlugin
{
	@Inject
	private VitalBirdhouseConfig config;
	private static final WorldArea FOSSIL_ISLAND_TELE_ROOM = new WorldArea(3760, 3867, 9, 25, 1);
	private static final WorldArea VALLEY_AREA = new WorldArea(3757, 3754, 13, 9, 0);
	private static final WorldPoint VALLEY_TILE_1 = new WorldPoint(3763, 3755, 0);
	private static final WorldPoint VALLEY_TILE_2 = new WorldPoint(3768, 3761, 0);

	private static final WorldArea MEADOW_AREA = new WorldArea(3676, 3871, 4, 14, 0);
	private static final WorldPoint MEADOW_TILE_1 = new WorldPoint(3677, 3882, 0);

	private static final WorldArea MEADOW_AREA2 = new WorldArea(3678, 3812, 5, 5, 0);
	private static final WorldPoint MEADOW_TILE_2 = new WorldPoint(3679, 3815, 0);
	//private static final WorldPoint VALLEY_TILE_2 = new WorldPoint(3768, 3761, 0);

	boolean didTeleport() {

		return FOSSIL_ISLAND_TELE_ROOM.contains(LocalPlayer.get());
	}

	@Override
	protected int loop()
	{
		if (!Game.isLoggedIn() || Movement.isWalking() || LocalPlayer.get().isAnimating())
		{

			return Rand.nextInt(1000, 2000);
		}

		Inventory.getFirst(ItemID.DIGSITE_PENDANT_1, ItemID.DIGSITE_PENDANT_2, ItemID.DIGSITE_PENDANT_3,
				ItemID.DIGSITE_PENDANT_4, ItemID.DIGSITE_PENDANT_5).interact("Rub");

		Time.sleep(Rand.nextInt(2000, 3000));

		Keyboard.type(2);

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getNearest("Magic Mushtree").interact("Use");

		Time.sleep(Rand.nextInt(2000, 3000));

		Keyboard.type(2);

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(VALLEY_TILE_1, x -> x.hasAction("Empty")).interact("Empty");

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(VALLEY_TILE_1, x -> x.hasAction("Build")).interact("Build");

		Time.sleep(Rand.nextInt(2000, 3000));

		Inventory.getFirst(config.seedID()).useOn(TileObjects.getFirstAt(VALLEY_TILE_1, x -> x.hasAction("Seeds")));

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(VALLEY_TILE_2, x -> x.hasAction("Empty")).interact("Empty");

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(VALLEY_TILE_2, x -> x.hasAction("Build")).interact("Build");

		Time.sleep(Rand.nextInt(2000, 3000));

		Inventory.getFirst(config.seedID()).useOn(TileObjects.getFirstAt(VALLEY_TILE_2, x -> x.hasAction("Seeds")));

		TileObjects.getNearest("Magic Mushtree").interact("Use");

		Time.sleep(Rand.nextInt(2000, 3000));

		Keyboard.type(4);

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(MEADOW_TILE_1, x -> x.hasAction("Empty")).interact("Empty");

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(MEADOW_TILE_1, x -> x.hasAction("Build")).interact("Build");

		Time.sleep(Rand.nextInt(2000, 3000));

		Inventory.getFirst(config.seedID()).useOn(TileObjects.getFirstAt(MEADOW_TILE_1, x -> x.hasAction("Seeds")));

		Time.sleep(Rand.nextInt(2000, 3000));

		Movement.walkTo(MEADOW_AREA2);

		Time.sleep(Rand.nextInt(20000, 21000));

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(MEADOW_TILE_2, x -> x.hasAction("Empty")).interact("Empty");

		Time.sleep(Rand.nextInt(2000, 3000));

		TileObjects.getFirstAt(MEADOW_TILE_2, x -> x.hasAction("Build")).interact("Build");

		Time.sleep(Rand.nextInt(2000, 3000));

		return 1000;
	}

	@Provides
	VitalBirdhouseConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalBirdhouseConfig.class);
	}
}
