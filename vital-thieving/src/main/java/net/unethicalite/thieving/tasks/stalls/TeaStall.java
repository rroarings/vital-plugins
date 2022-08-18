package net.unethicalite.thieving.tasks.stalls;

import net.runelite.api.ItemID;
import net.runelite.api.World;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.thieving.ThievingType;
import net.unethicalite.thieving.VitalThievingConfig;
import net.unethicalite.thieving.tasks.ScriptTask;

public class TeaStall implements ScriptTask
{
	private static final WorldPoint TEA_STALL = new WorldPoint(3269, 3410, 0);
	private static final WorldArea TEA_STALL_AREA = new WorldArea(3268, 3412, 4, 4, 0);

	VitalThievingConfig config;

	public TeaStall(VitalThievingConfig config) { this.config = config; }

	@Override
	public boolean validate() { return config.thievingType().equals(ThievingType.STALL_TEA); }

	@Override
	public int execute() {

		if(!Players.getAll(x -> !x.equals(LocalPlayer.get()) && TEA_STALL_AREA.contains(x) && x.getInteracting() != null).isEmpty()) {
			Worlds.hopTo(Worlds.getRandom(World::isMembers));
			return -12;
		}

		if(Inventory.isFull() && config.dropItems()) {

			for (var item : Inventory.getAll(ItemID.CUP_OF_TEA_1978)) {

				item.interact("Drop");

				Time.sleep(180, 230);
			}
		}
		else {
			var tea_stall = TileObjects.getNearest("Tea stall");
			if(tea_stall != null && Reachable.isInteractable(tea_stall)) {

				tea_stall.interact("Steal-from");

				Time.sleep(1500);
			}
			else {

				Movement.walkTo(TEA_STALL);
			}
		}

		return -1;
	}
}
