package dev.vital.quester.quests;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.Quest;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.ScriptTask;
import dev.vital.quester.tasks.Taskz;
import dev.vital.quester.tools.CheckItems;
import dev.vital.quester.tools.GetItems;
import dev.vital.quester.tools.PurchaseItems;
import dev.vital.quester.tools.ItemList;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.widgets.WidgetInfo;

import java.util.Arrays;
import java.util.List;

public class SheepShearer implements ScriptTask
{
	List<ItemList> item_list = Arrays.asList(
			new ItemList(ItemID.BALL_OF_WOOL, 200, 20, false, Bank.WithdrawMode.ITEM, false, ""),
			new ItemList(ItemID.SPADE, 200, 1, false, Bank.WithdrawMode.ITEM, false, ""),
			new ItemList(ItemID.COINS_995, 0, 60, true, Bank.WithdrawMode.ITEM, false, "")
	);

	private static final WorldArea freds_house_outside = new WorldArea(3188, 3280, 2, 2, 0);

	@Override
	public boolean validate(VitalQuesterConfig quester_config)
	{
		return (Quest.SHEEP_SHEARER.getState() != QuestState.FINISHED && quester_config.automatic())
				|| (Quest.SHEEP_SHEARER.getState() != QuestState.FINISHED && !quester_config.automatic()
				&& quester_config.questName().compareToIgnoreCase("Sheep Shearer") == 0);
	}

	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking() || !CheckItems.check(item_list) || !PurchaseItems.purchase(item_list) || !GetItems.get(item_list)) {

			return Rand.nextInt(1000, 1200);
		}

		var fred = NPCs.getNearest("Fred the Farmer");
		if(fred == null) {
			Movement.walkTo(freds_house_outside.getRandom());
		}
		else if(!Reachable.isInteractable(fred)) {
			Movement.walkTo(fred);
		}
		else {
			if(!Dialog.isOpen()) {
				fred.interact("Talk-to");
			}
			else if(Dialog.canContinue()){

				Keyboard.sendSpace();
				return Rand.nextInt(100, 200);
			}
			else if(Dialog.isViewingOptions()){
				if(Tools.getDialogueHeader().contains("Start the Sheep Shearer quest?")) {
					Dialog.chooseOption("Yes.");
				}
				else if(Tools.getDialogueHeader().contains("Select an Option")) {
					Dialog.chooseOption("I'm looking for a quest.");
				}
			}
		}

		return Rand.nextInt(1000, 1200);
	}
}
