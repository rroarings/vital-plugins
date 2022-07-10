package net.unethicalite.thieving;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import net.runelite.api.Actor;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.unethicalite.api.Interactable;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.events.MenuAutomated;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.client.Static;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-thieving", enabledByDefault = false)
@Extension
@Slf4j
public class VitalThieving extends LoopedPlugin
{
	@Inject
	private VitalThievingConfig config;

	void getItem(int item, int amount) {

		if (Bank.isOpen()) {

			if(Inventory.getFreeSlots() < amount) {

				Bank.depositInventory();
			}
			else {

				Bank.withdraw(item, amount, Bank.WithdrawMode.ITEM);
			}
		}
		else {

			TileObjects.getNearest("Bank booth").interact("Bank");
		}
	}

	@Override
	protected int loop() {

		if (!Game.isLoggedIn()) {

			return -1;
		}

		var local_player = LocalPlayer.get();
		if (local_player.isAnimating() || local_player.getGraphic() == 245) {

			return -1;
		}
		//var knighta = ((Actor)NPCs.getNearest("Man")).getClickPoint();
//var r = knighta.getClickPoint();
		//Static.getClient().interact(2133,10,0,0,
		//		r.getX(), r.getY());

		int current_hp = Static.getClient().getBoostedSkillLevel(Skill.HITPOINTS);
		int max_hp = Static.getClient().getRealSkillLevel(Skill.HITPOINTS);
		if(current_hp < (max_hp / 2)) {
			Inventory.getFirst(ItemID.JUG_OF_WINE).interact("Drink");
		}
		else if(Inventory.getCount(true, ItemID.COIN_POUCH_22531) == 28) {

			Inventory.getFirst(ItemID.COIN_POUCH_22531).interact("Open-all");
		}
		else if(!Inventory.contains(config.foodID())) {

			getItem(config.foodID(), 23);
		}
		else if(!Inventory.contains(ItemID.DODGY_NECKLACE)) {

			getItem(ItemID.DODGY_NECKLACE, 3);
		}
		else if(Bank.isOpen()) {

			Bank.close();
		}
		else if(!Equipment.contains(ItemID.DODGY_NECKLACE)) {

			Inventory.getFirst(ItemID.DODGY_NECKLACE).interact("Wear");
		}
		else {



			return Rand.nextInt(config.minDelay(), config.maxDelay());
		}

		return -2;
	}

	@Provides
	VitalThievingConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalThievingConfig.class);
	}
}
