package dev.vital.bankstander;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.GameAccount;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Predicates;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Dialog;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PluginDescriptor(name = "vital-bank-stander", enabledByDefault = false)
@Extension
@Slf4j
public class VitalBankStander extends LoopedPlugin
{
	@Inject
	private VitalBankStanderConfig config;
	private List<Integer> itemIds = new ArrayList<Integer>();
	public int tick_delay = 0;
	public int tick_count = 0;
	public boolean should_bank = false;
	public boolean tick_delay_begin = false;
public boolean animatingg = false;
	public int eventt = -1;

	public List<Integer> stringToIntList(String string) {
		return (string == null || string.trim().equals("")) ? List.of(0) :
				Arrays.stream(string.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
	}

	@Override
	protected int loop()
	{
		if(Movement.isWalking() || LocalPlayer.get().isAnimating() || !Game.isLoggedIn() || !WorldLocation.GRAND_EXCHANGE.getWorldArea().contains(LocalPlayer.get())) {

			return -1;
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

		if(config.needleAndThread()) {

			if(eventt == -1)
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
			if(eventt == 0) {

				Inventory.getFirst(ItemID.NEEDLE).useOn(Inventory.getFirst(config.firstMaterial()));
				eventt = 1;
				return Rand.nextInt(1000, 1400);
			}
			else if(eventt == 1)
			{
				Keyboard.sendSpace();
				tick_delay_begin= true;
				eventt = -1;
				return Rand.nextInt(2500, 3000);
			}
		}
		if(config.bowAndString()) {

			if(eventt == -1)
			{
				if (!Inventory.contains(ItemID.BOW_STRING) || Inventory.getCount(ItemID.BOW_STRING) != 14)
				{
					if (Bank.isOpen())
					{
						if (Inventory.isEmpty())
						{
							Bank.withdraw(Predicates.ids(ItemID.BOW_STRING), 14, Bank.WithdrawMode.ITEM);
						}
						else {

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
						else if(Bank.getCount(true, config.unfinishedBowID()) >= 14)
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
			if(eventt == 0) {

				Inventory.getFirst(ItemID.BOW_STRING).useOn(Inventory.getFirst(config.unfinishedBowID()));
				eventt = 1;
				return Rand.nextInt(1000, 1400);
			}
			else if(eventt == 1)
			{
				Keyboard.sendSpace();
				tick_delay_begin= true;
				eventt = -1;
				return Rand.nextInt(2500, 3000);
			}
		}
		else if(config.jugs()) {

			if(eventt == -1)
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
						else {

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
						else if(Bank.getCount(true, config.secondaryMat()) >= 14)
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
			if(eventt == 0) {

				Inventory.getFirst(ItemID.JUG_OF_WATER).useOn(Inventory.getFirst(config.secondaryMat()));
				eventt = 1;
				return Rand.nextInt(300, 600);
			}
			else if(eventt == 1 && Dialog.isOpen())
			{
				Keyboard.sendSpace();
				tick_delay_begin= true;
				eventt = -1;
				return Rand.nextInt(2500, 3000);
			}
		}
		//else if(config.potions)
		return Rand.nextInt(800, 1200);
	}
	public int gg = 0;
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
	VitalBankStanderConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalBankStanderConfig.class);
	}
}
