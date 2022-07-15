package dev.vital.quester;

import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.Quests;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;

public class Tools {

	public static String getDialogueHeader() {

		Widget widget = Widgets.get(WidgetID.DIALOG_OPTION_GROUP_ID, 1);
		if (!Widgets.isVisible(widget)) {

			return "";
		}

		Widget[] children = widget.getChildren();
		if (children == null) {

			return "";
		}

		return children[0].getText();
	}

	public static void talkTo(String name, WorldPoint gen_location, String... options) {

		if(Dialog.canContinue()) {

			Dialog.continueSpace();
		}
		else if(getDialogueHeader().contains("Start")) {

			Dialog.chooseOption("Yes.");
		}
		else if(Dialog.isViewingOptions()) {

			Dialog.chooseOption(options);
		}
		else {

			var nearest_npc = NPCs.getNearest(name);
			if (nearest_npc != null) {

				if (Reachable.isInteractable(nearest_npc)) {

					nearest_npc.interact("Talk-to");

					Time.sleepUntil(() -> Dialog.isOpen(), 1800);
				}
				else {
					Movement.walkTo(nearest_npc);
				}
			}
			else {

				Movement.walkTo(gen_location);
			}
		}
	}

	public static boolean getQuest(Quest quest, String npc_name, WorldPoint point, String... options) {

		if(Quests.getState(quest) == QuestState.IN_PROGRESS) {

			return true;
		}

		talkTo(npc_name, point, options);

		return false;
	}
}
