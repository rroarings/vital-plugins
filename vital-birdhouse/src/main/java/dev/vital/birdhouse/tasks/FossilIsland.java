package dev.vital.birdhouse.tasks;

import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.coords.WorldArea;

public class FossilIsland implements ScriptTask {

	private static final WorldArea FOSSIL_ISLAND_TELE_ROOM = new WorldArea(3760, 3867, 9, 25, 1);

	VitalBirdhouseConfig config = null;

	public FossilIsland(VitalBirdhouseConfig config) {

		this.config = config;
	}

	@Override
	public boolean validate() {

		return VitalBirdhouse.step.equals(Steps.FOSSIL_ISLAND);
	}

	@Override
	public int execute() {

		if(!FOSSIL_ISLAND_TELE_ROOM.contains(LocalPlayer.get())) {

			Movement.walkTo(FOSSIL_ISLAND_TELE_ROOM);
		}
		else {

			VitalBirdhouse.step = Steps.VALLEY;
		}

		return -1;
	}
}