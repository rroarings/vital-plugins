package dev.vital.quester.ui;

import dev.vital.quester.VitalQuesterConfig;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.components.ComboBoxListRenderer;
import net.runelite.client.util.Text;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public abstract class PanelContainer extends JPanel
{
	@Getter
	private final String title;
	protected final VitalQuesterConfig config;
	protected final ConfigManager configManager;

	public PanelContainer(String title, VitalQuesterConfig config, ConfigManager configManager)
	{
		this.title = title;
		this.config = config;
		this.configManager = configManager;

		setLayout(new MigLayout());

		rebuild();
	}

	protected JButton createButton(String enable, String disable, String configKey)
	{
		var current = Boolean.parseBoolean(configManager.getConfiguration(VitalQuesterConfig.CONFIG_GROUP, configKey));

		JButton button = new JButton(current ? disable : enable);
		button.setSelected(configManager.getConfiguration(VitalQuesterConfig.CONFIG_GROUP, configKey, Boolean.class));
		button.setSize(200, 20);
		button.setMinimumSize(new Dimension(200,20));
		button.addActionListener(l -> configManager.setConfiguration(VitalQuesterConfig.CONFIG_GROUP, configKey, !Boolean.parseBoolean(configManager.getConfiguration(VitalQuesterConfig.CONFIG_GROUP, configKey))));

		return button;
	}

	protected JCheckBox createCheckBox(String text, String configKey)
	{
		JCheckBox checkBox = new JCheckBox(text);
		checkBox.setSelected(configManager.getConfiguration(VitalQuesterConfig.CONFIG_GROUP, configKey, Boolean.class));
		checkBox.addChangeListener(l -> configManager.setConfiguration(VitalQuesterConfig.CONFIG_GROUP, configKey,
				((JCheckBox) l.getSource()).isSelected()));
		return checkBox;
	}

	protected JPanel createComboBoxSection(String text, String key, Class<? extends Enum> type)
	{
		JPanel section = new JPanel();
		section.add(new JLabel(text));
		JComboBox<Enum<?>> box = new JComboBox<>(type.getEnumConstants());
		box.setRenderer(new ComboBoxListRenderer<>());
		box.setForeground(Color.WHITE);
		box.setFocusable(false);

		try
		{
			Enum<?> selectedItem = Enum.valueOf(type, configManager.getConfiguration(VitalQuesterConfig.CONFIG_GROUP,
					key));
			box.setSelectedItem(selectedItem);
			box.setToolTipText(Text.titleCase(selectedItem));
		}
		catch (IllegalArgumentException ignored)
		{
		}
		box.addItemListener(e ->
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				configManager.setConfiguration(VitalQuesterConfig.CONFIG_GROUP, key, e.getItem());
				box.setToolTipText(Text.titleCase((Enum<?>) box.getSelectedItem()));
			}
		});

		section.add(box);

		return section;
	}

	protected JPanel createTextSection(String text, String key, boolean secret)
	{
		JPanel section = new JPanel();
		section.add(new JLabel(text));

		JTextComponent textField;

		if (secret)
		{
			textField = new JPasswordField();
		}
		else
		{
			final JTextArea textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textField = textArea;
		}

		textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textField.setText(configManager.getConfiguration(VitalQuesterConfig.CONFIG_GROUP, key));

		textField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				configManager.setConfiguration(VitalQuesterConfig.CONFIG_GROUP, key, textField.getText());
			}
		});

		section.add(textField);

		return section;
	}

	protected JPanel createIntSection(String text, String key, int value, int min, int max)
	{
		JPanel section = new JPanel();
		section.add(new JLabel(text));

		SpinnerModel model = new SpinnerNumberModel(value, min, max, 1);
		JSpinner spinner = new JSpinner(model);
		Component editor = spinner.getEditor();
		JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) editor).getTextField();
		spinnerTextField.setColumns(6);
		spinner.addChangeListener(ce -> configManager.setConfiguration(VitalQuesterConfig.CONFIG_GROUP, key, value));

		section.add(spinner);

		return section;
	}

	protected abstract void rebuild();
}
