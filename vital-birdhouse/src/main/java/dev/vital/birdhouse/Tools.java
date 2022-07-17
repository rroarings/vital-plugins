package dev.vital.birdhouse;

import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.widgets.Dialog;

import java.util.List;

public class Tools
{
	public static void waitForInvChange(int timeout) {

		int free_space = Inventory.getFreeSlots();
		Time.sleepUntil(() -> free_space != Inventory.getFreeSlots(), timeout);
	}

	public static void waitForOpenDialog(int timeout) {

		Time.sleepUntil(Dialog::isOpen, timeout);
	}
	public static boolean buildBirdhouse(WorldPoint p, int log_id, int seed_id) {

		var space = TileObjects.getFirstAt(p, x -> x.getName().equals("Space"));
		var birdhouse_empty = TileObjects.getFirstAt(p, x -> x.getName().contains("irdhouse (empty)"));
		var birdhouse = TileObjects.getFirstAt(p, x -> x.getName().contains("irdhouse"));
		if (space != null) {

			var birdhouse_item = Inventory.getFirst(ItemID.BIRD_HOUSE, ItemID.OAK_BIRD_HOUSE,
					ItemID.WILLOW_BIRD_HOUSE,
					ItemID.YEW_BIRD_HOUSE, ItemID.MAGIC_BIRD_HOUSE, ItemID.MAPLE_BIRD_HOUSE,
					ItemID.TEAK_BIRD_HOUSE, ItemID.MAHOGANY_BIRD_HOUSE, ItemID.REDWOOD_BIRD_HOUSE);
			if (birdhouse_item != null) {

				birdhouse_item.useOn(space);

				waitForInvChange(1800);
			}
			else {
				if (Dialog.isOpen() || Dialog.isViewingOptions()) {

					Keyboard.sendSpace();

					waitForInvChange(1800);
				}
				else {

					if(Inventory.contains(log_id, ItemID.CLOCKWORK)) {

						Inventory.getFirst(log_id).useOn(Inventory.getFirst(ItemID.CLOCKWORK));

						waitForOpenDialog(1800);
					}
				}
			}
		}
		else if (birdhouse_empty != null) {

			Inventory.getFirst(seed_id).useOn(birdhouse_empty);

			waitForOpenDialog(1800);

			return true;
		}
		else if (birdhouse != null) {

			birdhouse.interact("Empty");

			waitForInvChange(1800);
		}

		return false;
	}

	public static boolean goTo(WorldArea area) {

		if (area.contains(LocalPlayer.get())) {

			return true;
		}

		Movement.walkTo(area);

		return false;
	}

	static public boolean goToBank(WorldArea area, String entity, String action, boolean is_npc) {

		if (Bank.isOpen()) {

			return true;
		}

		if (Tools.goTo(area)) {

			if (is_npc) {

				var npc = NPCs.getNearest(entity);
				if(npc != null) {
					npc.interact(action);
				}
			}
			else {

				var object = TileObjects.getNearest(entity);
				if(object != null) {
					object.interact(action);
				}
			}
		}

		return false;
	}

	public static boolean hasItems(List<BItems> items) {

		for(var item : items) {

			if(item.id == ItemID.DIGSITE_PENDANT_1) {

				if(Inventory.getFirst(ItemID.DIGSITE_PENDANT_1, ItemID.DIGSITE_PENDANT_2,
						ItemID.DIGSITE_PENDANT_3, ItemID.DIGSITE_PENDANT_4, ItemID.DIGSITE_PENDANT_5) != null) {

					item.obtained = true;
				}
			}
			else if(Inventory.contains(item.id) && Inventory.getCount(item.stacks, item.id) >= item.amount) {

				item.obtained = true;
			}
		}

		return items.stream().allMatch(x -> x.obtained);
	}

	static boolean withdrawDigsitePendant() {

		if(Inventory.getFirst(ItemID.DIGSITE_PENDANT_1, ItemID.DIGSITE_PENDANT_2,
				ItemID.DIGSITE_PENDANT_3, ItemID.DIGSITE_PENDANT_4, ItemID.DIGSITE_PENDANT_5) != null) {

			return true;
		}

		var pendant = Bank.getFirst(ItemID.DIGSITE_PENDANT_1, ItemID.DIGSITE_PENDANT_2,
				ItemID.DIGSITE_PENDANT_3, ItemID.DIGSITE_PENDANT_4, ItemID.DIGSITE_PENDANT_5);

		int free_slots = Inventory.getFreeSlots();

		if(pendant != null) {
			Bank.withdraw(pendant.getId(),1, Bank.WithdrawMode.DEFAULT);

			Time.sleepUntil(() -> free_slots != Inventory.getFreeSlots(), 1800);

			return true;
		}

		return false;
	}

	public static boolean withdrawBankItems(List<BItems> items) {

		if(!Bank.isOpen()) {

			return false;
		}

		for(var item : items) {

			if(item.id == ItemID.DIGSITE_PENDANT_1) {

				if(withdrawDigsitePendant()) {
					item.obtained = true;
					continue;
				}

				break;
			}

			int free_slots = Inventory.getFreeSlots();
			int count = Inventory.getCount(item.stacks, item.id);

			if(count == item.amount) {

				item.obtained = true;
			}
			else if(Bank.contains(item.id) && Bank.getCount(true, item.id) >= item.amount - count) {

				Bank.withdraw(item.id, item.amount - count, Bank.WithdrawMode.DEFAULT);

				Time.sleepUntil(() -> free_slots != Inventory.getFreeSlots() || Inventory.getCount(item.stacks,
						item.id) > count, 1800);

				item.obtained = Inventory.getCount(item.stacks, item.id) >= item.amount;
			}
			else {

				System.out.println("Not enought materials in the bank! " + item.id);
			}
		}

		return items.stream().allMatch(x -> x.obtained);
	}
}
