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
		return Worlds.inMembersWorld() && Inventory.getCount(ItemID.COINS_995) >= 50 && Inventory.contains(537) &&
				(!Inventory.contains(ItemID.DRAGON_BONES) || (Inventory.getCount(ItemID.DRAGON_BONES) <= 4 && !inside_widly_chaos_alter.contains(LocalPlayer.get())));
	}

	@Override
	public int execute()
	{
		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return -1;
		}

		var elder_chaos_druid = NPCs.getNearest(7995);
		if(inside_widly_chaos_alter.contains(local)) {
			Movement.walkTo(elder_chaos_druid);
		}
		else {
			if(Dialog.isViewingOptions()) {
				Dialog.chooseOption("Exchange All:");
			}
			else {
				Inventory.getFirst(537).useOn(elder_chaos_druid);
			}
		}

		return -1;
	}
}
