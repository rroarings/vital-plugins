package dev.vital.fisher.tasks;

import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class PortPiscarilius implements ScriptTask {
	private static final WorldArea PORT_PISC_BANK = new WorldArea(1800, 3787, 4, 10, 0);
	@Override
	public boolean validate() {

		Player local = LocalPlayer.get();

		return local != null && !PORT_PISC_BANK.contains(local);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return -1;
		}

		Movement.walkTo(WorldLocation.PISCARILIUS_ANGLERFISH.getWorldArea().getCenter());

		return -1;
	}
}
