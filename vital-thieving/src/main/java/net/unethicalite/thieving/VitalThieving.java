package net.unethicalite.thieving;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import net.runelite.api.Actor;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.api.World;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.worldhopper.WorldHopperPlugin;
import net.unethicalite.api.Interactable;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Predicates;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.events.MenuAutomated;
import net.unethicalite.api.events.WorldHopped;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
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
	private static final WorldArea FRUIT_STALLS = new WorldArea(1796, 3606, 5, 5, 0);

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

		if(Skills.getLevel(Skill.THIEVING) >= config.thievingLevel()) {

			Game.logout();
		}

		if(config.thievingType().equals(ThievingType.STALL_TEA)) {

			if(Inventory.isFull() && config.dropItems()) {

				while(Inventory.contains(ItemID.CUP_OF_TEA_1978)) {

					Inventory.getFirst(ItemID.CUP_OF_TEA_1978).interact("Drop");

					Time.sleep(400);
				}
			}
			else {
				var tea_stall = TileObjects.getFirstAt(new WorldPoint(3269, 3410, 0), x -> x.hasAction("Steal-from"));
				if(tea_stall != null && Reachable.isInteractable(tea_stall)) {

					tea_stall.interact("Steal-from");
					return -2;
				}
				else {

					Movement.walkTo(new WorldPoint(3269, 3410, 0));
				}
			}

			return -1;
		}
		else if(config.thievingType().equals(ThievingType.STALL_FRUIT)) {

			if(Inventory.isFull() && config.dropItems())
			{
				for (var item : Inventory.getAll(ItemID.COOKING_APPLE, ItemID.STRANGE_FRUIT,
						ItemID.BANANA, ItemID.LEMON,
						ItemID.LIME, ItemID.GOLOVANOVA_FRUIT_TOP, ItemID.JANGERBERRIES, ItemID.PINEAPPLE,
						ItemID.REDBERRIES, ItemID.STRAWBERRY, ItemID.PAPAYA_FRUIT)) {

					item.interact("Drop");
					Time.sleep(180, 230);
				}
			}
			else {
				var fruit_Stall = TileObjects.getNearest("Fruit Stall");
				if(fruit_Stall != null && Reachable.isInteractable(fruit_Stall) && fruit_Stall.distanceTo(LocalPlayer.get()) < 5) {

					if(Players.getNearest(x -> x != LocalPlayer.get() && FRUIT_STALLS.contains(x)) != null) {

						Worlds.hopTo(Worlds.getRandom(World::isMembers));
						return -12;
					}

					fruit_Stall.interact("Steal-from");

					Time.sleep(1500);
				}
				else {

					Movement.walkTo(FRUIT_STALLS);
				}
			}
		}
		else if(config.thievingType().equals(ThievingType.ARDOUGNE_KNIGHT)) {

			var local_player = LocalPlayer.get();
			if (local_player.isAnimating() || local_player.getGraphic() == 245) {

				return -1;
			}

			int current_hp = Static.getClient().getBoostedSkillLevel(Skill.HITPOINTS);
			int max_hp = Static.getClient().getRealSkillLevel(Skill.HITPOINTS);
			if(current_hp < (max_hp / 2)) {
				Inventory.getFirst(ItemID.JUG_OF_WINE).interact("Drink");
			}
			else if(Inventory.getCount(true, ItemID.COIN_POUCH_22531) == 28) {

				Inventory.getFirst(ItemID.COIN_POUCH_22531).interact("Open-all");
			}
			else if(!Inventory.contains(ItemID.JUG_OF_WINE)) {

				getItem(ItemID.JUG_OF_WINE, 23);
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

			return -2;
		}

		return 10;
	}

	@Provides
	VitalThievingConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalThievingConfig.class);
	}
}
