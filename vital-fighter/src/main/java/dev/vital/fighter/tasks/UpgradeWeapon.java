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

public class UpgradeWeapon implements ScriptTask
{
	private static final WorldArea GRAND_EXCHANGE_AREA = new WorldArea(3162, 3487, 6, 6, 0);

	private static int wanted_upgrade_id = -1;

	public int GetWeapon(int attack_level) {

		switch(attack_level) {

			case 1: case 2: case 3: case 4:
				return ItemID.IRON_SCIMITAR;
			case 5: case 6: case 7: case 8: case 9:
				return ItemID.STEEL_SCIMITAR;
			case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19:
				return ItemID.BLACK_SCIMITAR;
			case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29:
				return ItemID.MITHRIL_SCIMITAR;
			case 30: case 31: case 32: case 33: case 34: case 35: case 36: case 37: case 38: case 39:
				return ItemID.ADAMANT_SCIMITAR;
			case 40: case 41: case 42: case 43: case 44: case 45: case 46: case 47: case 48: case 49:
				return ItemID.RUNE_SCIMITAR;
			case 60: case 61: case 62: case 63: case 64: case 65: case 66: case 67: case 68: case 69:
				if(Quest.MONKEY_MADNESS_I.getState() == QuestState.FINISHED) {
					return ItemID.DRAGON_SCIMITAR;
				}
				else {
					return ItemID.RUNE_SCIMITAR;
				}
			default: return ItemID.BRONZE_SCIMITAR;
		}
	}

	@Override
	public boolean validate() {

		var attack_level = Skills.getLevel(Skill.ATTACK);
		wanted_upgrade_id = GetWeapon(attack_level);

		var weapon_id = Equipment.fromSlot(EquipmentInventorySlot.WEAPON).getId();
		if(wanted_upgrade_id == weapon_id) {

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

			System.out.printf("Moving to Grand Exchange");
			Movement.walkTo(GRAND_EXCHANGE_AREA);
		}
		else if(!Inventory.contains(wanted_upgrade_id)) {

			if(!Bank.isOpen()) {

				System.out.printf("Opening bank");
				NPCs.getNearest(NpcID.BANKER_1634).interact("Bank");
			}
			else {

				System.out.printf("Withdrawing item {}", wanted_upgrade_id);
				Bank.withdraw(wanted_upgrade_id, 1, Bank.WithdrawMode.DEFAULT);
			}
		}
		else {

			if(Bank.isOpen()) {

				Bank.close();
			}
			else {

				System.out.printf("Equipping item {}", wanted_upgrade_id);

				Inventory.getFirst(wanted_upgrade_id).interact("Wield");
			}
		}

		return Rand.nextInt(1000, 2500);
	}
}
