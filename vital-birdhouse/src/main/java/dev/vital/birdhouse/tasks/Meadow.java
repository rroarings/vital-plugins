package dev.vital.birdhouse.tasks;

import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

public class Meadow implements ScriptTask
{

	private static final WorldArea MEADOW_AREA = new WorldArea(3672, 3867, 10, 20, 0);
	private static final WorldPoint MEADOW_TILE_1 = new WorldPoint(3677, 3882, 0);

	VitalBirdhouseConfig config = null;

	public Meadow(VitalBirdhouseConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return VitalBirdhouse.step.equals(Steps.MEADOW);
	}

	@Override
	public int execute()
	{

		if (Tools.goTo(MEADOW_AREA))
		{

			if (Tools.buildBirdhouse(MEADOW_TILE_1, config.logID(), config.seedID()))
			{

				VitalBirdhouse.step = Steps.MEADOW2;
			}
		}

		return -1;
	}
}