package dev.vital.birdhouse.tasks;

import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

public class Valley2 implements ScriptTask
{

	private static final WorldArea VALLEY_AREA = new WorldArea(3757, 3754, 13, 9, 0);
	private static final WorldPoint VALLEY_TILE_2 = new WorldPoint(3768, 3761, 0);

	VitalBirdhouseConfig config = null;

	public Valley2(VitalBirdhouseConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return VitalBirdhouse.step.equals(Steps.VALLEY2);
	}

	@Override
	public int execute()
	{

		if (Tools.goTo(VALLEY_AREA))
		{

			if (Tools.buildBirdhouse(VALLEY_TILE_2, config.logID(), config.seedID()))
			{

				VitalBirdhouse.step = Steps.MEADOW;
			}
		}

		return -1;
	}
}