package net.unethicalite.tools;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.DialogOption;
import net.runelite.api.events.DialogProcessed;
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
import net.unethicalite.api.events.MenuAutomated;
import net.unethicalite.api.events.PacketSent;
import net.unethicalite.api.events.ServerPacketProcessed;
import net.unethicalite.api.events.ServerPacketReceived;
import net.unethicalite.client.Static;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(
		name = "Unethical Dev Tools",
		description = "Shows entity information",
		enabledByDefault = false
)
@Slf4j
public class VitalTools extends Plugin
{

}
