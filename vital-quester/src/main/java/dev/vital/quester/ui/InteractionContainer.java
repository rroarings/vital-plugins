package dev.vital.quester.ui;

import dev.vital.quester.QuestList;
import dev.vital.quester.VitalQuesterConfig;
import net.runelite.client.config.ConfigManager;

public class InteractionContainer extends PanelContainer
{
	public InteractionContainer(VitalQuesterConfig config, ConfigManager configManager)
	{
		super("Questing", config, configManager);
	}

	@Override
	protected void rebuild()
	{
		removeAll();

		add(createCheckBox("Automatic optimal questing", "automaticOptimal"), "wrap");
		add(createComboBoxSection("Task", "currentQuest", QuestList.class), "wrap");

		add(createButton("Start task", "Stop task", "startStopPlugin"), "wrap");

		revalidate();
	}
}
