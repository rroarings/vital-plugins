package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.widgets.Widgets;

public class EquipDagger implements ScriptTask
{
	VitalQuesterConfig config;

	public EquipDagger(VitalQuesterConfig config)
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
				return widget_child.getText().contains("You can see what items you are wearing");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		Widgets.get(85, 0, 8).interact("Equip");
		//Inventory.getFirst("Bronze dagger").interact("Equip");

		return -5;
	}
}
