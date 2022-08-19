package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

public class BuyRum implements ScriptTask
{
	private final WorldPoint zambo_location = new WorldPoint(2928, 3144, 0);

	VitalQuesterConfig config;
	BasicTask buy_rum = new BasicTask(() ->
			Tools.purchaseFrom("Zambo", zambo_location, ItemID.KARAMJAN_RUM, 1, false));

	public BuyRum(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return !buy_rum.taskCompleted();
	}

	@Override
	public int execute()
	{

		if (!buy_rum.taskCompleted())
		{
			return buy_rum.execute();
		}

		return -1;
	}
}
