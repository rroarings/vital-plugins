package net.unethicalite.thieving;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldArea;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.thieving.tasks.ScriptTask;
import net.unethicalite.thieving.tasks.npcs.ArdougneKnight;
import net.unethicalite.thieving.tasks.npcs.Man;
import net.unethicalite.thieving.tasks.stalls.FruitStall;
import net.unethicalite.thieving.tasks.stalls.TeaStall;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(name = "vital-thieving", enabledByDefault = false)
@Extension
@Slf4j
public class VitalThieving extends LoopedPlugin
{
	private static final WorldArea FRUIT_STALLS = new WorldArea(1796, 3606, 5, 5, 0);

	@Inject
	private VitalThievingConfig config;

	List<ScriptTask> tasks = new ArrayList<>();

	@Override
	public void startUp()
	{
		tasks.add(new FruitStall(config));
		tasks.add(new TeaStall(config));
		tasks.add(new ArdougneKnight(config));
		tasks.add(new Man(config));
	}

	@Override
	protected int loop()
	{
		if(Game.isLoggedIn() && config.thievingLevel() > Skills.getLevel(Skill.THIEVING))
		{
			for (ScriptTask task : tasks)
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
		}
		return -1;
	}

	@Provides
	VitalThievingConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalThievingConfig.class);
	}
}
