package dev.vital.motherlode;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import org.pf4j.Extension;

import java.util.Comparator;

@PluginDescriptor(name = "vital-motherlode", enabledByDefault = false)
@Extension
@Slf4j
public class VitalMotherlode extends LoopedPlugin
{


	@Inject
	private VitalMotherlodeConfig config;

	private static final WorldArea MINE_ZONE = new WorldArea(3716, 5665, 11, 22, 0);
	//private static final WorldArea OUT_SAFESPOT = new WorldArea(3725, 5683, 5, 5, 0);
	private static final WorldArea IN_SAFESPOT = new WorldArea(3734, 5674, 4, 4, 0);

	private static final WorldPoint FIRST_ROCKFALL = new WorldPoint(3731, 5683, 0);
	private static final WorldPoint SECOND_ROCKFALL = new WorldPoint(3733, 5680, 0);

	private static final WorldPoint BAD_ROCKFALL1 = new WorldPoint(3727, 5683, 0);
	private static final WorldPoint BAD_ROCKFALL2 = new WorldPoint(3728, 5688, 0);

	private boolean mining = false;
	private Events event = Events.MINE;
	private enum Events {
		MINE,
		CLEAR_ROCKS,
		HOPPER,
		BANK,
		LEAVE_CENTER
	}
	@Override
	public void startUp()
	{
		mining= false;
		event = Events.MINE;
	}


	public static int step = 0;
	@Override
	protected int loop()
	{
		if(!Game.isLoggedIn()) {

			return Rand.nextInt(1000, 1200);
		}

		var local_player = LocalPlayer.get();

		if(Movement.isWalking() || local_player.isAnimating() || mining) {
			return Rand.nextInt(300, 600);
		}

		if(WorldLocation.MOTHERLODE_MINE.getWorldArea().contains(local_player)) {


			switch(event) {

				case MINE: {

					if(!Inventory.isFull())
					{
						TileObject ore = TileObjects.getSurrounding(Players.getLocal().getWorldLocation(), 5, "Ore vein").stream().sorted(Comparator.comparingInt(o -> Reachable.getVisitedTiles(o).size())).findFirst().orElse(null);

						if (ore == null) {
							return 600;
						}

						for (var player : Players.getAll())
						{
							if (player.distanceTo(ore.getWorldLocation()) == 0 && player != local_player)
							{
								continue;
							}

							ore.interact("Mine");
							break;
						}

						return Rand.nextInt(3200, 4200);
					}
					else {

						event = Events.CLEAR_ROCKS;
					}
					break;
				}
				case CLEAR_ROCKS: {

					if(IN_SAFESPOT.contains(local_player)){
						event = Events.HOPPER;
					}
					else {

						Movement.walkTo(IN_SAFESPOT.getCenter());
					}
					break;
				}
				case HOPPER: {

					var strut = TileObjects.getNearest("Broken strut");
					if(strut != null) {
						strut.interact("Hammer");
					}
					else if(Inventory.contains(ItemID.PAYDIRT)){

						TileObjects.getNearest("Hopper").interact("Deposit");
						return Rand.nextInt(1200, 1600);
					}
					else {

						TileObjects.getNearest("Empty sack").interact("Search");
						if(Dialog.isOpen())
						{
							Keyboard.sendSpace();
							event = Events.BANK;
						}
						return Rand.nextInt(1200, 1600);
					}

					break;
				}
				case BANK: {

					if (!Bank.isOpen())
					{
						TileObjects.getNearest("Bank chest").interact("Use");
					}
					else {

						if(Inventory.getFreeSlots() == 27) {
							event = Events.LEAVE_CENTER;
						}
						else {
							Bank.depositAllExcept(ItemID.HAMMER);
						}
					}

					break;
				}
				case LEAVE_CENTER: {
					if(MINE_ZONE.contains(local_player)){
						event = Events.MINE;
					}
					else {
						Movement.walkTo(MINE_ZONE.getCenter());
					}
					break;
				}
				default: {
					break;
				}
			}
		}

		return Rand.nextInt(600,800);
	}

	@Provides
	VitalMotherlodeConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalMotherlodeConfig.class);
	}

	public int ticker = 0;
	public int oldticker = 0;
	@Subscribe
	private void onGameTick(GameTick event)
	{
		ticker++;
		var local_player = LocalPlayer.get();
		if(local_player.getAnimation() == config.miningAnimation()) {

			oldticker = ticker;
			mining = true;
		}

		if(mining && local_player.getAnimation() != config.miningAnimation() && ticker - oldticker > config.animationDelta()) {
			mining = false;
		}
	}
}
