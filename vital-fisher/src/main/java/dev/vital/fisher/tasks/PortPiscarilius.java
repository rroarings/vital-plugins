package dev.vital.fisher.tasks;

import com.openosrs.client.game.WorldLocation;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.movement.Movement;

public class PortPiscarilius implements ScriptTask
{
	private static final WorldArea PORT_PISC_BANK = new WorldArea(1800, 3787, 4, 10, 0);

	@Override
	public boolean validate()
	{

		return !PORT_PISC_BANK.contains(LocalPlayer.get());
	}

	@Override
	public int execute()
	{

		Movement.walkTo(WorldLocation.PISCARILIUS_ANGLERFISH.getWorldArea());

		return -1;
	}
}
