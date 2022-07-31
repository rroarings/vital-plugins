package dev.vital.prayer.tasks;

import com.google.inject.Inject;
import dev.vital.prayer.VitalPrayer;
import dev.vital.prayer.VitalPrayerConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.packets.ItemPackets;
import net.unethicalite.api.packets.ObjectPackets;
import net.unethicalite.api.widgets.Dialog;

import java.util.concurrent.ScheduledExecutorService;

public class SacraficeBones implements ScriptTask
{
	@Inject
	ScheduledExecutorService scheduledExecutorService;

	WorldPoint pray_location = new WorldPoint(2948, 3820, 0);

	VitalPrayerConfig config;

	public SacraficeBones(VitalPrayerConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() { return Worlds.inMembersWorld() && Inventory.contains(config.boneID()); }

	@Override
	public int execute() {

		if(Dialog.canLevelUpContinue()) {

			Dialog.continueSpace();

			return 50;
		}

		var altar = TileObjects.getNearest(411);
		if(altar != null) {

			if(Reachable.isInteractable(altar)) {

				if (VitalPrayer.is_animating > 3) {

					ObjectPackets.useItemOnTileObject(Inventory.getFirst(config.boneID()), altar);
					VitalPrayer.is_animating = 0;
				}
			}
			else
			{
				if (!Movement.isWalking()) {

					Movement.walkTo(pray_location);
				}
			}
		}
		else {

			if (!Movement.isWalking()) {

				Movement.walkTo(pray_location);
			}
		}

		return 10;
	}
}
