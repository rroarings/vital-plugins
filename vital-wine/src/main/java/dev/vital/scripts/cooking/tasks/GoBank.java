package dev.vital.scripts.cooking.tasks;

import dev.vital.scripts.cooking.VitalWineConfig;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoBank implements ScriptTask
{
	private static final WorldPoint COOKING_GUILD_DOOR = new WorldPoint(3143, 3443, 0);
	private static final WorldArea COOKING_GUILD_0 = new WorldArea(3138, 3444, 7, 7, 0);
	private static final WorldArea VARROCK_WEST_BANK_AREA = new WorldArea(3181, 3434, 5, 10, 0);

	List<WidgetInfo> bank_number_widgets = Arrays.asList(WidgetInfo.BANK_PIN_1, WidgetInfo.BANK_PIN_2,
			WidgetInfo.BANK_PIN_3, WidgetInfo.BANK_PIN_4, WidgetInfo.BANK_PIN_5, WidgetInfo.BANK_PIN_6,
			WidgetInfo.BANK_PIN_7, WidgetInfo.BANK_PIN_8, WidgetInfo.BANK_PIN_9, WidgetInfo.BANK_PIN_10);

	List<WidgetInfo> display_number_widgets = Arrays.asList(WidgetInfo.BANK_PIN_FIRST _ENTERED,
			WidgetInfo.BANK_PIN_SECOND_ENTERED, WidgetInfo.BANK_PIN_THIRD_ENTERED, WidgetInfo.BANK_PIN_FOURTH_ENTERED);

	VitalWineConfig config;

	public GoBank(VitalWineConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {

		return Inventory.contains(ItemID.JUG_OF_WINE) || Inventory.contains(ItemID.JUG_OF_BAD_WINE);
	}

	void enterPin(String pin) {

		for(var display_widget : display_number_widgets) {

			if (Widgets.get(display_widget).getText().contains("?")) {

				for (var bank_number_widget : bank_number_widgets) {

					for (var digit : pin.toCharArray()){

						var number_widget = Widgets.get(bank_number_widget).getChild(1);
						if (number_widget.getText().contains(String.valueOf(digit))) {

							var p = number_widget.getClickPoint();
							Mouse.click(p.getX(), p.getY(), true);
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || local.isMoving()) {

			return -1;
		}

		var widget = Widgets.get(WidgetInfo.BANK_PIN_TOP_LEFT_TEXT);

		if(COOKING_GUILD_0.contains(local)) {

			TileObjects.getFirstAt(COOKING_GUILD_DOOR, x -> x.hasAction("Open")).interact("Open");
		}
		else if(!VARROCK_WEST_BANK_AREA.contains(local)){

			Movement.walkTo(VARROCK_WEST_BANK_AREA.getCenter());
		}
		else if(!Bank.isOpen() && widget == null) {

			TileObjects.getNearest("Bank booth").interact("Bank");
		}
		else if(widget != null) {

			enterPin(config.bankPin());
		}
		else {
			Bank.depositInventory();
		}

		return -1;
	}
}
