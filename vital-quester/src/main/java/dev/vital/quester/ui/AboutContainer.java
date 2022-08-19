package dev.vital.quester.ui;

import dev.vital.quester.VitalQuester;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.util.LinkBrowser;

import javax.swing.JButton;
import java.awt.Dimension;

public class AboutContainer extends PanelContainer
{
	public AboutContainer(VitalQuesterConfig config, ConfigManager configManager)
	{
		super("About", config, configManager);
	}

	@Override
	protected void rebuild()
	{
		removeAll();

		JButton button = new JButton("Version " + VitalQuester.version);
		button.setSize(200, 20);
		button.setMinimumSize(new Dimension(200, 20));
		button.addActionListener(e -> LinkBrowser.browse("https://github.com/Vitalflea/vital-plugins/tree/development/vital-quester"));

		add(button);

		revalidate();
	}
}
