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

		add(createComboBoxSection("Task", "currentQuest", QuestList.class), "wrap");

		switch (config.currentQuest()) {
			case TUTORIAL_ISLAND:

				break;
			default: {
				add(createCheckBox("Automatic Optimal Questing", "automaticOptimal"), "wrap");
				add(createCheckBox("Handle Genie", "handleGenie"), "wrap");
				add(createCheckBox("Handle Lamps", "handleLamp"), "wrap");
				break;
			}
		}

		revalidate();
	}
}
