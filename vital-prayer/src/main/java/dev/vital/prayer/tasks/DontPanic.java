package dev.vital.prayer.tasks;

import dev.vital.prayer.VitalPrayerConfig;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.movement.Movement;

public class DontPanic implements ScriptTask
{
	WorldPoint safe_location = new WorldPoint(2948, 3817, 0);

	VitalPrayerConfig config;

	public DontPanic(VitalPrayerConfig config)
	{

		this.config = config;
	}

	@Override
	public boolean validate()
	{

		return !Worlds.inMembersWorld();
	}

	@Override
	public int execute()
	{

		if (Movement.walkTo(safe_location) && LocalPlayer.get().getWorldLocation().equals(safe_location))
		{

			Worlds.hopTo(Worlds.getRandom(x -> x.isMembers() && x.isNormal()), true);
		}

		return 100;
	}
}
