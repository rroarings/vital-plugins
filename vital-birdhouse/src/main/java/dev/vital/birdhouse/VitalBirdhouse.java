package dev.vital.birdhouse;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.birdhouse.tasks.FossilIsland;
import dev.vital.birdhouse.tasks.GetMats;
import dev.vital.birdhouse.tasks.Meadow;
import dev.vital.birdhouse.tasks.Meadow2;
import dev.vital.birdhouse.tasks.ScriptTask;
import dev.vital.birdhouse.tasks.Valley;
import dev.vital.birdhouse.tasks.Valley2;
import net.runelite.api.ItemID;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.plugins.LoopedPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PluginDescriptor(name = "vital-birdhouse", enabledByDefault = false)
@Extension
@Slf4j
public class VitalBirdhouse extends LoopedPlugin {

	@Inject
	private VitalBirdhouseConfig config;

	List<ScriptTask> tasks = new ArrayList<>();

	public static Steps step = Steps.GETS_MATS;

	@Override
	public void startUp() {

		GetMats.bank_items = Arrays.asList(
				new BItems(config.returnTeleport(), 1, false, Bank.WithdrawMode.DEFAULT),
				new BItems(ItemID.HAMMER, 1, false,Bank.WithdrawMode.DEFAULT),
				new BItems(ItemID.CHISEL, 1, false,Bank.WithdrawMode.DEFAULT),
				new BItems(config.seedID(), 40, true, Bank.WithdrawMode.DEFAULT),
				new BItems(config.logID(), 4, false,Bank.WithdrawMode.DEFAULT),
				new BItems(ItemID.DIGSITE_PENDANT_1, 1, false,Bank.WithdrawMode.DEFAULT)
		);

		step = Steps.GETS_MATS;

		tasks.add(new GetMats(config));
		tasks.add(new FossilIsland(config));
		tasks.add(new Valley(config));
		tasks.add(new Valley2(config));
		tasks.add(new Meadow(config));
		tasks.add(new Meadow2(config));
	}

	@Override
	protected int loop() {

		for (ScriptTask task : tasks){

			if (task.validate()) {

				int sleep = task.execute();
				if (task.blocking()) {

					return sleep;
				}
			}
		}

		return -1;
	}

	@Provides
	VitalBirdhouseConfig getConfig(ConfigManager configManager) {

		return configManager.getConfig(VitalBirdhouseConfig.class);
	}
}
