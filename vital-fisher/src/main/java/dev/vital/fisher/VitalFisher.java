package dev.vital.fisher;

import dev.unethicalite.api.plugins.Script;
import dev.vital.fisher.tasks.GearUp;
import dev.vital.fisher.tasks.GoFish;
import dev.vital.fisher.tasks.ScriptTask;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

// This annotation is required in order for the client to detect it as a plugin/script.
@PluginDescriptor(name = "vital-fisher", enabledByDefault = false)
@Extension
public class VitalFisher extends Script
{
	private static final ScriptTask[] TASKS = new ScriptTask[]{
			new GearUp(),
			new GoFish()
	};

	@Override
	public void onStart(String... args)
	{
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
}
