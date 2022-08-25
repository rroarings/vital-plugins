package dev.vital.prayer.tasks;

import dev.vital.prayer.VitalPrayer;
import dev.vital.prayer.VitalPrayerConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Dialog;

public class SacraficeBones implements ScriptTask
{
	WorldPoint pray_location = new WorldPoint(2948, 3820, 0);

	VitalPrayerConfig config;

	public SacraficeBones(VitalPrayerConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Worlds.inMembersWorld() && Inventory.contains(config.boneID());
	}

	@Override
	public int execute()
	{
		if (Dialog.canLevelUpContinue())
		{
			Dialog.continueSpace();

			return 100;
		}

		var altar = TileObjects.getNearest(411);
		if (altar != null)
		{
			if (Reachable.isInteractable(altar) && altar.distanceTo(LocalPlayer.get()) < 2)
			{
				if (VitalPrayer.is_animating >= config.animationDelay())
				{
					Inventory.getFirst(config.boneID()).useOn(altar);

					VitalPrayer.is_animating = 0;

					Time.sleepUntil(() -> Players.query().results().stream().anyMatch(x -> x != null && x != LocalPlayer.get()), Rand.nextInt(80, 150));
				}
			}
			else if (!Movement.isWalking())
			{
				Movement.walkTo(pray_location);
			}
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(pray_location);
		}

		return Rand.nextInt(80, 200);
	}
}
