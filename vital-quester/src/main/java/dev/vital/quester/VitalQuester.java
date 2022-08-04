package dev.vital.quester;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.quester.quests.pirates_treasure.PiratesTreasure;
import dev.vital.quester.quests.pirates_treasure.tasks.GetJob;
import dev.vital.quester.quests.restless_ghost.RestlessGhost;
import dev.vital.quester.quests.sheep_shearer.SheepShearer;
import dev.vital.quester.quests.x_marks_the_spot.XMarksTheSpot;
import dev.vital.quester.tasks.HandleQuestComplete;
import dev.vital.quester.tools.Tools;
import net.runelite.api.events.ConfigButtonClicked;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import dev.vital.quester.quests.cooks_assistant.CooksAssistant;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(name = "vital-quester", enabledByDefault = false)
@Extension
public class VitalQuester extends LoopedPlugin
{
	@Inject
	public VitalQuesterConfig config;

	static boolean plugin_enabled = false;
	static List<ScriptTask> tasks = new ArrayList<>();

	static boolean thread_once = false;
	Thread t1 = new Thread(() -> {
		if(Game.isLoggedIn()) {
			Tools.isAnimating(1);
		}
		Time.sleepTick();
	});

	@Override
	protected void startUp()
	{
		if(!thread_once) {
			t1.start();
			thread_once = true;
		}
		plugin_enabled = false;

		tasks.clear();

		tasks.add(new HandleQuestComplete(config));
		tasks.add(new CooksAssistant(config));
		tasks.add(new RestlessGhost(config));
		tasks.add(new SheepShearer(config));
		tasks.add(new XMarksTheSpot(config));
		tasks.add(new PiratesTreasure(config));
	}


	@Override
	protected int loop()
	{
		if(Game.isLoggedIn())
		{
			if(Dialog.canContinue()) {
				Dialog.continueSpace();
				return -1;
			}

			if(Movement.isWalking()) {
				return -1;
			}

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

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked e) {

		if (!e.getGroup().equals("vitalquesterconfig")) {
			return;
		}

		switch (e.getKey()) {
			case "startStopPlugin":
				plugin_enabled = !plugin_enabled;
				break;
		}
	}
	@Provides
	VitalQuesterConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalQuesterConfig.class);
	}
}
