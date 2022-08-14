package dev.vital.quester.ui;

import dev.vital.quester.QuestList;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.client.config.ConfigManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuestContainer extends PanelContainer
{
	public QuestContainer(VitalQuesterConfig config, ConfigManager configManager)
	{
		super("Quest", config, configManager);
	}

	@Override
	protected void rebuild()
	{
		removeAll();

		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		titlePanel.setLayout(new BorderLayout());

		add(createCheckBox("Automatic optimal questing", "automaticOptimal"), "wrap");
		if(!Boolean.parseBoolean(configManager.getConfiguration(VitalQuesterConfig.CONFIG_GROUP, "automaticOptimal"))) {
			add(createComboBoxSection("Task", "currentQuest", QuestList.class), "wrap");
		}

		add(createButton("Start task", "Stop task", "startStopPlugin"), "wrap");
		add(titlePanel);

		revalidate();
	}
}
