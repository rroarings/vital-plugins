package net.unethicalite.thieving.tasks.npcs;

import net.runelite.api.Skill;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.runelite.api.ItemID;
import net.unethicalite.client.Static;
import net.unethicalite.thieving.ThievingType;
import net.unethicalite.thieving.VitalThievingConfig;
import net.unethicalite.thieving.tasks.ScriptTask;

public class ArdougneKnight implements ScriptTask
{
	//private static final WorldArea TEA_STALL = new WorldArea(3267, 3408, 5, 7, 0);

	VitalThievingConfig config;

	public ArdougneKnight(VitalThievingConfig config) {

		this.config = config;
	}

	@Override
	public boolean validate() {

		return config.thievingType().equals(ThievingType.NPC_ARDOUGNE_KNIGHT);
	}

	void getItem(int item, int amount) {

		if (Bank.isOpen()) {

			if(Inventory.getFreeSlots() < amount) {

				Bank.depositInventory();
			}
			else {

				Bank.withdraw(item, amount, Bank.WithdrawMode.ITEM);
			}
		}
		else {

			TileObjects.getNearest("Bank booth").interact("Bank");
		}
	}

	@Override
	public int execute() {

		var local_player = LocalPlayer.get();
		if (local_player.isAnimating() || local_player.getGraphic() == 245) {

			return -1;
		}

		int current_hp = Static.getClient().getBoostedSkillLevel(Skill.HITPOINTS);
		int max_hp = Static.getClient().getRealSkillLevel(Skill.HITPOINTS);
		if(current_hp < (max_hp / 2)) {

			Inventory.getFirst(ItemID.JUG_OF_WINE).interact("Drink");
		}
		else if(Inventory.getCount(true, ItemID.COIN_POUCH_22531) == 28) {

			Inventory.getFirst(ItemID.COIN_POUCH_22531).interact("Open-all");
		}
		else if(!Inventory.contains(ItemID.JUG_OF_WINE)) {

			getItem(ItemID.JUG_OF_WINE, 23);
		}
		else if(!Inventory.contains(ItemID.DODGY_NECKLACE)) {

			getItem(ItemID.DODGY_NECKLACE, 3);
		}
		else if(Bank.isOpen()) {

			Bank.close();
		}
		else if(!Equipment.contains(ItemID.DODGY_NECKLACE)) {

			Inventory.getFirst(ItemID.DODGY_NECKLACE).interact("Wear");
		}
		else {

			NPCs.getNearest("Ardougne Knight").interact("Pickpocket");
		}

		return -2;
	}
}
