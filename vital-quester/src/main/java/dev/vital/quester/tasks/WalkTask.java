package dev.vital.quester.tasks;

import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.movement.Movement;

public class WalkTask
{

	boolean task_completed;
	WorldPoint point;
	LocalPoint local_point;
	public WalkTask(WorldPoint point)
	{
		this.task_completed = false;
		this.point = point;
	}

	public WalkTask(LocalPoint point)
	{
		this.task_completed = false;
		this.point = null;
	}

	public int execute()
	{

		if (!LocalPlayer.get().getWorldLocation().equals(this.point))
		{
			if (!Movement.isWalking())
			{
				Movement.walkTo(this.point);
			}
		}
		else
		{
			this.task_completed = true;
		}

		return -1;
	}

	public boolean taskCompleted()
	{
		return this.task_completed;
	}
}

