package dev.vital.quester;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.quester.quests.RestlessGhost;
import dev.vital.quester.tasks.HandleQuestComplete;
import net.runelite.api.events.ConfigButtonClicked;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;
import dev.vital.quester.quests.cooks_assistant.CooksAssistant;
import dev.vital.quester.quests.PiratesTreasure;
import dev.vital.quester.quests.SheepShearer;
import dev.vital.quester.quests.XMarks;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;
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
	static boolean plugin_enabled = false;
	@Inject
	public VitalQuesterConfig config;

	static List<ScriptTask> tasks = new ArrayList<>();

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

	@Override
	protected void startUp()
	{
		plugin_enabled = false;
		tasks.clear();

		//tasks.add(new HandleQuestComplete(config));
		tasks.add(new CooksAssistant(config));
		//tasks.add(new XMarks(config));
		//tasks.add(new PiratesTreasure(config));
		//tasks.add(new SheepShearer(config));
		//tasks.add(new RestlessGhost(config));
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
