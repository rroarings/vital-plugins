package dev.vital.cat;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PluginDescriptor(name = "vital-cat", enabledByDefault = false)
@Extension
@Slf4j
public class VitalCat extends LoopedPlugin
{
	@Inject
	private VitalCatConfig config;

	boolean pet = false;
	boolean feed = false;
	int current_fish = 0;

	public static List<Widget> getOptions()
	{
		Widget widget = Widgets.get(WidgetID.DIALOG_OPTION_GROUP_ID, 1);
		if (!Widgets.isVisible(widget))
		{
			return Collections.emptyList();
		}

		List<Widget> out = new ArrayList<>();
		Widget[] children = widget.getChildren();
		if (children == null)
		{
			return out;
		}

		for (int i = 0; i < children.length; i++)
		{
			if (children[i].getText().isBlank())
			{
				continue;
			}

			out.add(children[i]);
		}

		return out;
	}

	@Override
	protected int loop()
	{
		if (!Game.isLoggedIn() || LocalPlayer.get().isMoving() || LocalPlayer.get().isAnimating())
		{
			return -1;
		}

		for(var kitten : NPCs.getAll("Kitten")) {

			if(kitten.getInteracting() == LocalPlayer.get()) {

				if(feed) {

					if(Inventory.getCount(false, config.foodID()) < current_fish) {

						feed = false;
					}
					else {

						Inventory.getFirst(config.foodID()).useOn(kitten);
					}

					return Rand.nextInt(1200, 2200);
				}
				else if(pet) {

					var options = getOptions();
					if(Dialog.isViewingOptions() && Dialog.getOptions().get(0).getText().equals("Stroke")) {
						Dialog.chooseOption("Stroke");
					}
					else if(Dialog.canContinue() && options.get(0).getText().equals(LocalPlayer.getDisplayName())) {
						Keyboard.sendSpace();
						pet = false;
					}
					else if(!Dialog.isOpen()) {
						kitten.interact("Interact");
					}
				}
			}
		}

		return Rand.nextInt(1000, 1200);
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		final String message = event.getMessage();
		if(message.contains("<col=ef1020>Your kitten is hungry.</col>")) {

			current_fish = Inventory.getCount(false, config.foodID());
			feed = true;
		}
		else if(message.contains("<col=ef1020>Your kitten really wants attention.</col>")) {

			pet = true;
		}
	}

	@Provides
	VitalCatConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalCatConfig.class);
	}
}
