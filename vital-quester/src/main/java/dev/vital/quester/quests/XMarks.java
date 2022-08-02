package dev.vital.quester.quests;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.Quest;
import net.unethicalite.api.widgets.Dialog;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.tools.CheckItems;
import dev.vital.quester.tools.GetItems;
import dev.vital.quester.tools.PurchaseItems;
import dev.vital.quester.tools.ItemList;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

import java.util.Arrays;
import java.util.List;

public class XMarks implements ScriptTask
{
	VitalQuesterConfig config;

	private static final WorldArea OUTSIDE_LUMB_CASTLE = new WorldArea(3227, 3231	, 4, 4, 0);
	private static final WorldArea SECOND_VEOS = new WorldArea(3051, 3246	, 2, 2, 0);

	List<ItemList> item_list = Arrays.asList(
			new ItemList(ItemID.SPADE, 200, 1, false, Bank.WithdrawMode.ITEM, false, "")
	);

	public XMarks(VitalQuesterConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return false;
	}

	int getQuest() {

		var veos = NPCs.getNearest("Veos");
		if(veos == null) {
			Movement.walkTo(OUTSIDE_LUMB_CASTLE.getRandom());
		}
		else if(!Reachable.isInteractable(veos)) {
			Movement.walkTo(veos);
		}
		else {
			if(!Dialog.isOpen()) {
				veos.interact("Talk-to");
			}
			else if(Dialog.canContinue()){

				Keyboard.sendSpace();
				return Rand.nextInt(100, 200);
			}
			else if(Dialog.isViewingOptions()){
				if(Tools.getDialogueHeader().contains("Start the X Marks the Spot quest?")) {
					Dialog.chooseOption("Yes.");
				}
				else if(Tools.getDialogueHeader().contains("Select an Option")) {
					Dialog.chooseOption("I'm looking for a quest.", "Okay, thanks Veos.");
				}
			}
		}

		return Rand.nextInt(1000, 1200);
	}

	int firstDig() {

		var dig_spot = new WorldPoint(3230, 3209, 0);
		if(LocalPlayer.get().getWorldLocation().equals(dig_spot)) {
			Inventory.getFirst(ItemID.SPADE).interact("Dig");
		}
		else {
			Movement.walkTo(dig_spot);
		}

		return Rand.nextInt(1000, 1200);
	}

	int secondDig() {

		var dig_spot = new WorldPoint(3203, 3212, 0);
		if(LocalPlayer.get().getWorldLocation().equals(dig_spot)) {
			Inventory.getFirst(ItemID.SPADE).interact("Dig");
		}
		else {
			Movement.walkTo(dig_spot);
		}

		return Rand.nextInt(1000, 1200);
	}

	int thirdDig() {

		var dig_spot = new WorldPoint(3109, 3264, 0);
		if(LocalPlayer.get().getWorldLocation().equals(dig_spot)) {
			Inventory.getFirst(ItemID.SPADE).interact("Dig");
		}
		else {
			Movement.walkTo(dig_spot);
		}

		return Rand.nextInt(1000, 1200);
	}

	int fourthDig() {

		var dig_spot = new WorldPoint(3078, 3259, 0);
		if(LocalPlayer.get().getWorldLocation().equals(dig_spot)) {
			Inventory.getFirst(ItemID.SPADE).interact("Dig");
		}
		else {
			Movement.walkTo(dig_spot);
		}

		return Rand.nextInt(1000, 1200);
	}

	int returnChest() {

		var veos = NPCs.getNearest("Veos");
		if(veos == null) {
			Movement.walkTo(SECOND_VEOS.getRandom());
		}
		else if(!Reachable.isInteractable(veos)) {
			Movement.walkTo(veos);
		}
		else
		{
			if(!Dialog.isOpen()) {
				veos.interact("Talk-to");
			}
			else if(Dialog.canContinue()){

				Keyboard.sendSpace();
				return Rand.nextInt(100, 200);
			}
		}

		return Rand.nextInt(1000, 1200);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking() || !CheckItems.check(item_list) || !PurchaseItems.purchase(item_list) || !GetItems.get(item_list)) {

			return Rand.nextInt(1000, 1200);
		}

		switch(Quest.X_MARKS_THE_SPOT.getProgress()) {
			case 1: return getQuest();
			case 2: return firstDig();
			case 3: return secondDig();
			case 4: return thirdDig();
			case 5: return fourthDig();
			case 6: return returnChest();
			default: return Rand.nextInt(1000, 1200);
		}
	}
}
