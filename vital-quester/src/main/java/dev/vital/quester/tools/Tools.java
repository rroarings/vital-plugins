package dev.vital.quester.tools;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.unethicalite.api.SceneEntity;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;

public class Tools
{
	public enum EntityType {
		NPC,
		TILE_OBJECT,
		TILE_ITEM
	}

	public static boolean localHas(int... ids) {

		return Inventory.contains(ids) || Equipment.contains(ids);
	}
	public static String getDialogueHeader()
	{
		Widget widget = Widgets.get(WidgetID.DIALOG_OPTION_GROUP_ID, 1);
		if (!Widgets.isVisible(widget))
		{
			return "";
		}

		Widget[] children = widget.getChildren();
		if (children == null)
		{
			return "";
		}

		return children[0].getText();
	}

	public static boolean interactWith(String name, String action, WorldPoint point, EntityType type) {

		SceneEntity entity;
		switch(type) {
			case NPC:{
				entity = NPCs.getNearest(name);
				break;
			}
			case TILE_ITEM:{
				entity = TileItems.getNearest(name);
				break;
			}
			case TILE_OBJECT:{
				entity = TileObjects.getNearest(name);
				break;
			}
			default:{
				return false;
			}
		}

		if(LocalPlayer.get().getInteracting() == entity && Dialog.isOpen()) {

			return true;
		}

		if(entity != null) {

			if(Reachable.isInteractable(entity)) {

				entity.interact(action);

				return true;
			}
			else {
				Movement.walkTo(entity);
			}
		}
		else {

			Movement.walkTo(point);
		}

		return false;
	}

	public static boolean startQuest(String quest) {

		if (Dialog.isViewingOptions() && getDialogueHeader().contains("Start the " + quest + " quest?")) {

			return Dialog.chooseOption("Yes.");
		}

		return false;
	}

	public static boolean selectOptions(String... options) {

		if(Dialog.isViewingOptions() && Tools.getDialogueHeader().contains("Select an Option")) {

			return Dialog.chooseOption(options);
		}

		return false;
	}
}
