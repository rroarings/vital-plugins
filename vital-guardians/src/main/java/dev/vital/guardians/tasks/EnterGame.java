package dev.vital.guardians.tasks;

import dev.vital.guardians.VitalGuardiansConfig;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

public class EnterGame implements ScriptTask
{

	private static final WorldArea VALLEY_AREA = new WorldArea(3757, 3754, 13, 9, 0);
	private static final WorldPoint VALLEY_TILE_1 = new WorldPoint(3763, 3755, 0);

	VitalGuardiansConfig config = null;

	public EnterGame(VitalGuardiansConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return /*VitalGuardians.step.equals(Steps.VALLEY)*/true;
	}

	@Override
	public int execute()
	{


		return -1;
	}
}