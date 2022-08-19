package dev.vital.sandcrab;

import net.runelite.api.coords.WorldPoint;

public class Spot
{

	public WorldPoint spot;
	public WorldPoint reset_spot;

	Spot(WorldPoint spot, WorldPoint reset_spot)
	{

		this.spot = spot;
		this.reset_spot = reset_spot;
	}
}
