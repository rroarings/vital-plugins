package dev.vital.birdhouse;

import net.unethicalite.api.items.Bank;

public class BItems {

	int id;
	int amount;
	boolean stacks;
	boolean obtained;
	Bank.WithdrawMode mode;

	public BItems(int id, int amount, boolean stacks, Bank.WithdrawMode mode) {

		this.id = id;
		this.amount = amount;
		this.stacks = stacks;
		this.mode = mode;
		this.obtained = false;
	}
}