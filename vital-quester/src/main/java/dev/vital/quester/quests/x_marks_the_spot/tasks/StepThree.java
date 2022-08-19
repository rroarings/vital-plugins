package dev.vital.quester.quests.x_marks_the_spot.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class StepThree implements ScriptTask
{
	private final WorldPoint dig_three_point = new WorldPoint(3109, 3264, 0);

	VitalQuesterConfig config;

	public StepThree(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Inventory.contains(ItemID.MYSTERIOUS_ORB_23069);
	}

	@Override
	public int execute()
	{

		if (!LocalPlayer.get().getWorldLocation().equals(dig_three_point))
		{
			if (!Movement.isWalking())
			{
				Movement.walkTo(dig_three_point);
			}
			return -1;
		}

		Inventory.getFirst(ItemID.SPADE).interact("Dig");

		return -1;
	}
}