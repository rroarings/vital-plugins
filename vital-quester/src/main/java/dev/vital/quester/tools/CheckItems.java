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
import net.unethicalite.api.movement.Movement;

import java.util.List;

public class CheckItems
{

	public static boolean check(List<ItemList> purchase_list)
	{

		if (purchase_list.stream().noneMatch(n -> n.state == ItemState.UNCHECKED))
		{
			return true;
		}

		Player local = LocalPlayer.get();

		if (WorldLocation.GRAND_EXCHANGE.getWorldArea().contains(local))
		{
			var banker = NPCs.getNearest("Banker");

			if (purchase_list.stream().anyMatch(n -> n.state == ItemState.UNCHECKED))
			{
				for (var item : purchase_list)
				{
					if (item.state == ItemState.UNCHECKED)
					{
						if (Inventory.contains(item.item_id) && Inventory.getCount(item.stack, item.item_id) >= item.quantity || (item.equip && Equipment.contains(item.item_id)))
						{
							item.state = ItemState.OBTAINED;
						}
						else if (item.equip && Inventory.contains(item.item_id) && !Equipment.contains(item.item_id))
						{
							Inventory.getFirst(item.item_id).interact(item.interaction);
						}
					}
					Time.sleep(Rand.nextInt(400, 800));
				}

				if (purchase_list.stream().allMatch(n -> n.state == ItemState.OBTAINED))
				{
					return true;
				}

				if (!Bank.isOpen())
				{
					if (banker == null)
					{
						Movement.walkTo(WorldLocation.GRAND_EXCHANGE.getWorldArea().getRandom());
					}
					else
					{
						banker.interact("Bank");
					}
				}
				else
				{
					for (var item : purchase_list)
					{
						if (item.state == ItemState.UNCHECKED)
						{
							if (Bank.contains(item.item_id) && Bank.getCount(true, item.item_id) >= item.quantity)
							{
								item.state = ItemState.PRESENT;
							}
							else
							{
								if (item.purchase_quanity == 0)
								{
									item.purchase_quanity = item.quantity - Bank.getCount(item.item_id);
								}
								item.state = ItemState.ABSENT;
							}
						}
					}
				}
			}
		}
		else
		{
			if (purchase_list.stream().anyMatch(n -> n.state == ItemState.UNCHECKED))
			{
				for (var item : purchase_list)
				{
					if (item.state == ItemState.UNCHECKED)
					{
						if (Inventory.contains(item.item_id) && Inventory.getCount(item.stack, item.item_id) >= item.quantity)
						{
							item.state = ItemState.OBTAINED;
						}
						else
						{

							item.state = ItemState.CHECK_BANK;
						}
					}
				}
			}
		}

		Time.sleep(Rand.nextInt(1000, 1200));
		return false;
	}
}
