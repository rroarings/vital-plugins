package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.widgets.Widgets;

public class AttackChicken implements ScriptTask
{
	VitalQuesterConfig config;

	public AttackChicken(VitalQuesterConfig config)
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
				return widget_child.getText().contains("You now have some runes");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		var chicken = NPCs.getNearest(x -> (x.getInteracting() == null || x.getInteracting() == LocalPlayer.get()) && x.getName().equals("Chicken"));
		if (chicken != null && !Tools.isAnimating(5))
		{
			Magic.cast(SpellBook.Standard.WIND_STRIKE, chicken);
		}

		return -5;
	}
}
