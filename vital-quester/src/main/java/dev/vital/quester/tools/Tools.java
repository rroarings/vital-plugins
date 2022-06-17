package dev.vital.quester.tools;

import net.unethicalite.api.widgets.Widgets;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;

public class Tools
{
	public static String getDialogueHeader()
	{
		Widget widget = Widgets.get(WidgetID.DIALOG_OPTION_GROUP_ID, 1);
		if (!Widgets.isVisible(widget))
		{
			return "";
		}

		Widget[] children = widget.getChildren();
		if (children == null)
		{
			return "";
		}

		return children[0].getText();
	}
}