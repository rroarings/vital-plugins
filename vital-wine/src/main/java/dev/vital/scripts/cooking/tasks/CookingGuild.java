package dev.vital.scripts.cooking.tasks;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import dev.vital.scripts.cooking.VitalWineConfig;
import net.runelite.api.Player;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

public class CookingGuild implements ScriptTask
{
	private static final WorldPoint COOKING_GUILD_DOOR = new WorldPoint(3143, 3443, 0);
	private static final WorldArea COOKING_GUILD_0 = new WorldArea(3138, 3444, 7, 7, 0);
	private static final WorldArea COOKING_GUILD_1 = new WorldArea(3138, 3444, 7, 7, 1);
	private static final WorldArea COOKING_GUILD_2 = new WorldArea(3138, 3444, 7, 7, 2);

	VitalWineConfig config;

	public CookingGuild(VitalWineConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() {

		var test = config.fuckthisshit();

		Player local = LocalPlayer.get();
		return local != null && Inventory.isEmpty() && !COOKING_GUILD_0.contains(local) && !COOKING_GUILD_1.contains(local) && !COOKING_GUILD_2.contains(local);
	}

	@Override
	public int execute()
	{
		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking())
		{

			return 1000;
		}

		TileObject door = TileObjects.getFirstAt(COOKING_GUILD_DOOR, x -> x.hasAction("Open"));
		if (door == null || door.distanceTo(local) > 20 || !Reachable.isInteractable(door))
		{

			Movement.walkTo(COOKING_GUILD_DOOR);

			Time.sleep(Rand.nextInt(1200, 2500));
		}
		else
		{

			door.interact("Open");

			Time.sleep(Rand.nextInt(1200, 2500));
		}

		return Rand.nextInt(50, 200);
	}
}
