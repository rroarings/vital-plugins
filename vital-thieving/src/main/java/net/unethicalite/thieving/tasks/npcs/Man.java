package net.unethicalite.thieving.tasks.npcs;

import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.thieving.ThievingType;
import net.unethicalite.thieving.VitalThievingConfig;
import net.unethicalite.thieving.tasks.ScriptTask;

public class Man implements ScriptTask
{
	private static final WorldArea TEA_STALL = new WorldArea(3267, 3408, 5, 7, 0);

	VitalThievingConfig config;

	public Man(VitalThievingConfig config) {

		this.config = config;
	}

	@Override
	public boolean validate() {

		return config.thievingType().equals(ThievingType.NPC_MAN);
	}

	@Override
	public int execute() {

		var local_player = LocalPlayer.get();
		if (local_player.isAnimating() || local_player.getGraphic() == 245) {

			return -1;
		}

		if (Inventory.getCount(true, ItemID.COIN_POUCH) >= 28) {

			Inventory.getFirst(ItemID.COIN_POUCH).interact("Open-all");
		}
		else {

			NPCs.getNearest("Man", "Woman").interact("Pickpocket");
		}

		return -2;
	}
}
