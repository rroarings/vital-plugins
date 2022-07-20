package dev.vital.prayer.tasks;

import dev.vital.prayer.VitalPrayerConfig;
import net.runelite.api.ItemID;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Dialog;

public class UnnoteBones implements ScriptTask
{
	WorldArea inside_widly_chaos_alter = new WorldArea(2948, 3819, 10, 4	, 0);

	VitalPrayerConfig config;

	public UnnoteBones(VitalPrayerConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {
		return Worlds.inMembersWorld() && Inventory.getCount(true, ItemID.COINS_995) >= 50 && Inventory.contains(config.notedBoneID()) && !Inventory.contains(config.boneID());
	}

	@Override
	public int execute()
	{
		var elder_chaos_druid = NPCs.getNearest(7995);
		if(elder_chaos_druid != null) {

			if(Reachable.isInteractable(elder_chaos_druid)) {

				if (Dialog.isViewingOptions()) {

					Dialog.chooseOption("Exchange All:");
				}
				else {

					Inventory.getFirst(config.notedBoneID()).useOn(elder_chaos_druid);
				}
			}
			else {
				if(!Movement.isWalking()) {

					Movement.walkTo(elder_chaos_druid);
				}
			}
		}
		else {

			if(!Movement.isWalking()) {

				Movement.walkTo(inside_widly_chaos_alter);
			}
		}

		return -1;
	}
}
