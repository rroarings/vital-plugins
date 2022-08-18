package dev.vital.scripts.cooking.tasks;

import dev.vital.scripts.cooking.VitalWineConfig;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class MakeWine implements ScriptTask
{

	VitalWineConfig config;

	public MakeWine(VitalWineConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {

		return !Inventory.contains(ItemID.JUG) && Inventory.contains(ItemID.JUG_OF_WATER) && Inventory.contains(ItemID.GRAPES);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return -1;
		}

		Inventory.getFirst(ItemID.GRAPES).useOn(Inventory.getFirst(ItemID.JUG_OF_WATER));

		Time.sleep(Rand.nextInt(1400, 1800));

		Keyboard.sendSpace();

		return -1;
	}
}
