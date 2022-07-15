package dev.vital.birdhouse.tasks;

import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.runelite.api.coords.WorldArea;

public class FossilIsland implements ScriptTask
{

	private static final WorldArea FOSSIL_ISLAND_TELE_ROOM = new WorldArea(3763, 3868, 3, 3, 1);

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

		if(Tools.goTo(FOSSIL_ISLAND_TELE_ROOM)) {

			VitalBirdhouse.step = Steps.VALLEY;
		}

		return -1;
	}
}