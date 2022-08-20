package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tasks.WalkTask;
import net.runelite.api.GameObject;
import net.runelite.api.GraphicsObject;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;
import net.unethicalite.client.Static;

public class FuckMirrors implements ScriptTask
{
	VitalQuesterConfig config;
	WalkTask walk1 = new WalkTask(new WorldPoint(8022, 5083, 0));
	boolean getWardrobe() {

		//var wardrobe_south = TileObjects.getFirstAt(new WorldPoint(8024, 5081, 0), 30141);
		//var ttt = (GameObject)(wardrobe_south);
		//ttt.getOrientation();
		for (GraphicsObject graphicsObject : Static.getClient().getGraphicsObjects()) {

			//if(graphicsObject.getId() == 483 && wardrobe_south.getLocalLocation().equals(graphicsObject.getLocation())) {
			//	return true;
			//}
		}
		return false;
	}

	BasicTask move_shit = new BasicTask(() ->
	{
		//if (LocalPlayer.get().getWorldLocation().equals(new WorldPoint())) {

		//}
		if (getWardrobe())
		{
			NPCs.getNearest("Mirror").interact("Push");
		}

		return -1;
	});

	public FuckMirrors(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 111;
	}

	@Override
	public int execute()
	{
		var local_direction = LocalPlayer.get();
		var rr = local_direction;
		//var tett = local_direction.getLocalLocation()
		if (!walk1.taskCompleted())
		{
			return walk1.execute();
		}
		else if (!move_shit.taskCompleted())
		{
			return move_shit.execute();
		}

		return -1;
	}
}
