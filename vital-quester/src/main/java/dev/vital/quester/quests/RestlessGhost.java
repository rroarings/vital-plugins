package dev.vital.quester.quests;

import net.runelite.api.Quest;
import net.runelite.api.coords.WorldPoint;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;

public class RestlessGhost implements ScriptTask
{
	VitalQuesterConfig config;

	public RestlessGhost(VitalQuesterConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {
		return false;
	}

	boolean has_ghost_speak_amulet = false;

	@Override
	public int execute() {

		/*if(!Tools.getQuest(Quest.THE_RESTLESS_GHOST, "Father Aereck", new WorldPoint(3243, 3210, 0),
				"I'm looking for a quest!")) {

			return -1;
		}

		if(!has_ghost_speak_amulet) {

			Tools.talkTo("Father Urhney", new WorldPoint(3147, 3174, 0), "Father Aereck sent me to talk to you.", "He" +
					"'s got a ghost hunting his graveyard.");

			return -1;
		}*/

		return -1;
	}
}
