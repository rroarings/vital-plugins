package dev.vital.bankstander;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Predicates;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(name = "vital-bank-stander", enabledByDefault = false)
@Extension
@Slf4j
public class VitalBankStander extends LoopedPlugin
{
	private final List<Integer> itemIds = new ArrayList<>();
	public int tick_delay = 0;
	public int tick_count = 0;
	public boolean should_bank = false;
	public boolean tick_delay_begin = false;
	public boolean animatingg = false;
	public int eventt = -1;
	public int gg = 0;
	@Inject
	private VitalBankStanderConfig config;

	public List<Integer> stringToIntList(String string)
	{
		return (string == null || string.trim().equals("")) ? List.of(0) :
				Arrays.stream(string.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
	}

	@Override
	protected int loop()
	{
		if (Movement.isWalking() || LocalPlayer.get().isAnimating() || !Game.isLoggedIn() || !WorldLocation.GRAND_EXCHANGE.getWorldArea().contains(LocalPlayer.get()))
		{

			return -1;
		}

		if (tick_delay_begin)
		{

			if (tick_count < tick_delay)
			{
				return 1;
			}
			else
			{

				var min = config.minimumDelay();
				var max = config.maximumDelay();

				if (min > 600)
				{
					min = 600;
				}
				if (max > 600)
				{
					max = 600;
				}

				tick_delay_begin = false;

				return Rand.nextInt(min, max);
			}
		}

		if (config.glass())
		{

			if (eventt == -1)
			{
				if (Inventory.getCount(ItemID.MOLTEN_GLASS) != 27)
				{
					if (Bank.isOpen())
					{
						if (Inventory.getFreeSlots() == 27)
						{
							Bank.withdraw(Predicates.ids(ItemID.MOLTEN_GLASS), 27, Bank.WithdrawMode.ITEM);
							return -1;
						}
						else
						{

							Bank.depositAllExcept(ItemID.GLASSBLOWING_PIPE);
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else if (!Inventory.contains(ItemID.GLASSBLOWING_PIPE))
				{
					if (Bank.isOpen())
					{
						if (Bank.contains(ItemID.GLASSBLOWING_PIPE))
						{
							Bank.withdraw(ItemID.GLASSBLOWING_PIPE, 1, Bank.WithdrawMode.ITEM);
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else
				{
					if (Bank.isOpen())
					{
						Bank.close();
					}
					else
					{
						eventt = 0;
					}
				}
				return Rand.nextInt(300, 600);
			}
			if (eventt == 0)
			{

				Inventory.getFirst(ItemID.GLASSBLOWING_PIPE).useOn(Inventory.getFirst(ItemID.MOLTEN_GLASS));
				eventt = 1;
				return Rand.nextInt(300, 600);
			}
			else if (eventt == 1 && Dialog.isOpen())
			{
				var level = Skills.getLevel(Skill.CRAFTING);
				if (level >= 1 && level < 4)
				{
					Keyboard.type(1);
				}
				else if (level >= 4 && level < 12)
				{
					Keyboard.type(2);
				}
				else if (level >= 12 && level < 33)
				{
					Keyboard.type(3);
				}
				else if (level >= 33 && level < 42)
				{
					Keyboard.type(4);
				}
				else if (level >= 42 && level < 46)
				{
					Keyboard.type(5);
				}
				else if (level >= 46 && level < 49)
				{
					Keyboard.type(6);
				}
				else if (level >= 49 && level < 87)
				{
					Keyboard.type(7);
				}
				else if (level >= 87)
				{
					Keyboard.type(8);
				}
				tick_delay_begin = true;
				eventt = -1;
				return Rand.nextInt(2500, 3000);
			}
		}
		else if (config.needleAndThread())
		{

			if (eventt == -1)
			{
				if (!Inventory.contains(ItemID.NEEDLE) || !Inventory.contains(ItemID.THREAD))
				{
					if (Bank.isOpen())
					{
						if (!Inventory.contains(ItemID.NEEDLE))
						{
							Bank.withdraw(Predicates.ids(ItemID.NEEDLE), 1, Bank.WithdrawMode.ITEM);
						}
						else if (!Inventory.contains(ItemID.THREAD))
						{
							Bank.withdraw(Predicates.ids(ItemID.THREAD), 9999, Bank.WithdrawMode.ITEM);
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else if (!Inventory.contains(config.firstMaterial()) || Inventory.getCount(config.firstMaterial()) != config.firstMaterialAmount())
				{
					if (Bank.isOpen())
					{
						if (Inventory.contains(config.firstMaterial()))
						{
							Bank.depositAll(config.firstMaterial());
						}
						else if (Inventory.contains(config.depositMaterial()))
						{

							Bank.depositAll(config.depositMaterial());
						}
						else
						{

							Bank.withdraw(config.firstMaterial(), config.firstMaterialAmount(), Bank.WithdrawMode.ITEM);
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else if (config.secondMaterial() > 0 && config.secondMaterialAmount() > 0 && !Inventory.contains(config.secondMaterial()) || Inventory.getCount(config.secondMaterial()) != config.secondMaterialAmount())
				{

					if (Bank.isOpen())
					{
						if (Inventory.contains(config.secondMaterial()))
						{
							Bank.depositAll(config.secondMaterial());
						}
						else
						{

							Bank.withdraw(config.secondMaterial(), config.secondMaterialAmount(), Bank.WithdrawMode.ITEM);
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else
				{

					if (Bank.isOpen())
					{

						Bank.close();
					}
					else
					{
						eventt = 0;
					}
				}
			}
			if (eventt == 0)
			{

				Inventory.getFirst(ItemID.NEEDLE).useOn(Inventory.getFirst(config.firstMaterial()));
				eventt = 1;
				return Rand.nextInt(1000, 1400);
			}
			else if (eventt == 1)
			{
				Keyboard.sendSpace();
				tick_delay_begin = true;
				eventt = -1;
				return Rand.nextInt(2500, 3000);
			}
		}
		if (config.bowAndString())
		{

			if (eventt == -1)
			{
				if (!Inventory.contains(ItemID.BOW_STRING) || Inventory.getCount(ItemID.BOW_STRING) != 14)
				{
					if (Bank.isOpen())
					{
						if (Inventory.isEmpty())
						{
							Bank.withdraw(Predicates.ids(ItemID.BOW_STRING), 14, Bank.WithdrawMode.ITEM);
						}
						else
						{

							Bank.depositInventory();
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else if (!Inventory.contains(config.unfinishedBowID()) || Inventory.getCount(config.unfinishedBowID()) != 14)
				{
					if (Bank.isOpen())
					{
						if (Inventory.contains(config.unfinishedBowID()))
						{
							Bank.depositAll(config.unfinishedBowID());
						}
						else if (Bank.getCount(true, config.unfinishedBowID()) >= 14)
						{
							Bank.withdraw(config.unfinishedBowID(), 14, Bank.WithdrawMode.ITEM);
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else
				{
					if (Bank.isOpen())
					{

						Bank.close();
					}
					else
					{
						eventt = 0;
					}
				}
			}
			if (eventt == 0)
			{

				Inventory.getFirst(ItemID.BOW_STRING).useOn(Inventory.getFirst(config.unfinishedBowID()));
				eventt = 1;
				return Rand.nextInt(1000, 1400);
			}
			else if (eventt == 1)
			{
				Keyboard.sendSpace();
				tick_delay_begin = true;
				eventt = -1;
				return Rand.nextInt(2500, 3000);
			}
		}
		else if (config.jugs())
		{

			if (eventt == -1)
			{
				if (!Inventory.contains(ItemID.JUG_OF_WATER) || Inventory.getCount(ItemID.JUG_OF_WATER) != 14)
				{
					if (Bank.isOpen())
					{
						if (Inventory.isEmpty())
						{
							Bank.withdraw(Predicates.ids(ItemID.JUG_OF_WATER), 14, Bank.WithdrawMode.ITEM);
							Time.sleepTicks(1);
						}
						else
						{

							Bank.depositInventory();
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else if (!Inventory.contains(config.secondaryMat()) || Inventory.getCount(config.secondaryMat()) != 14)
				{
					if (Bank.isOpen())
					{
						if (Inventory.contains(config.secondaryMat()))
						{
							Bank.depositAll(config.secondaryMat());
						}
						else if (Bank.getCount(true, config.secondaryMat()) >= 14)
						{
							Bank.withdraw(config.secondaryMat(), 14, Bank.WithdrawMode.ITEM);
						}
					}
					else
					{
						TileObjects.getNearest("Grand Exchange booth").interact("Bank");
					}
				}
				else
				{
					if (Bank.isOpen())
					{

						Bank.close();
					}
					else
					{
						eventt = 0;
					}
				}
				return Rand.nextInt(300, 600);
			}
			if (eventt == 0)
			{

				Inventory.getFirst(ItemID.JUG_OF_WATER).useOn(Inventory.getFirst(config.secondaryMat()));
				eventt = 1;
				return Rand.nextInt(300, 600);
			}
			else if (eventt == 1 && Dialog.isOpen())
			{
				Keyboard.sendSpace();
				tick_delay_begin = true;
				eventt = -1;
				return Rand.nextInt(2500, 3000);
			}
		}
		//else if(config.potions)
		return Rand.nextInt(800, 1200);
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		tick_count++;

		if (!tick_delay_begin)
		{
			tick_delay = tick_count + Rand.nextInt(config.tickMinDelay(), config.tickMaxDelay());
		}
	}

	@Provides
	VitalBankStanderConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalBankStanderConfig.class);
	}
}
