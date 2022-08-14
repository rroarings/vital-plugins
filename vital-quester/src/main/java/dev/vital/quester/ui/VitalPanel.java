package dev.vital.quester.ui;

import dev.vital.quester.VitalQuesterConfig;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;
import net.runelite.api.Client;
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

	public VitalPanel(Client client, VitalQuesterConfig config, ConfigManager configManager)
	{
		setLayout(new MigLayout());

		InteractionContainer interactionContainer = new InteractionContainer(config, configManager);
		MiscellaneousContainer miscellaneousContainer = new MiscellaneousContainer(config, configManager);

		containers.add(interactionContainer);
		containers.add(miscellaneousContainer);

		add(tabbedPane);

		tabbedPane.addTab(interactionContainer.getTitle(), interactionContainer);
		tabbedPane.addTab(miscellaneousContainer.getTitle(), miscellaneousContainer);
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
