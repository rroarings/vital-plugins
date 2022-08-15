package dev.vital.quester;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.vital.quester.quests.cooks_assistant.CooksAssistant;
import dev.vital.quester.quests.pirates_treasure.PiratesTreasure;
import dev.vital.quester.quests.restless_ghost.RestlessGhost;
import dev.vital.quester.quests.romeo_and_juliet.RomeoAndJuliet;
import dev.vital.quester.quests.rune_mysteries.RuneMysteries;
import dev.vital.quester.quests.sheep_shearer.SheepShearer;
import dev.vital.quester.quests.tutorial_island.TutorialIsland;
import dev.vital.quester.quests.x_marks_the_spot.XMarksTheSpot;
import dev.vital.quester.tasks.*;
import dev.vital.quester.tools.Tools;
import dev.vital.quester.ui.VitalPanel;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import org.pf4j.Extension;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(name = "vital-quester", enabledByDefault = false)
@Extension
public class VitalQuester extends LoopedPlugin
{
    public static String version = "0.2.4";

    @Inject
	public VitalQuesterConfig config;

	@Inject
	private EventBus eventBus;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ConfigManager configManager;

	private VitalPanel vitalPanel;
	private NavigationButton navButton;

	static boolean plugin_enabled = false;
	static List<ScriptTask> tasks = new ArrayList<>();

	public VitalQuester() {
	}

	@Override
	protected void startUp()
	{
		configManager.setConfiguration(VitalQuesterConfig.CONFIG_GROUP, "startStopPlugin", false);

		plugin_enabled = false;

		tasks.clear();

		tasks.add(new HandleQuestComplete(config));
		tasks.add(new HandleDeath(config));
		tasks.add(new HandleGrave(config));
		tasks.add(new HandleGenie(config));
		tasks.add(new HandleLamp(config));

		tasks.add(new TutorialIsland(config));

		tasks.add(new CooksAssistant(config));
		tasks.add(new SheepShearer(config));
		tasks.add(new RestlessGhost(config));
		tasks.add(new RuneMysteries(config));
		tasks.add(new XMarksTheSpot(config));
		tasks.add(new PiratesTreasure(config));
		tasks.add(new RomeoAndJuliet(config));

		vitalPanel = new VitalPanel(config, configManager);

		eventBus.register(vitalPanel);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "1583.png");

		navButton = NavigationButton.builder()
				.tooltip("Vital Quester")
				.icon(icon)
				.priority(10)
				.panel(vitalPanel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected int loop()
	{
		var logged_in = Game.isLoggedIn();
		if(!logged_in && plugin_enabled) {
			plugin_enabled = false;
		}
		if(logged_in && config.startStopPlugin())
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

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
		eventBus.unregister(vitalPanel);
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

	@Provides
	VitalQuesterConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalQuesterConfig.class);
	}
}
