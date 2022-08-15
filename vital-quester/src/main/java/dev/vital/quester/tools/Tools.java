package dev.vital.quester.tools;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.unethicalite.api.SceneEntity;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.QuestVarPlayer;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;
import net.unethicalite.client.Static;

import java.util.List;

import static java.lang.Math.abs;

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

	/*public static int pickUpItem(int id, WorldPoint point) {
		var item = TileItems.getNearest(id);
		if(item != null && Reachable.isInteractable(item)) {
			item.interact("Take");
			return -5;
		}
		else if(!Movement.isWalking()){
			Movement.walkTo(point);
		}

		return -1;
	}*/

	public static int talkTo(String name, WorldPoint point, List<String> dialog) {

		if(Dialog.canContinue()) {
			Dialog.continueSpace();
			return -1;
		}

		if(dialog != null) {
			boolean is_wanted_text = false;
			for (var d : dialog) {
				if(d == null) {
					continue;
				}
				if (Dialog.getOptions().stream().anyMatch(x -> x.getText().contains(d))) {
					is_wanted_text = true;
				}
			}

			if (Dialog.isViewingOptions() && is_wanted_text) {
				for (var option : dialog) {
					if (Dialog.chooseOption(option)) {
						dialog.remove(option);
						return -2;
					}
				}
				return -2;
			}
		}
		var entity = NPCs.getNearest(x -> x.getName().equals(name));
		if(entity != null && Reachable.isInteractable(entity) && LocalPlayer.get().getWorldLocation().distanceTo2D(entity.getWorldLocation()) < 10) {

			entity.interact("Talk-to");
			return -5;
		}
		else if(!Movement.isWalking()) {
			Movement.walkTo(point);
		}

		return -1;
	}
	public static int interactWith(String name, String action, WorldPoint point, EntityType type) {

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
				entity = null;
			}
		}

		if(entity != null && Reachable.isInteractable(entity)) {

			entity.interact(action);
			return -5;
		}
		else if(!Movement.isWalking()) {
			Movement.walkTo(point);
		}

		return -1;
	}
	public static int interactWith(int id, String action, WorldPoint point, EntityType type) {

		SceneEntity entity;
		switch(type) {
			case NPC:{
				entity = NPCs.getNearest(x -> x.hasAction(action) && x.getId() == id);
				break;
			}
			case TILE_ITEM:{
				entity = TileItems.getNearest(x -> x.hasAction(action) && x.getId() == id);
				break;
			}
			case TILE_OBJECT:{
				entity = TileObjects.getNearest(x -> x.hasAction(action) && x.getId() == id);
				break;
			}
			default:{
				entity = null;
			}
		}

		if(entity != null && Reachable.isInteractable(entity)) {

			entity.interact(action);
			return -5;
		}
		else if(!Movement.isWalking()) {
			Movement.walkTo(point);
		}

		return -1;
	}
	public static boolean startQuest(String quest) {

		if (Dialog.isViewingOptions() && getDialogueHeader().contains(quest)) {

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

	public static int sellTo(String name, WorldPoint point, int id, int amount, boolean stack) {

		int current_amount = Inventory.getCount(stack, id);
		if(current_amount == amount) {
			return 0;
		}

		if(Shop.isOpen()) {

			int amount_needed = abs(amount - current_amount);
			if(amount_needed >= 50) {
				Shop.sellFifty(id);
			}
			else if(amount_needed >= 10) {
				Shop.sellTen(id);
			}
			else if(amount_needed >= 5) {
				Shop.sellFifty(id);
			}
			else if(amount_needed >= 1) {
				Shop.sellOne(id);
			}

			return -2;
		}

		var shop = NPCs.getNearest(x -> x.hasAction("Trade") && x.getName().equals(name));
		if(shop != null && Reachable.isInteractable(shop)) {
			shop.interact("Trade");
			return -4;
		}
		else if(!Movement.isWalking()) {
			Movement.walkTo(point);
		}

		return -1;
	}

	public static int purchaseFrom(String name, WorldPoint point, int id, int amount, boolean stack) {

		int current_amount = Inventory.getCount(stack, id);
		if(current_amount == amount) {
			return 0;
		}

		if(Shop.isOpen()) {

			int amount_needed = amount - current_amount;
			if(amount_needed >= 50) {
				Shop.buyFifty(id);
			}
			else if(amount_needed >= 10) {
				Shop.buyTen(id);
			}
			else if(amount_needed >= 5) {
				Shop.buyFifty(id);
			}
			else if(amount_needed >= 1) {
				Shop.buyOne(id);
			}

			return -2;
		}

		var shop = NPCs.getNearest(x -> x.hasAction("Trade") && x.getName().equals(name));
		if(shop != null && Reachable.isInteractable(shop)) {
			shop.interact("Trade");
			return -4;
		}
		else if(!Movement.isWalking()) {
			Movement.walkTo(point);
		}

		return -1;
	}

	public static int getQuestProgress(QuestVarPlayer quest) {
		return Vars.getVarp(quest.getId());
	}
}
