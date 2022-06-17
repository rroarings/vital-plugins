package dev.vital.fighter.tasks;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.Quest;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.ObjectID;
import net.runelite.api.Player;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

public class UpgradeHelmet implements ScriptTask
{
	private static final WorldArea GRAND_EXCHANGE_AREA = new WorldArea(3162, 3487, 6, 6, 0);

	private static int wanted_helm = -1;
	private static int wanted_chest = -1;
	private static int wanted_legs = -1;
	private static int wanted_shield = -1;


	@Override
	public boolean validate() {

		var defence_level = Skills.getLevel(Skill.DEFENCE);

		var weapon_id = Equipment.fromSlot(EquipmentInventorySlot.HEAD).getId();

		if(defence_level < 5 && weapon_id != ItemID.IRON_FULL_HELM) {

			wanted_helm = ItemID.IRON_FULL_HELM;
			wanted_chest = ItemID.IRON_PLATEBODY;
			wanted_legs = ItemID.IRON_PLATELEGS;
			wanted_shield = ItemID.IRON_KITESHIELD;

			return true;
		}
		else if(defence_level >= 5 && defence_level < 10 && weapon_id != ItemID.STEEL_FULL_HELM) {

			wanted_helm = ItemID.STEEL_FULL_HELM;
			wanted_chest = ItemID.STEEL_PLATEBODY;
			wanted_legs = ItemID.STEEL_PLATELEGS;
			wanted_shield = ItemID.STEEL_KITESHIELD;

			return true;
		}
		else if(defence_level >= 10 && defence_level < 20 && weapon_id != ItemID.BLACK_FULL_HELM) {

			wanted_helm = ItemID.BLACK_FULL_HELM;
			wanted_chest = ItemID.BLACK_PLATEBODY;
			wanted_legs = ItemID.BLACK_PLATELEGS;
			wanted_shield = ItemID.BLACK_KITESHIELD;

			return true;
		}
		else if(defence_level >= 20 && defence_level < 30 && weapon_id != ItemID.MITHRIL_FULL_HELM) {

			wanted_helm = ItemID.MITHRIL_FULL_HELM;
			wanted_chest = ItemID.MITHRIL_PLATEBODY;
			wanted_legs = ItemID.MITHRIL_PLATELEGS;
			wanted_shield = ItemID.MITHRIL_KITESHIELD;

			return true;
		}
		else if(defence_level >= 30 && defence_level < 40 && weapon_id != ItemID.ADAMANT_FULL_HELM) {

			wanted_helm = ItemID.ADAMANT_FULL_HELM;
			wanted_chest = ItemID.ADAMANT_PLATEBODY;
			wanted_legs = ItemID.ADAMANT_PLATELEGS;
			wanted_shield = ItemID.ADAMANT_KITESHIELD;

			return true;
		}
		else if(defence_level >= 40 && defence_level < 60 && weapon_id != ItemID.RUNE_FULL_HELM) {

			wanted_helm = ItemID.RUNE_FULL_HELM;
			wanted_chest = ItemID.RUNE_PLATEBODY;
			wanted_legs = ItemID.RUNE_PLATELEGS;
			wanted_shield = ItemID.RUNE_KITESHIELD;

			return true;
		}

		return false;
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return 1000;
		}

		if(!GRAND_EXCHANGE_AREA.contains(local)) {

			Movement.walkTo(GRAND_EXCHANGE_AREA);
		}
		else if(!Inventory.contains(wanted_helm, wanted_chest, wanted_legs, wanted_shield)) {

			if(!Bank.isOpen()) {

				NPCs.getNearest(NpcID.BANKER_1634).interact("Bank");
			}
			else {

				Bank.withdraw(wanted_helm, 1, Bank.WithdrawMode.DEFAULT);
				Bank.withdraw(wanted_chest, 1, Bank.WithdrawMode.DEFAULT);
				Bank.withdraw(wanted_legs, 1, Bank.WithdrawMode.DEFAULT);
				Bank.withdraw(wanted_shield, 1, Bank.WithdrawMode.DEFAULT);
			}
		}
		else {

			if(Bank.isOpen()) {

				Bank.close();
			}
			else {

				Inventory.getFirst(wanted_helm).interact("Wear");
				Inventory.getFirst(wanted_chest).interact("Wear");
				Inventory.getFirst(wanted_legs).interact("Wear");
				Inventory.getFirst(wanted_shield).interact("Wear");
			}
		}

		return Rand.nextInt(1200, 2500);
	}
}
