package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import net.runelite.api.ItemID;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.quests.QuestVarbits;
import net.unethicalite.api.widgets.Widgets;

public class PlayPiano implements ScriptTask
{
	VitalQuesterConfig config;
	BasicTask play_piano = new BasicTask(() ->
	{
		var counter = Vars.getBit(4049);
		var piano_widget = Widgets.get(554, 5);
		if (piano_widget != null)
		{
			if (counter == 0)
			{
				Mouse.click(Widgets.get(554, 21).getClickPoint().getAwtPoint(), true);
			}
			else if (counter == 1)
			{
				Mouse.click(Widgets.get(554, 22).getClickPoint().getAwtPoint(), true);
			}
			if (counter == 2)
			{
				Mouse.click(Widgets.get(554, 25).getClickPoint().getAwtPoint(), true);
			}
			if (counter == 3)
			{
				Mouse.click(Widgets.get(554, 21).getClickPoint().getAwtPoint(), true);
				return 0;
			}
		}
		else
		{
			TileObjects.getNearest("Piano").interact("Play");
			return -5;
		}

		return -2;
	});
	BasicTask get_emerald_key = new BasicTask(() ->
	{
		if (Inventory.contains(ItemID.EMERALD_KEY_21054))
		{
			return 0;
		}

		var piano = TileObjects.getNearest(29658);
		if (piano.hasAction("Search"))
		{
			piano.interact("Search");
			return -5;
		}

		return -1;
	});

	public PlayPiano(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 75;
	}

	@Override
	public int execute()
	{

		if (!play_piano.taskCompleted())
		{
			return play_piano.execute();
		}
		else if (!get_emerald_key.taskCompleted())
		{
			return get_emerald_key.execute();
		}

		return -1;
	}
}
