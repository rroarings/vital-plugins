package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class SmeltOre implements ScriptTask
{
	VitalQuesterConfig config;

	public SmeltOre(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		var widget = Widgets.get(263, 1);
		if (widget != null)
		{
			var widget_child = widget.getChild(0);
			if (widget_child != null)
			{
				return widget_child.getText().contains("You now have some tin ore");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (Inventory.contains(ItemID.COPPER_ORE) && Inventory.contains(ItemID.TIN_ORE))
		{

			TileObjects.getNearest("Furnace").interact("Use");
		}

		return -5;
	}
}
