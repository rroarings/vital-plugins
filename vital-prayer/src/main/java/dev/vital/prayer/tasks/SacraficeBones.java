package dev.vital.prayer.tasks;

import com.google.inject.Inject;
import dev.vital.prayer.VitalPrayer;
import dev.vital.prayer.VitalPrayerConfig;
import net.runelite.api.ItemID;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Dialog;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SacraficeBones implements ScriptTask
{
	@Inject
	ScheduledExecutorService scheduledExecutorService;

	WorldPoint pray_location = new WorldPoint(2948, 3820, 0);
	WorldPoint safe_location = new WorldPoint(2948, 3817, 0);

	VitalPrayerConfig config;

	public SacraficeBones(VitalPrayerConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() { return Worlds.inMembersWorld() && Inventory.contains(config.boneID()); }

	int last_free = 0;
	@Override
	public int execute()
	{
		if(Dialog.canLevelUpContinue()) {
			Dialog.continueSpace();
			return 50;
		}

		var altar = TileObjects.getNearest(411);
		if(altar != null) {

			if(Reachable.isInteractable(altar)) {

				if (VitalPrayer.is_animating > 3) {

					Inventory.getFirst(config.boneID()).useOn(altar);
					VitalPrayer.is_animating = 0;
				}
			}
			else
			{
				if (!Movement.isWalking())
				{
					Movement.walkTo(pray_location);
				}
			}
		}
		else {

			if (!Movement.isWalking())
			{
				Movement.walkTo(pray_location);
			}
		}

		return 10;
	}
}
