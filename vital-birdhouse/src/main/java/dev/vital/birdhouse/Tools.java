package dev.vital.birdhouse;

import com.openosrs.client.game.WorldLocation;
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

public class Tools
{
	public static void waitForInvChange(int timeout) {

		int free_space = Inventory.getFreeSlots();
		Time.sleepUntil(() -> free_space != Inventory.getFreeSlots(), timeout);
	}

	public static void waitForOpenDialog(int timeout) {

		Time.sleepUntil(() -> Dialog.isOpen(), timeout);
	}
	public static boolean buildBirdhouse(WorldPoint p, int log_id, int seed_id) {

		var space = TileObjects.getFirstAt(p, x -> x.getName().equals("Space"));
		var birdhouse_empty = TileObjects.getFirstAt(p, x -> x.getName().equals("Birdhouse (empty)"));
		var birdhouse = TileObjects.getFirstAt(p, x -> x.getName().equals("Birdhouse"));
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

					Inventory.getFirst(log_id).useOn(Inventory.getFirst(ItemID.CLOCKWORK));

					waitForOpenDialog(1800);
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

	static public boolean goToBanker(WorldArea area, String entity, String action, boolean is_npc) {

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

	public static boolean withdrawBankItem(int id, int amount) {

		boolean ready_to_go = false;

		if(Bank.isOpen()) {

			int free_slots = Inventory.getFreeSlots();
			int count = Inventory.getCount(id);

			if (count != amount) {

				if (Bank.getCount(id) >= amount) {

					Bank.withdraw(id, amount - count, Bank.WithdrawMode.DEFAULT);
					ready_to_go = true;
				}
			}

			Time.sleepUntil(() -> free_slots != Inventory.getFreeSlots(), 1800);
		}

		return ready_to_go;
	}
}
