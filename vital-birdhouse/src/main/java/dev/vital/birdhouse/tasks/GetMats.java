package dev.vital.birdhouse.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.birdhouse.BItems;
import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.unethicalite.api.items.Bank;
import net.runelite.api.ItemID;

import java.util.ArrayList;
import java.util.List;

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

	public static List<BItems> bank_items = new ArrayList<>();

	@Override
	public int execute() {

		if(Tools.goToBanker(WorldLocation.GRAND_EXCHANGE.getWorldArea(), "Banker", "Bank", true)) {

			if (Tools.withdrawBankItems(bank_items)) {

				VitalBirdhouse.step = Steps.FOSSIL_ISLAND;
			}
		}

		return -1;
	}
}