package dev.vital.birdhouse;

import net.unethicalite.api.items.Bank;

public class BItems {

	public int id;
	public int amount;
	public boolean stacks;
	public boolean obtained;
	public Bank.WithdrawMode mode;

	public BItems(int id, int amount, boolean stacks, Bank.WithdrawMode mode) {

		this.id = id;
		this.amount = amount;
		this.stacks = stacks;
		this.mode = mode;
		this.obtained = false;
	}
}