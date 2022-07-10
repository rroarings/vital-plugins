package dev.vital.prayer.tasks;

import dev.vital.prayer.VitalPrayerConfig;
import net.runelite.api.ItemID;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.widgets.Dialog;

public class SacraficeBones implements ScriptTask
{
	WorldPoint pray_location = new WorldPoint(2948, 3820, 0);
	WorldArea inside_widly_chaos_alter = new WorldArea(2948, 3819, 10, 4	, 0);
	int is_animating = 0;

	VitalPrayerConfig config;

	public SacraficeBones(VitalPrayerConfig config) {
		this.config = config;
	}

	@Override
	public boolean validate() { return Worlds.inMembersWorld() && Inventory.contains(ItemID.DRAGON_BONES); }

	@Override
	public int execute()
	{
		Player local = LocalPlayer.get();

		if (local.isAnimating() || Movement.isWalking()) {

			return -1;
		}

		if(inside_widly_chaos_alter.contains(local))
		{
			if (is_animating > 3)
			{
				Inventory.getFirst(ItemID.DRAGON_BONES).useOn(TileObjects.getFirstAt(pray_location, 411));
			}
		}
		else {

			Movement.walkTo(pray_location);
		}

		return -1;
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		if(LocalPlayer.get().getAnimation() == 3705) {
			is_animating = 0;
		}
		else {
			is_animating++;
		}
	}
}
