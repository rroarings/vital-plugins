package dev.vital.quester.ui;

import dev.vital.quester.VitalQuesterConfig;
import net.miginfocom.swing.MigLayout;
import net.runelite.client.config.ConfigManager;
import net.unethicalite.api.game.Combat;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class CombatContainer extends PanelContainer
{
	public CombatContainer(VitalQuesterConfig config, ConfigManager configManager)
	{
		super("Combat", config, configManager);
	}

	@Override
	protected void rebuild()
	{
		removeAll();

		JPanel main_panel = new JPanel(new MigLayout());
		main_panel.setBorder(new LineBorder(Color.DARK_GRAY));

		main_panel.add(createComboBoxSection("Style", "preferedStyle", Combat.AttackStyle.class), "wrap");

		add(main_panel, "wrap");

		revalidate();
	}
}
