package dev.vital.quester.ui;

import dev.vital.quester.VitalQuesterConfig;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VitalPanel extends PluginPanel
{
	private final List<PanelContainer> containers = new ArrayList<>();
	private final JTabbedPane tabbedPane = new JTabbedPane();

	public VitalPanel(VitalQuesterConfig config, ConfigManager configManager)
	{
		setLayout(new MigLayout());

		QuestContainer questContainer = new QuestContainer(config, configManager);
		CombatContainer combatContainer = new CombatContainer(config, configManager);
		MiscellaneousContainer miscellaneousContainer = new MiscellaneousContainer(config, configManager);
		AboutContainer aboutContainer = new AboutContainer(config, configManager);

		containers.add(questContainer);
		containers.add(combatContainer);
		containers.add(miscellaneousContainer);
		containers.add(aboutContainer);

		add(tabbedPane);

		tabbedPane.addTab(questContainer.getTitle(), questContainer);
		tabbedPane.addTab(combatContainer.getTitle(), combatContainer);
		tabbedPane.addTab(miscellaneousContainer.getTitle(), miscellaneousContainer);
		tabbedPane.addTab(aboutContainer.getTitle(), aboutContainer);
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged e)
	{
		if (!e.getGroup().equals(VitalQuesterConfig.CONFIG_GROUP))
		{
			return;
		}

		SwingUtilities.invokeLater(() -> containers.forEach(PanelContainer::rebuild));
	}
}
