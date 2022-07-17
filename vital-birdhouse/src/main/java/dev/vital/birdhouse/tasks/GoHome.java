package dev.vital.birdhouse.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;

public class GoHome implements ScriptTask {

	VitalBirdhouseConfig config;

	public GoHome(VitalBirdhouseConfig config) { this.config = config; }

	@Override
	public boolean validate() { return VitalBirdhouse.step.equals(Steps.GO_HOME) && config.returnToGE(); }

	@Override
	public int execute() {

		if(!Inventory.isEmpty() && config.returnToGE() && Tools.goToBank(WorldLocation.GRAND_EXCHANGE.getWorldArea(),
				"Banker", "Bank", true)) {

			Bank.depositInventory();

			Time.sleepUntil(Inventory::isEmpty, 2400);

			Bank.close();

			Time.sleepUntil(() -> !Bank.isOpen(), 2400);

			VitalBirdhouse.step = Steps.GETS_MATS;
			GetMats.bank_items.forEach(x -> x.obtained = false);

			if(config.autoLogOut()) {
				Game.logout();
			}
			else {
				VitalBirdhouse.plugin_enabled = false;
			}
		}

		return -1;
	}
}