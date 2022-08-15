package dev.vital.quester.ui;

import dev.vital.quester.QuestList;
import dev.vital.quester.VitalQuesterConfig;
import net.miginfocom.swing.MigLayout;
import net.runelite.client.config.ConfigManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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

		JPanel main_panel = new JPanel(new MigLayout());
		main_panel.setBorder(new LineBorder(Color.DARK_GRAY));
		//main_panel.setMinimumSize(new Dimension(210, 250));
		//main_panel.setMaximumSize(new Dimension(210, ));
		main_panel.add(createCheckBox("Automatic optimal questing", "automaticOptimal"), "wrap");
		main_panel.add(createComboBoxSection("Task", "currentQuest", QuestList.class), "wrap");

		switch(config.currentQuest()) {
			case SHEEP_SHEARER: {
				JPanel panel = new JPanel(new MigLayout());
				panel.setPreferredSize(new Dimension(300,100));
				panel.setBorder(new TitledBorder("Sheep Shearer configuration"));
				panel.add(createCheckBox("Bank Inventory", "sheepShearerBankInventory"), "wrap");
				if(config.sheepShearerBankInventory()) {
					var text_section = createTextSection("Items", "sheepShearerExcludedItems", false);
					text_section.setToolTipText("Prevent items from being banked. separated by ,");
					panel.add(text_section, "wrap");
				}
				main_panel.add(panel, "wrap");

				break;
			}
		}

		add(main_panel, "wrap");

		main_panel.add(createButton("Start task", "Stop task", "startStopPlugin"), "wrap");

		revalidate();
	}
}
