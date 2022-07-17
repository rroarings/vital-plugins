package net.unethicalite.tools;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.DialogOption;
import net.runelite.api.events.DialogProcessed;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.packets.ClientPacket;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.ServerPacket;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.unethicalite.api.events.LoginStateChanged;
import net.unethicalite.api.events.MenuAutomated;
import net.unethicalite.api.events.PacketSent;
import net.unethicalite.api.events.ServerPacketProcessed;
import net.unethicalite.api.events.ServerPacketReceived;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.client.Static;
import org.pf4j.Extension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(name = "vital-tools", enabledByDefault = false)
@Extension
@Slf4j
public class VitalTools extends Plugin {

	@Inject
	private Client client;

	@Inject
	private VitalToolsConfig config;

	boolean has_logged_in = false;

	@Subscribe
	private void onGameTick(GameTick gameTick)
	{
		if(!has_logged_in && Game.isLoggedIn()) {
			has_logged_in = true;
		}
	}
	@Subscribe
	private void onLoginStateChanged(LoginStateChanged e)
	{
		switch (e.getIndex())
		{
			case 2:
			{
				if(config.autoRelog() && has_logged_in)
				{
					client.setUsername(config.username());
					client.setPassword(config.password());

					Mouse.click(299, 322, true);
				}

				break;
			}
		}
	}
}
