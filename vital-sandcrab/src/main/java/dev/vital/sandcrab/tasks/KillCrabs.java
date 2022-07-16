package dev.vital.sandcrab.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.sandcrab.VitalSandCrabConfig;
import dev.vital.sandcrab.VitalSandCrab;
import net.runelite.api.World;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.movement.Movement;


public class KillCrabs implements dev.vital.birdhouse.tasks.ScriptTask {

	WorldPoint aggro = new WorldPoint(1773, 3447, 0);

	VitalSandCrabConfig config;

	public KillCrabs(VitalSandCrabConfig config) { this.config = config; }

	@Override
	public boolean validate() { return WorldLocation.CRAB_CLAW_ISLE.getWorldArea().contains(LocalPlayer.get()); }

	WorldPoint getValidSpot() {

		WorldPoint final_spot = null;

		for(var player : Players.getAll()) {

			if(player == LocalPlayer.get()) {

				continue;
			}

			WorldPoint valid_spot = null;
			for(var spot : VitalSandCrab.spots) {

				if(player.getWorldLocation().equals(spot)) {

					continue;
				}

				valid_spot = spot;
			}

			final_spot = valid_spot;
		}

		return final_spot;
	}

	@Override
	public int execute() {

		if (VitalSandCrab.no_target_ticks > 10) {

			Movement.walkTo(aggro);

			Time.sleepUntil(() -> LocalPlayer.get().getWorldLocation().equals(aggro), 10000);
		}
		else {

			var spot = getValidSpot();
			if (spot != null) {

				Movement.walkTo(spot);
			}
			else {

				Worlds.hopTo(Worlds.getRandom(World::isMembers));
				return -10;
			}
		}

		return -1;
	}
}