package dev.vital.quester;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.quester.quests.enter_the_abyss.EnterTheAbyss;
import dev.vital.quester.quests.pirates_treasure.PiratesTreasure;
import dev.vital.quester.quests.restless_ghost.RestlessGhost;
import dev.vital.quester.quests.rune_mysteries.RuneMysteries;
import dev.vital.quester.quests.sheep_shearer.SheepShearer;
import dev.vital.quester.quests.tutorial_island.TutorialIsland;
import dev.vital.quester.quests.x_marks_the_spot.XMarksTheSpot;
import dev.vital.quester.tasks.HandleQuestComplete;
import dev.vital.quester.tools.Tools;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.events.ConfigChanged;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.LoopedPlugin;
import dev.vital.quester.quests.cooks_assistant.CooksAssistant;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.widgets.Dialog;
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

	@Override
	protected void startUp()
	{
		plugin_enabled = false;

		tasks.clear();

		tasks.add(new TutorialIsland(config));
		tasks.add(new HandleQuestComplete(config));
		tasks.add(new CooksAssistant(config));
		tasks.add(new SheepShearer(config));
		tasks.add(new RestlessGhost(config));
		tasks.add(new RuneMysteries(config));
		tasks.add(new XMarksTheSpot(config));
		tasks.add(new PiratesTreasure(config));
		tasks.add(new EnterTheAbyss(config));
	}

	@Override
	protected int loop()
	{
		if(Game.isLoggedIn() && plugin_enabled)
		{
			if(Dialog.canContinue()) {
				Dialog.continueSpace();
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
	private void onGameTick(GameTick event) {
		if(Game.isLoggedIn()) {
			Tools.isAnimating(1);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e)
	{
		if (!e.getGroup().equals("vitalquesterconfig") && !e.getKey().equals("currentQuest")) {
			return;
		}

		plugin_enabled = false;
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
