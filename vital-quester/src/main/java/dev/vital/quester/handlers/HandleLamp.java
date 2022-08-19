package dev.vital.quester.handlers;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class HandleLamp implements ScriptTask
{
	VitalQuesterConfig config;

	public HandleLamp(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return config.handleLamp() && Inventory.getFirst(x -> x.hasAction("Rub") && x.getName().contains("lamp")) != null;
	}

	@Override
	public int execute()
	{

		var choose_skill_lamp_menu = Widgets.get(240, config.lampSkill().ordinal() + 4);
		if (choose_skill_lamp_menu != null && choose_skill_lamp_menu.hasAction(config.lampSkill().getName()) && choose_skill_lamp_menu.getChild(8).getSpriteId() != 1158)
		{

			choose_skill_lamp_menu.interact(config.lampSkill().getName());
			return -3;
		}

		var confirm_button = Widgets.get(240, 26);
		if (confirm_button != null)
		{

			var confirm_button_child = confirm_button.getChild(0);
			if (confirm_button_child != null && confirm_button_child.getText().equals("Confirm: " + config.lampSkill().getName()))
			{
				confirm_button.interact("Confirm");
				return -3;
			}

			return -1;
		}

		var item = Inventory.getFirst(x -> x.hasAction("Rub") && x.getName().contains("lamp"));
		if (item != null)
		{
			item.interact("Rub");
			return -3;
		}

		return -1;
	}
}
