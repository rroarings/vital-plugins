package dev.vital.birdhouse.tasks;

import com.openosrs.client.game.WorldLocation;
import dev.vital.birdhouse.Steps;
import dev.vital.birdhouse.Tools;
import dev.vital.birdhouse.VitalBirdhouse;
import dev.vital.birdhouse.VitalBirdhouseConfig;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;

public class GoHome implements ScriptTask {

	VitalBirdhouseConfig config = null;

	public GoHome(VitalBirdhouseConfig config) { this.config = config; }

	@Override
	public boolean validate() { return VitalBirdhouse.step.equals(Steps.GO_HOME) && config.returnToGE(); }

	@Override
	public int execute() {

		if(!Inventory.isEmpty() && config.returnToGE() && Tools.goToBanker(WorldLocation.GRAND_EXCHANGE.getWorldArea(),
				"Banker", "Bank", true)) {

			Bank.depositInventory();

			Time.sleepUntil(() -> Inventory.isEmpty(), 2400);

			VitalBirdhouse.plugin_enabled = false;
			VitalBirdhouse.step = Steps.GETS_MATS;
		}

		return -1;
	}
}