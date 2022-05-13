package dev.unethicalite.scripts.cooking.tasks;

import dev.unethicalite.api.account.LocalPlayer;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.commons.Time;
import dev.unethicalite.api.input.Keyboard;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.movement.Movement;
import dev.unethicalite.api.widgets.Dialog;
import net.runelite.api.DialogOption;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.MenuAction;
import net.runelite.api.Player;

public class MakeWine implements ScriptTask
{
	@Override
	public boolean validate() {

		Player local = LocalPlayer.getEntity();
		return local != null && !Inventory.contains(ItemID.JUG) && Inventory.contains(ItemID.JUG_OF_WATER) && Inventory.contains(ItemID.GRAPES);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.getEntity();

		if (local.isAnimating() || Movement.isWalking()) {

			return 2300;
		}

		Item grapes = Inventory.getFirst(ItemID.GRAPES);

		grapes.useOn(Inventory.getFirst(ItemID.JUG_OF_WATER));

		Time.sleep(Rand.nextInt(1800, 2500));

		Keyboard.sendSpace();
		System.out.println(MenuAction.WIDGET_FIRST_OPTION.name());
		return Rand.nextInt(2000, 4000);
	}
}
