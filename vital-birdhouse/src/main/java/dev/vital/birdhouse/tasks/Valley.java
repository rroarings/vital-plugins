package dev.vital.birdhouse.tasks;

import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.coords.WorldArea;

public class Valley implements ScriptTask {

	private static final WorldArea VALLEY_AREA = new WorldArea(3757, 3754, 13, 9, 0);
	private static final WorldPoint VALLEY_TILE_1 = new WorldPoint(3763, 3755, 0);

	VitalBirdhouseConfig config = null;

	public Valley(VitalBirdhouseConfig config) { this.config = config; }

	@Override
	public boolean validate() { return VitalBirdhouse.step.equals(Steps.VALLEY); }

	@Override
	public int execute() {

		if(Tools.goTo(VALLEY_AREA)) {

			if(Tools.buildBirdhouse(VALLEY_TILE_1, config.logID(), config.seedID())) {

				VitalBirdhouse.step = Steps.VALLEY2;
			}
		}

		return -1;
	}
}
