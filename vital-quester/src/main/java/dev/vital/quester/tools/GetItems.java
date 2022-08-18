package dev.vital.quester.tools;

import com.openosrs.client.game.WorldLocation;
import net.runelite.api.Player;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;

import java.util.List;

public class GetItems
{
	public static boolean get(List<ItemList> purchase_list) {

		var uh = purchase_list.stream().allMatch(n -> n.state == ItemState.OBTAINED);
		if(uh) {
			return true;
		}

		Player local = LocalPlayer.get();

		if(WorldLocation.GRAND_EXCHANGE.getWorldArea().contains(local))
		{
			var banker = NPCs.getNearest("Banker");

			if (purchase_list.stream().anyMatch(n -> n.state == ItemState.PRESENT))
			{
				if (!Bank.isOpen())
				{
					banker.interact("Bank");
				}
				else
				{
					for (var item : purchase_list)
					{
						if (item.state == ItemState.PRESENT)
						{
							if(Inventory.contains(item.item_id) && Inventory.getCount(item.stack, item.item_id) == item.quantity) {
								item.state = ItemState.OBTAINED;
							}
							else if (Bank.contains(item.item_id) && Bank.getCount(item.stack, item.item_id) >= item.quantity)
							{
								if(!item.equip && !Inventory.contains(item.item_id) || Inventory.getCount(item.stack, item.item_id) != item.quantity) {
									Bank.withdraw(item.item_id, item.quantity - Inventory.getCount(item.stack, item.item_id), item.mode);
								}
								else if(item.equip && !Equipment.contains(item.item_id)){

									Inventory.getFirst(item.item_id).interact(item.interaction);
								}
								else {

									item.state = ItemState.OBTAINED;
								}
							}
						}
						Time.sleep(Rand.nextInt(1000, 1200));
					}
				}
			}
		}
		Time.sleep(Rand.nextInt(1000, 1200));
		return false;
	}
}
