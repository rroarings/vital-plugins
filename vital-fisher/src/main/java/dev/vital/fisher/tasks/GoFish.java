package dev.vital.fisher.tasks;

import dev.vital.fisher.VitalFisher;
import dev.vital.fisher.VitalFisherConfig;
import dev.vital.quester.tools.Tools;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;

public class GoFish implements ScriptTask
{
	VitalFisherConfig config;
	public GoFish(VitalFisherConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return !Inventory.isFull();
	}

	@Override
	public int execute()
	{
		var npc = NPCs.getNearest(x -> x.hasAction(config.action()));
		if (npc != null)
		{
			if (Reachable.isInteractable(npc) && LocalPlayer.get().distanceTo(npc) < 8)
			{
				if (!Tools.isAnimating(5))
				{
					npc.interact(config.action());
					Time.sleep(Rand.nextInt(config.minDelay() * 1000, config.maxDelay() * 1000));
				}
			}
			else if (!Movement.isWalking())
			{
				Movement.walkTo(npc);
			}
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(VitalFisher.fish_location);
		}

		return -1;
	}
}
