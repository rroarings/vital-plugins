package dev.vital.birdhouse.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.birdhouse.*;

import java.util.ArrayList;
import java.util.List;

public class GetMats implements ScriptTask {

	VitalBirdhouseConfig config = null;

	public GetMats(VitalBirdhouseConfig config) { this.config = config; }

	public static List<BItems> bank_items = new ArrayList<>();

	@Override
	public boolean validate() {

		if (!config.autoMats() && VitalBirdhouse.step == Steps.GETS_MATS
		|| Tools.hasItems(bank_items) && VitalBirdhouse.step == Steps.GETS_MATS) {

			VitalBirdhouse.step = Steps.FOSSIL_ISLAND;
		}

		return VitalBirdhouse.step.equals(Steps.GETS_MATS) && config.autoMats();
	}

	@Override
	public int execute() {

		if(Tools.goToBank(WorldLocation.GRAND_EXCHANGE.getWorldArea(), "Banker", "Bank", true)) {

			if (Tools.withdrawBankItems(bank_items)) {

				VitalBirdhouse.step = Steps.FOSSIL_ISLAND;
			}
		}

		return -1;
	}
}