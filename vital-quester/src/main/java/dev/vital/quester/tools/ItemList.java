package dev.vital.quester.tools;

import net.unethicalite.api.items.Bank;

enum ItemState {
	UNCHECKED,
	PRESENT,
	ABSENT,
	OBTAINED,
	CHECK_BANK
}

public class ItemList {
	public int item_id;
	public ItemState state;
	public int price;
	public int quantity;
	public int purchase_quanity;
	public Bank.WithdrawMode mode;
	public boolean equip;
	public String interaction;
	boolean stack;
	public ItemList(int item_id, int price, int quantity, boolean stack, Bank.WithdrawMode mode, boolean equip,
					String interaction)
	{
		this.state = ItemState.UNCHECKED;
		this.item_id = item_id;
		this.price = price;
		this.quantity = quantity;
		this.mode = mode;
		this.purchase_quanity = 0;
		this.equip = equip;
		this.interaction = interaction;
		this.stack = stack;
	}
}