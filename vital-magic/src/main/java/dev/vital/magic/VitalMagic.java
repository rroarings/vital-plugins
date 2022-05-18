package dev.vital.magic;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.unethicalite.api.commons.Rand;
import dev.unethicalite.api.items.Bank;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.magic.Magic;
import dev.unethicalite.api.magic.SpellBook;
import dev.unethicalite.api.movement.Movement;
import dev.unethicalite.api.plugins.LoopedPlugin;
import dev.unethicalite.api.widgets.Dialog;
import dev.unethicalite.api.widgets.Widgets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-magic", enabledByDefault = false)
@Extension
@Slf4j
public class VitalMagic extends LoopedPlugin
{
	@Inject
	private VitalMagicConfig config;

	public int tick_delay = 0;
	public int tick_count = 0;

	public boolean tick_delay_begin = false;
	@Override
	protected int loop()
	{
		if(Bank.isOpen() || Dialog.isOpen()) {

			return 2000;
		}

		if(tick_delay_begin) {

			if(tick_count < tick_delay)
			{
				return 1;
			}
			else {

				var min = config.minimumDelay();
				var max = config.maximumDelay();

				if(min > 600) { min = 600; }
				if(max > 600) { max = 600; }

				tick_delay_begin = false;

				return Rand.nextInt(min, max);
			}
		}

		if(config.alchemyEnabled() && config.alchemyItem() != 0 && Inventory.contains(config.alchemyItem()) && SpellBook.Standard.HIGH_LEVEL_ALCHEMY.canCast())
		{
			if (Movement.isWalking() && !config.alchemyWhileMoving())
			{
				return 1;
			}

			Magic.cast(SpellBook.Standard.HIGH_LEVEL_ALCHEMY, Inventory.getFirst(config.alchemyItem()));

			tick_delay_begin = true;
		}

		return 1;
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		tick_count++;

		if(!tick_delay_begin)
		{
			tick_delay = tick_count + Rand.nextInt(config.tickMinDelay(), config.tickMaxDelay());
		}
	}

	@Provides
	VitalMagicConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalMagicConfig.class);
	}
}
