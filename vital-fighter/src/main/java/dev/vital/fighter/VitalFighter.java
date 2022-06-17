package dev.vital.fighter;

import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Script;
import dev.vital.fighter.tasks.ScriptTask;
import dev.vital.fighter.tasks.UpgradeHelmet;
import dev.vital.fighter.tasks.UpgradeWeapon;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-fighter", enabledByDefault = false)
@Extension
public class VitalFighter extends Script {

	private static final ScriptTask[] TASKS = new ScriptTask[] {
			new UpgradeWeapon(),
			new UpgradeHelmet()
	};

	@Override
	public void onStart(String... args) {

		int waterRune = Inventory.query().names("Water rune").stackable(true).results().size();
	}

	@Override
	protected int loop() {

		for (ScriptTask task : TASKS) {

			if (task.validate()) {

				int sleep = task.execute();

				if (task.blocking()) {

					return sleep;
				}
			}
		}

		return 1000;
	}
}
