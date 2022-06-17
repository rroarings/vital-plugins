package dev.vital.quester;

import com.google.inject.Inject;
import com.google.inject.Provides;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Widgets;
import dev.vital.quester.quests.CooksAssistant;
import dev.vital.quester.quests.PiratesTreasure;
import dev.vital.quester.quests.SheepShearer;
import dev.vital.quester.quests.XMarks;
import dev.vital.quester.tasks.ScriptTask;
import dev.vital.quester.tools.ItemList;
import net.runelite.api.ItemID;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.Arrays;

@PluginDescriptor(name = "vital-quester", enabledByDefault = false)
@Extension
public class VitalQuester extends LoopedPlugin
{
	@Inject
	public VitalQuesterConfig quester_config;

	private final ScriptTask[] TASKS = new ScriptTask[]{
			new CooksAssistant(),
			new XMarks(),
			new PiratesTreasure(),
			new SheepShearer(),
	};

	@Override
	protected int loop()
	{
		if(Game.isLoggedIn())
		{
			var widget = Widgets.get(WidgetInfo.QUEST_COMPLETED);
			if(widget != null & widget.isVisible()) {
				//widget.interact();
			}
			for (ScriptTask task : TASKS)
			{
				if (task.validate(quester_config))
				{
					int sleep = task.execute();
					if (task.blocking())
					{
						return sleep;
					}
				}
			}
		}
		return 1000;
	}

	@Override
	protected void startUp() throws Exception
	{
		super.startUp();
		CooksAssistant.item_list = Arrays.asList(
				new ItemList(ItemID.EGG, 50, 1, false, Bank.WithdrawMode.ITEM, false, ""),
				new ItemList(ItemID.BUCKET_OF_MILK, 200, 1, false, Bank.WithdrawMode.ITEM, false, ""),
				new ItemList(ItemID.POT_OF_FLOUR, 200, 1, false, Bank.WithdrawMode.ITEM, false, ""),
				new ItemList(ItemID.SPADE, 200, 1, false, Bank.WithdrawMode.ITEM, false, ""),
				new ItemList(ItemID.COINS_995, 0, 60, true, Bank.WithdrawMode.ITEM, false, ""),
				new ItemList(ItemID.IRON_SCIMITAR, 1000, 1, false, Bank.WithdrawMode.ITEM, true, "Wield"),
				new ItemList(ItemID.IRON_SQ_SHIELD, 1000, 1, false, Bank.WithdrawMode.ITEM, true, "Wield")
		);
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{

	}

	@Provides
	VitalQuesterConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalQuesterConfig.class);
	}
}
