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
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;
import net.unethicalite.client.Static;

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
				entity = NPCs.getNearest(x -> x.hasAction(action) && x.getName().equals(name));
				break;
			}
			case TILE_ITEM:{
				entity = TileItems.getNearest(x -> x.hasAction(action) && x.getName().equals(name));
				break;
			}
			case TILE_OBJECT:{
				entity = TileObjects.getNearest(x -> x.hasAction(action) && x.getName().equals(name));
				break;
			}
			default:{
				return false;
			}
		}

		if(type == EntityType.NPC && ((entity != null && LocalPlayer.get().getInteracting() == entity) || (Dialog.isOpen() || Dialog.isViewingOptions() || Dialog.canContinue() || Shop.isOpen()))) {

			return true;
		}

		if(entity != null && Reachable.isInteractable(entity)) {

			entity.interact(action);

			return true;
		}
		else {

			if(!Movement.isWalking()) {
				Movement.walkTo(point);
			}
		}

		return false;
	}
	public static boolean interactWith(int id, String action, WorldPoint point, EntityType type) {

		SceneEntity entity;
		switch(type) {
			case NPC:{
				entity = NPCs.getNearest(id);
				break;
			}
			case TILE_ITEM:{
				entity = TileItems.getNearest(id);
				break;
			}
			case TILE_OBJECT:{
				entity = TileObjects.getNearest(id);
				break;
			}
			default:{
				return false;
			}
		}

		if(LocalPlayer.get().getInteracting() == entity && Dialog.isOpen()) {

			return true;
		}

		if(entity != null && Reachable.isInteractable(entity)) {

			entity.interact(action);

			return true;
		}
		else {

			if(!Movement.isWalking()) {
				Movement.walkTo(point);
			}
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

	static int animation_tick = 0;
	public static boolean isAnimating(int delta) {

		int tick_count = Static.getClient().getTickCount();
		if(LocalPlayer.get().isAnimating()) {
			animation_tick = tick_count;
		}

		return (tick_count - animation_tick) <= delta;
	}
}
