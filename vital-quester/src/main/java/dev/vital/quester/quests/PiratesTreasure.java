package dev.vital.quester.quests;

import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
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
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetInfo;

import java.util.Arrays;
import java.util.List;

public class PiratesTreasure implements ScriptTask
{
	enum Events {
		GO_TO_KARAMJA,
		BUY_RUM,
		PICK_BANANAS,
		TALK_TO_LUTHAS_ONE,
		STUFF_CRATE,
		TALK_TO_LUTHAS_TWO,
		LEAVE_KARAMJA,
		GET_RUM,
		GO_TO_CHEST,
		GO_TO_FALADOR
	}
	private static Events event = Events.GO_TO_KARAMJA;
	private boolean talked_to_luthas_one = false;
	private boolean talked_to_luthas_two = false;
	private boolean talked_to_wydin = false;
	List<ItemList> item_list = Arrays.asList(
			new ItemList(ItemID.SPADE, 200, 1, false, Bank.WithdrawMode.ITEM, false, ""),
			new ItemList(ItemID.COINS_995, 0, 60, true, Bank.WithdrawMode.ITEM, false, "")
	);

	private static final WorldArea REDBEARD_FRANK = new WorldArea(3188, 3280, 2, 2, 0);
	private static final WorldArea BOARD_TO_KARAMJA = new WorldArea(3026, 3216, 3, 3, 0);
	private static final WorldArea KARAMJA_BOAT = new WorldArea(2955, 3142, 3, 2, 1);
	private static final WorldArea KARAMJA_BOAT_PORT_SARIM = new WorldArea(3033, 3216, 3, 2, 1);
	private static final WorldArea ZAMBO = new WorldArea(2922, 3146, 2, 2, 0);
	private static final WorldArea LUTHAS = new WorldArea(2937, 3148, 2, 2, 0);

	@Override
	public boolean validate(VitalQuesterConfig quester_config)
	{
		return (Quest.PIRATES_TREASURE.getState() != QuestState.FINISHED && quester_config.automatic())
				|| (Quest.PIRATES_TREASURE.getState() != QuestState.FINISHED && !quester_config.automatic()
				&& quester_config.questName().compareToIgnoreCase("Pirate's Treasure") == 0);
	}

	int getQuest() {

		var frank = NPCs.getNearest("Redbeard Frank");
		if(frank == null) {
			Movement.walkTo(REDBEARD_FRANK.getRandom());
		}
		else if(!Reachable.isInteractable(frank)) {
			Movement.walkTo(frank);
		}
		else {
			if(!Dialog.isOpen()) {
				frank.interact("Talk-to");
			}
			else if(Dialog.canContinue()){

				Keyboard.sendSpace();
				return Rand.nextInt(100, 200);
			}
			else if(Dialog.isViewingOptions()){
				if(Tools.getDialogueHeader().contains("Start the Pirate's Treasure quest?")) {
					Dialog.chooseOption("Yes.");
				}
				else if(Tools.getDialogueHeader().contains("Select an Option")) {
					Dialog.chooseOption("I'm in search of treasure.");
				}
			}
		}

		return Rand.nextInt(1000, 1200);
	}
	int rum() {

		switch (event) {
			case GO_TO_KARAMJA: {

				if(KARAMJA_BOAT.contains(LocalPlayer.get())) {
					event = Events.BUY_RUM;
					break;
				}

				var seaman = NPCs.getNearest("Seaman Lorris");
				if(seaman == null) {
					Movement.walkTo(BOARD_TO_KARAMJA.getRandom());
				}
				else if(!Reachable.isInteractable(seaman)) {
					Movement.walkTo(seaman);
				}
				else {
					if(!Dialog.isOpen()) {
						seaman.interact("Pay-fare");
					}
					else if(Dialog.canContinue()){

						Keyboard.sendSpace();
						return Rand.nextInt(100, 200);
					}
					else if(Dialog.isViewingOptions()){
						if(Tools.getDialogueHeader().contains("Select an Option")) {
							Dialog.chooseOption("Yes please.");
						}
					}
				}
				break;
			}
			case BUY_RUM: {

				if(Inventory.contains(ItemID.KARAMJAN_RUM)) {
					event = Events.PICK_BANANAS;
					break;
				}

				var zambo = NPCs.getNearest("Zambo");
				if(zambo == null) {
					Movement.walkTo(ZAMBO.getRandom());
				}
				else if(!Reachable.isInteractable(zambo)) {
					Movement.walkTo(zambo);
				}
				else {
					if(!Shop.isOpen()) {
						zambo.interact("Trade");
					}
					else {
						Shop.buyOne(ItemID.KARAMJAN_RUM);
					}
				}

				break;
			}
			case PICK_BANANAS: {

				if(Inventory.getCount(ItemID.BANANA) != 10) {
					TileObjects.getNearest(x -> x.hasAction("Pick")).interact("Pick");
				}
				else {
					event = Events.TALK_TO_LUTHAS_ONE;
				}
				break;
			}
			case TALK_TO_LUTHAS_ONE: {

				var luthas = NPCs.getNearest("Luthas");
				if(luthas == null) {
					Movement.walkTo(LUTHAS.getRandom());
				}
				else if(!Reachable.isInteractable(luthas)) {
					Movement.walkTo(luthas);
				}
				else {
					if(!Dialog.isOpen()) {
						if(talked_to_luthas_one) {
							event = Events.STUFF_CRATE;
							break;
						}
						luthas.interact("Talk-to");
					}
					else if(Dialog.canContinue()){

						Keyboard.sendSpace();
						return Rand.nextInt(100, 200);
					}
					else if(Dialog.isViewingOptions()){
						if(Tools.getDialogueHeader().contains("Select an Option")) {
							Dialog.chooseOption("Could you offer me employment on your plantation?");
							talked_to_luthas_one = true;
						}
					}
				}

				break;
			}
			case STUFF_CRATE: {
				var crate = TileObjects.getNearest(x -> x.hasAction("Fill"));
				if(crate != null) {

					if(Inventory.contains(ItemID.KARAMJAN_RUM)){
						Inventory.getFirst(ItemID.KARAMJAN_RUM).useOn(crate);
					}
					else if(Inventory.contains(ItemID.BANANA)) {
						crate.interact("Fill");
					}
					else {
						event = Events.TALK_TO_LUTHAS_TWO;
					}
				}
				else {
					Movement.walkTo(LUTHAS.getRandom());
				}

				break;
			}
			case TALK_TO_LUTHAS_TWO: {

				var luthas = NPCs.getNearest("Luthas");
				if(luthas == null) {
					Movement.walkTo(LUTHAS.getRandom());
				}
				else if(!Reachable.isInteractable(luthas)) {
					Movement.walkTo(luthas);
				}
				else {
					if(!Dialog.isOpen()) {
						if(talked_to_luthas_two) {
							event = Events.LEAVE_KARAMJA;
							break;
						}
						luthas.interact("Talk-to");
					}
					else if(Dialog.canContinue()){
						talked_to_luthas_two = true;
						Keyboard.sendSpace();
						return Rand.nextInt(100, 200);
					}
				}
				break;
			}
			case LEAVE_KARAMJA: {
				if(KARAMJA_BOAT_PORT_SARIM.contains(LocalPlayer.get())) {
					event = Events.GET_RUM;
				}
				var officer = NPCs.getNearest("Customs officer");
				if(officer == null) {
					Movement.walkTo(LUTHAS.getRandom());
				}
				else if(!Reachable.isInteractable(officer)) {
					Movement.walkTo(officer);
				}
				else {
					if(!Dialog.isOpen()) {

						officer.interact("Pay-Fare");
					}
					else if(Dialog.canContinue()){

						Keyboard.sendSpace();
						return Rand.nextInt(100, 200);
					}
					else if(Dialog.isViewingOptions()){
						if(Tools.getDialogueHeader().contains("Select an Option")) {
							Dialog.chooseOption("Can I journey on this ship?");
							Dialog.chooseOption("Search away, I have nothing to hide.");
							Dialog.chooseOption("Ok.");
						}
					}
				}
				break;
			}
			case GET_RUM: {
				if(!Equipment.contains(ItemID.WHITE_APRON)) {
					if(Inventory.contains(ItemID.WHITE_APRON)) {
						Inventory.getFirst(ItemID.WHITE_APRON).interact("Wear");
					}
					else {
						var apron = TileObjects.getNearest(ItemID.WHITE_APRON);
						if(Reachable.isInteractable(apron)) {
							apron.interact("Take");
						}
						else {
							Movement.walkTo(apron);
						}
					}
				}
				else {
					if(talked_to_wydin) {
						if(Inventory.contains(ItemID.KARAMJAN_RUM)) {
							var frank = NPCs.getNearest("Redbeard Frank");
							if(frank == null) {
								Movement.walkTo(REDBEARD_FRANK.getRandom());
							}
							else if(!Reachable.isInteractable(frank)) {
								Movement.walkTo(frank);
							}
							else {
								if(!Dialog.isOpen()) {
									frank.interact("Talk-to");
								}
								else if(Dialog.canContinue()){
									Keyboard.sendSpace();
									return Rand.nextInt(100, 200);
								}
								else if(Dialog.isViewingOptions()){
									if(Tools.getDialogueHeader().contains("Select an Option")) {
										Dialog.chooseOption("Ok thanks, I'll go and get it.");
										event = Events.GO_TO_CHEST;
									}
								}
							}
						}
						else {

							if(LocalPlayer.get().getWorldLocation().equals(new WorldPoint(3010, 3207, 0))) {
								TileObjects.getNearest("Crate").interact("Search");
							}
							else {
								Movement.walkTo(new WorldPoint(3010, 3207, 0));
							}
						}
					}
					else
					{
						var wydin = NPCs.getNearest("Wydin");
						if (wydin == null)
						{
							Movement.walkTo(LUTHAS.getRandom());
						}
						else if (!Reachable.isInteractable(wydin))
						{
							Movement.walkTo(wydin);
						}
						else
						{
							if (!Dialog.isOpen())
							{
								wydin.interact("Talk-to");
							}
							else if (Dialog.canContinue())
							{
								Keyboard.sendSpace();
								return Rand.nextInt(100, 200);
							}
							else if (Dialog.isViewingOptions())
							{
								if (Tools.getDialogueHeader().contains("Select an Option"))
								{
									Dialog.chooseOption("Can I get a job here?");
									talked_to_wydin = true;
								}
							}
						}
					}
				}
				break;
			}
			case GO_TO_CHEST: {
				if(!Inventory.contains("Pirate message"))
				{
					if (new WorldPoint(3219, 3395, 1).equals(LocalPlayer.get().getWorldLocation()))
					{
						Inventory.getFirst("Chest key").useOn(TileObjects.getNearest("Chest"));
					}
					else
					{
						Movement.walkTo(new WorldPoint(3219, 3395, 1));
					}
				}
				else {

					Inventory.getFirst(433).interact("Read");
					event = Events.GO_TO_FALADOR;
				}
			}
			case GO_TO_FALADOR: {
				if (new WorldPoint(2999, 3383, 1).equals(LocalPlayer.get().getWorldLocation()))
				{
					var gardener = NPCs.getNearest("Gardener");
					if(!gardener.getInteracting().equals(LocalPlayer.get())) {
						Inventory.getFirst("Dig").useOn(TileObjects.getNearest("Chest"));
					}
					else {
						if(!LocalPlayer.get().getInteracting().equals(gardener)) {
							gardener.interact("Attack");
						}
					}
				}
				else
				{
					Movement.walkTo(new WorldPoint(2999, 3383, 1));
				}
			}
			default: break;
		}

		return Rand.nextInt(1000, 1200);
	}
	@Override
	public int execute() {

		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking() || !CheckItems.check(item_list) || !PurchaseItems.purchase(item_list) || !GetItems.get(item_list)) {

			return Rand.nextInt(1000, 1200);
		}

		switch(Quest.PIRATES_TREASURE.getProgress()) {
			case 1: return getQuest();
			case 2: return rum();
			default: return Rand.nextInt(1000, 1200);
		}
	}
}
