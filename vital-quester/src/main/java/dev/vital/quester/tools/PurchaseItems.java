package dev.vital.quester.tools;

import com.openosrs.client.game.WorldLocation;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.GrandExchange;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

import java.util.List;

public class PurchaseItems
{
	public static boolean purchase(List<ItemList> purchase_list)
	{

		var uh =
				purchase_list.stream().noneMatch(n -> n.state == ItemState.ABSENT) && purchase_list.stream().noneMatch(n -> n.state == ItemState.UNCHECKED) && purchase_list.stream().noneMatch(n -> n.state == ItemState.CHECK_BANK);
		if (uh)
		{
			return true;
		}

		Player local = LocalPlayer.get();

		if (WorldLocation.GRAND_EXCHANGE.getWorldArea().contains(local))
		{
			var clerk = NPCs.getNearest("Grand Exchange Clerk");
			var banker = NPCs.getNearest("Banker");
			if (purchase_list.stream().anyMatch(n -> n.state == ItemState.CHECK_BANK))
			{
				if (!Bank.isOpen())
				{
					banker.interact("Bank");
				}
				else
				{
					for (var item : purchase_list)
					{
						if (item.state == ItemState.CHECK_BANK)
						{
							if (Bank.contains(item.item_id) && Bank.getCount(item.stack, item.item_id) >= item.quantity && (!Inventory.contains(item.item_id) || Inventory.getCount(item.stack, item.item_id) != item.quantity))
							{
								Bank.withdraw(item.item_id, item.quantity - Inventory.getCount(item.item_id), item.mode);
							}
							else if (!item.equip && Inventory.contains(item.item_id) && Inventory.getCount(item.stack, item.item_id) == item.quantity)
							{

								item.state = ItemState.OBTAINED;
							}
							else if (item.equip && !Equipment.contains(item.item_id) && Inventory.contains(item.item_id))
							{
								Inventory.getFirst(item.item_id).interact(item.interaction);
							}
							else if (item.equip && Equipment.contains(item.item_id))
							{

								item.state = ItemState.OBTAINED;
							}
							else
							{
								item.state = ItemState.ABSENT;
							}
							break;
						}
					}
				}
			}
			if (purchase_list.stream().anyMatch(n -> n.state == ItemState.ABSENT))
			{
				if (Inventory.contains(ItemID.COINS_995) && Inventory.getFreeSlots() < 27)
				{
					if (!Bank.isOpen())
					{
						banker.interact("Bank");
					}
					else
					{
						Bank.depositAllExcept(ItemID.COINS_995);
					}
				}
				else if (!Inventory.contains(ItemID.COINS_995) || Inventory.getCount(true, ItemID.COINS_995) < 10000)
				{
					if (!Bank.isOpen())
					{
						banker.interact("Bank");
					}
					else
					{
						var count = Bank.getCount(true, ItemID.COINS_995);
						if (count >= 10000)
						{
							Bank.withdraw(ItemID.COINS_995, 10000, Bank.WithdrawMode.ITEM);
						}
					}
				}
				else if (Bank.isOpen())
				{
					Bank.close();
				}
				else if (!GrandExchange.isOpen())
				{
					clerk.interact("Exchange");
				}
				else
				{
					for (var item : purchase_list)
					{
						if (item.state == ItemState.ABSENT)
						{
							if (GrandExchange.buy(item.item_id, item.quantity, item.price, true,
									true))
							{
								if (GrandExchange.canCollect())
								{
									Time.sleep(1200);
									GrandExchange.collect(true);
									item.state = ItemState.PRESENT;
								}
							}
							break;
						}
					}
				}
			}
		}
		else
		{

			Movement.walkTo(WorldLocation.GRAND_EXCHANGE.getWorldArea().getRandom());
		}
		Time.sleep(Rand.nextInt(1000, 1200));
		return false;
	}
}
