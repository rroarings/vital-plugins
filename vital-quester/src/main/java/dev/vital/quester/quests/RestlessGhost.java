package dev.vital.quester.quests;

import dev.vital.quester.Tools;
import net.runelite.api.NPC;
import net.runelite.api.Quest;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.Quests;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.ScriptTask;
import net.runelite.api.Player;
import net.runelite.api.QuestState;
import net.unethicalite.api.widgets.Dialog;

public class RestlessGhost implements ScriptTask {

	@Override
	public boolean validate(VitalQuesterConfig quester_config) {

		return (Quests.getState(Quest.THE_RESTLESS_GHOST) != QuestState.FINISHED && quester_config.automatic())
				|| (Quests.getState(Quest.THE_RESTLESS_GHOST) != QuestState.FINISHED && !quester_config.automatic()
				&& quester_config.questName().compareToIgnoreCase("The Restless Ghost") == 0);
	}

	boolean has_ghost_speak_amulet = false;

	@Override
	public int execute() {

		if(!Tools.getQuest(Quest.THE_RESTLESS_GHOST, "Father Aereck", new WorldPoint(3243, 3210, 0),
				"I'm looking for a quest!")) {

			return -1;
		}

		if(!has_ghost_speak_amulet) {

			Tools.talkTo("Father Urhney", new WorldPoint(3147, 3174, 0), "Father Aereck sent me to talk to you.", "He" +
					"'s got a ghost hunting his graveyard.");

			return -1;
		}

		return -1;
	}
}
