package dev.vital.birdhouse.tasks;

import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.coords.WorldArea;

public class Meadow2 implements ScriptTask {

	private static final WorldArea MEADOW_AREA2 = new WorldArea(3678, 3812, 5, 5, 0);
	private static final WorldPoint MEADOW_TILE_2 = new WorldPoint(3679, 3815, 0);

	VitalBirdhouseConfig config = null;

	public Meadow2(VitalBirdhouseConfig config) { this.config = config; }

	@Override
	public boolean validate() { return VitalBirdhouse.step.equals(Steps.MEADOW2); }

	@Override
	public int execute() {

		if(Tools.goTo(MEADOW_AREA2)) {

			if(Tools.buildBirdhouse(MEADOW_TILE_2, config.logID(), config.seedID())) {

				VitalBirdhouse.step = Steps.END;
			}
		}

		return -1;
	}
}
