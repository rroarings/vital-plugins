package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;

public class GetApron implements ScriptTask
{
	private final WorldPoint apron_point = new WorldPoint(3105, 3224, 0);

	VitalQuesterConfig config;

	public GetApron(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return !Equipment.contains(ItemID.WHITE_APRON);
	}

	@Override
	public int execute()
	{

		if (!Inventory.contains(ItemID.WHITE_APRON))
		{

			if (Tools.interactWith("White apron", "Take", apron_point, Tools.EntityType.TILE_ITEM) == -5)
			{

				return -5;
			}
		}
		else
		{
			Inventory.getFirst(ItemID.WHITE_APRON).interact("Wear");
		}

		return -1;
	}
}
