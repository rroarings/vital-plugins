package dev.vital.magic;

import com.google.inject.Inject;
import com.google.inject.Provides;
import dev.unethicalite.api.entities.TileObjects;
import dev.unethicalite.api.game.Game;
import dev.unethicalite.api.game.GameSettings;
import dev.unethicalite.api.input.Mouse;
import dev.unethicalite.api.items.Inventory;
import dev.unethicalite.api.magic.Magic;
import dev.unethicalite.api.magic.SpellBook;
import dev.unethicalite.api.movement.Movement;
import dev.unethicalite.api.plugins.LoopedPlugin;
import dev.unethicalite.api.widgets.Tab;
import dev.unethicalite.api.widgets.Widgets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.awt.*;

@PluginDescriptor(name = "vital-magic", enabledByDefault = false)
@Extension
@Slf4j
public class VitalMagic extends LoopedPlugin
{
	@Inject
	private VitalMagicConfig config;

	@Override
	protected int loop()
	{

		if(config.alchemyEnabled() && config.alchemyItem() != 0 && Inventory.contains(ItemID.NATURE_RUNE) && Inventory.contains(config.alchemyItem()))
		{
			if (Movement.isWalking())
			{

				return 1000;
			}

			if (SpellBook.Standard.HIGH_LEVEL_ALCHEMY.canCast())
			{
				Magic.cast(SpellBook.Standard.HIGH_LEVEL_ALCHEMY, Inventory.getFirst(config.alchemyItem()));
			}
			else
			{

				Widget high_alch = Widgets.get(SpellBook.Standard.HIGH_LEVEL_ALCHEMY.getWidget());
				Widget magic = Widgets.get(Tab.MAGIC.getWidgetInfo());
				if (high_alch != null && !high_alch.isHidden())
				{
					Rectangle rect = Widgets.get(SpellBook.Standard.HIGH_LEVEL_ALCHEMY.getWidget()).getBounds();

				}
				else {

					Mouse.click(magic.getClickPoint().getX(), magic.getClickPoint().getY(), true);
				}
			}
		}
		return 1000;
	}

	@Provides
	VitalMagicConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalMagicConfig.class);
	}
}
