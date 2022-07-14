package dev.vital.birdhouse.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.ItemID;

public class GetMats implements ScriptTask {

	VitalBirdhouseConfig config = null;

	public GetMats(VitalBirdhouseConfig config) {

		this.config = config;
	}

	@Override
	public boolean validate() {

		if (!config.autoMats() && VitalBirdhouse.step == Steps.GETS_MATS) {

			VitalBirdhouse.step = Steps.FOSSIL_ISLAND;
		}

		return VitalBirdhouse.step.equals(Steps.GETS_MATS) && config.autoMats();
	}

	@Override
	public int execute() {

		if(Tools.goToBanker(WorldLocation.GRAND_EXCHANGE.getWorldArea(), "Banker", "Bank", true)) {

			if (Tools.withdrawBankItem(config.returnTeleport(), 1) && Tools.withdrawBankItem(ItemID.CHISEL, 1)
					&& Tools.withdrawBankItem(ItemID.HAMMER, 1)
					&& (Tools.withdrawBankItem(Bank.getFirst(ItemID.DIGSITE_PENDANT_1, ItemID.DIGSITE_PENDANT_2,
					ItemID.DIGSITE_PENDANT_3, ItemID.DIGSITE_PENDANT_4, ItemID.DIGSITE_PENDANT_5).getId(), 1)
					|| Tools.withdrawBankItem(ItemID.TELEPORT_TO_HOUSE, 1))
					&& Tools.withdrawBankItem(config.logID(), 4) && Tools.withdrawBankItem(config.seedID(), 40)) {

				VitalBirdhouse.step = Steps.FOSSIL_ISLAND;
			}
			else {

				System.out.println("Unable to get all items.");
			}
		}

		return -1;
	}
}
