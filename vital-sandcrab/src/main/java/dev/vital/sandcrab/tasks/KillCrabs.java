package dev.vital.sandcrab.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.sandcrab.Spot;
import dev.vital.sandcrab.VitalSandCrab;
import dev.vital.sandcrab.VitalSandCrabConfig;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.movement.Movement;

public class KillCrabs implements ScriptTask
{

	VitalSandCrabConfig config;
	boolean should_aggro = false;
	Spot final_spot = null;

	public KillCrabs(VitalSandCrabConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return WorldLocation.CRAB_CLAW_ISLE.getWorldArea().contains(LocalPlayer.get());
	}

	@Override
	public int execute()
	{

		if (should_aggro)
		{

			if (!LocalPlayer.get().getWorldLocation().equals(final_spot.reset_spot))
			{

				Movement.walk(final_spot.reset_spot);
			}
			else
			{

				should_aggro = false;
			}

			return -1;
		}

		if (VitalSandCrab.should_find_new_spot)
		{

			for (var spot : VitalSandCrab.spots)
			{

				Spot new_spot = null;
				for (var player : Players.getAll(x -> !x.equals(LocalPlayer.get())))
				{

					if (!player.getWorldLocation().equals(spot.spot))
					{

						new_spot = spot;
					}
					else
					{

						new_spot = null;
					}
				}

				final_spot = new_spot;
			}
		}

		if (final_spot != null)
		{

			VitalSandCrab.should_find_new_spot = false;
		}
		else if (LocalPlayer.get().getInteracting() == null)
		{

			if (config.hopWorlds())
			{
				var current_world = Worlds.getCurrentWorld();
				Worlds.hopTo(Worlds.getRandom(x -> x.isMembers() && !x.isSkillTotal() && !x.isAllPkWorld() && !x.isLeague() && !x.isTournament() && !x.isPvpArena()));
				Time.sleepUntil(() -> current_world != Worlds.getCurrentWorld(), 1000 * 30);
			}
		}

		if (!VitalSandCrab.should_find_new_spot)
		{

			if (!LocalPlayer.get().getWorldLocation().equals(final_spot.spot))
			{

				Movement.walk(final_spot.spot);
			}
			else
			{

				if (VitalSandCrab.no_target_ticks >= 10)
				{

					should_aggro = true;
				}
			}
		}

		return -1;
	}
}