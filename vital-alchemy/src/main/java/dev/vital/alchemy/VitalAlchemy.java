package dev.vital.alchemy;

import com.google.inject.Provides;
import dev.unethicalite.api.plugins.Script;
import dev.vital.alchemy.tasks.HighAlch;
import dev.vital.alchemy.tasks.ScriptTask;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-alchemy", enabledByDefault = false)
@Extension
public class VitalAlchemy extends Script {

	private static final ScriptTask[] TASKS = new ScriptTask[] {

			//new GoToBank(),
			//new Withdraw(),
			new HighAlch()
	};

	@Override
	public void onStart(String... args) {

	}

	@Override
	protected int loop()
	{
		for (ScriptTask task : TASKS)
		{
			if (task.validate())
			{
				int sleep = task.execute();

				if (task.blocking())
				{
					return sleep;
				}
			}
		}

		return 1000;
	}

	@Provides
	public VitalAlchemyConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalAlchemyConfig.class);
	}
}
