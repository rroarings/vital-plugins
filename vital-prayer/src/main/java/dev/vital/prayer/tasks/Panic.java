package dev.vital.prayer.tasks;

import dev.vital.prayer.VitalPrayerConfig;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.game.Worlds;

public class Panic implements ScriptTask
{
	VitalPrayerConfig config;

	public Panic(VitalPrayerConfig config)
	{

		this.config = config;
	}

	@Override
	public boolean validate()
	{

		return !Players.getAll(x -> !x.equals(LocalPlayer.get())).isEmpty();
	}

	@Override
	public int execute()
	{
		Worlds.hopTo(Worlds.getRandom(x -> x.isNormal() && !x.isMembers()), true);

		return 100;
	}
}
