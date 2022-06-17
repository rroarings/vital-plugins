package dev.vital.aerial;

import com.google.inject.Inject;
import com.google.inject.Provides;

import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.plugins.LoopedPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(name = "vital-aerial", enabledByDefault = false)
@Extension
@Slf4j
public class VitalAerial extends LoopedPlugin
{
	@Inject
	private VitalAerialConfig config;
	private List<Integer> itemIds = new ArrayList<Integer>();
	public int tick_delay = 0;
	public int tick_count = 0;
	public boolean should_bank = false;
	public boolean tick_delay_begin = false;
	public boolean animatingg = false;
	public int eventt = -1;

	public List<Integer> stringToIntList(String string) {
		return (string == null || string.trim().equals("")) ? List.of(0) :
				Arrays.stream(string.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
	}


	@Override
	protected int loop()
	{
		if(!Game.isLoggedIn()  || Movement.isWalking() || LocalPlayer.get().isAnimating()) {

			return Rand.nextInt(1000, 1200);
		}

		if(tick_delay_begin) {

			if(tick_count < tick_delay)
			{
				return 1;
			}
			else {

				var min = config.minimumDelay();
				var max = config.maximumDelay();

				if(min > 600) { min = 600; }
				if(max > 600) { max = 600; }

				tick_delay_begin = false;

				return Rand.nextInt(min, max);
			}
		}

		var gyr_falcon = NPCs.getNearest("Gyr Falcon");
		if(gyr_falcon != null && Reachable.isInteractable(gyr_falcon))  {

			gyr_falcon.interact("Retrieve");
		}
		else if(LocalPlayer.get().getPoseFrame() == 5160) {

			var target = NPCs.getNearest(config.npcID());
			if(gyr_falcon != null && Reachable.isInteractable(gyr_falcon))  {

				target.interact("Catch");
				tick_delay_begin = true;
			}
		}
		else if(Inventory.isFull()) {

			var id_list = stringToIntList(config.toString());

			for(var inv : Inventory.getAll()) {

				for(var id : id_list) {

					if(id == inv.getId()) {

						inv.drop();
						Time.sleep(Rand.nextInt(1200, 2200));
						break;
					}
				}
			}
		}

		return Rand.nextInt(800, 1200);
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		tick_count++;

		if(!tick_delay_begin)
		{
			tick_delay = tick_count + Rand.nextInt(config.tickMinDelay(), config.tickMaxDelay());
		}
	}

	@Provides
	VitalAerialConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalAerialConfig.class);
	}
}
