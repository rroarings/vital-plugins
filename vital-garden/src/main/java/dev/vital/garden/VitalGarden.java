package dev.vital.garden;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.openosrs.client.game.WorldLocation;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-garden", enabledByDefault = false)
@Extension
@Slf4j
public class VitalGarden extends LoopedPlugin
{
	@Inject
	private VitalGardenConfig config;

	private static final WorldArea APPRENTICE_AREA = new WorldArea(3318, 3137, 7, 5, 0);

	private static final WorldArea CENTER_GARDEN = new WorldArea(2905, 5463, 14, 14, 0);
	private static final WorldPoint AUTUMN_GATE = new WorldPoint(2913, 5462, 0);

	private static final WorldPoint SAFE_SPOT_1 = new WorldPoint(2908, 5461, 0);
	private static final WorldPoint SAFE_SPOT_2 = new WorldPoint(2904, 5459, 0);
	private static final WorldPoint SAFE_SPOT_3 = new WorldPoint(2901, 5455, 0);
	private static final WorldPoint SAFE_SPOT_4 = new WorldPoint(2901, 5451, 0);
	private static final WorldPoint SAFE_SPOT_5 = new WorldPoint(2903, 5450, 0);
	private static final WorldPoint SAFE_SPOT_6 = new WorldPoint(2902, 5453, 0);
	private static final WorldPoint SAFE_SPOT_7 = new WorldPoint(2908, 5456, 0);
	private static final WorldPoint SAFE_SPOT_8 = new WorldPoint(2913, 5454, 0);

	private static final int FIRST_ELEMENTAL = 5802;
	private static final int SECOND_ELEMENTAL = 5803;
	private static final int THIRD_ELEMENTAL = 5804;
	private static final int FOURTH_ELEMENTAL = 5805;
	private static final int FIFTH_ELEMENTAL = 5806;
	private static final int SIXTH_ELEMENTAL = 5807;

	public static int step = 0;
	@Override
	protected int loop()
	{
		if(!Game.isLoggedIn() || Movement.isWalking()) {

			return Rand.nextInt(100, 200);
		}

		if(config.autumnGarden())
		{
			if(Inventory.isFull()) {

				var fountain = TileObjects.getNearest(12941);
				if(fountain != null) {

					fountain.interact("Drink-from");
					return Rand.nextInt(3600, 4200);
				}
				else
				{
					if (!Bank.isOpen())
					{
						if (WorldLocation.AL_KHARID_BANK.getWorldArea().contains(LocalPlayer.get()))
						{
							TileObjects.getNearest("Bank booth").interact("Bank");
						}
						else
						{
							Movement.walkTo(WorldLocation.AL_KHARID_BANK.getWorldArea().getRandom());
						}
					}
					else {
						Bank.depositInventory();
					}
				}
			}
			else if(!CENTER_GARDEN.contains(LocalPlayer.get()) && CENTER_GARDEN.distanceTo(LocalPlayer.get()) > 200){

				if(Bank.isOpen()) {

					Bank.close();
				}
				else if(APPRENTICE_AREA.contains(LocalPlayer.get())){

					NPCs.getNearest("Apprentice").interact("Teleport");
					return 3000;
				}
				else {

					Movement.walkTo(APPRENTICE_AREA);
				}
			}

			if (CENTER_GARDEN.contains(LocalPlayer.get()))
			{
				TileObjects.getNearest(12639).interact("Open");

				step = 1;

				return Rand.nextInt(4500, 5000);
			}
			else if (step == 1)
			{
				if(!SAFE_SPOT_1.toWorldArea().contains(LocalPlayer.get())) {

					Movement.walkTo(SAFE_SPOT_1);
					return Rand.nextInt(2000, 2200);
				}
				else {
					step = 2;
				}
			}
			else if (step == 2)
			{
				var element = NPCs.getNearest(FIRST_ELEMENTAL);

				if (element.getOrientation() == 512 && element.getWorldLocation().getX() < 2906)
				{
					if(!SAFE_SPOT_2.toWorldArea().contains(LocalPlayer.get())) {

						Movement.walkTo(SAFE_SPOT_2);
						return Rand.nextInt(1200, 1600);
					}
					else {
						step = 3;
					}
				}
			}
			else if(step == 3) {

				var element = NPCs.getNearest(FIRST_ELEMENTAL);

				if (element.getOrientation() == 1536 && element.getWorldLocation().getX() < 2907 && element.getWorldLocation().getX() > 2904)
				{
					if(!SAFE_SPOT_3.toWorldArea().contains(LocalPlayer.get())) {

						Movement.walkTo(SAFE_SPOT_3);
						return Rand.nextInt(2000, 2200);
					}
					else {
						step = 4;
					}
				}
			}
			else if(step == 4) {

				var element = NPCs.getNearest(SECOND_ELEMENTAL);

				if (element.getOrientation() == 0 && element.getWorldLocation().getY() <= 5454)
				{
					if(!SAFE_SPOT_4.toWorldArea().contains(LocalPlayer.get())) {

						Movement.walkTo(SAFE_SPOT_4);
						return Rand.nextInt(2000, 2200);
					}
					else {
						step = 5;
					}
				}
			}
			else if(step == 5) {

				var second_element = NPCs.getNearest(SECOND_ELEMENTAL);
				var third_element = NPCs.getNearest(THIRD_ELEMENTAL);

				if (second_element.getOrientation() == 1024 && second_element.getWorldLocation().getY() > 5451 &&
						third_element.getOrientation() == 1536 && third_element.getWorldLocation().getX() < 2902 && third_element.getWorldLocation().getX() > 2900)
				{
					if(!SAFE_SPOT_5.toWorldArea().contains(LocalPlayer.get())) {

						Movement.walkTo(SAFE_SPOT_5);
						return Rand.nextInt(1400, 1600);
					}
					else {
						step = 6;
					}
				}
			}
			else if(step == 6) {

				var fourth_element = NPCs.getNearest(FOURTH_ELEMENTAL);

				if (fourth_element.getWorldLocation().getY() > 5453
						|| (fourth_element.getWorldLocation().getY() > 5450 && fourth_element.getWorldLocation().getX() >= 2905))
				{
					if(!SAFE_SPOT_6.toWorldArea().contains(LocalPlayer.get())) {

						Movement.walkTo(SAFE_SPOT_6);
						return Rand.nextInt(1200, 1800);
					}
					else {
						step = 7;
					}
				}
			}
			else if(step == 7) {

				var fourth_element = NPCs.getNearest(FOURTH_ELEMENTAL);
				var fifth_element = NPCs.getNearest(FIFTH_ELEMENTAL);

				if ((fourth_element.getWorldLocation().getX() >= 2904 || fourth_element.getWorldLocation().getY() > 5453) &&
						(fifth_element.getOrientation() == 1536 || fifth_element.getWorldLocation().getX() < 2905))
				{
					if(!SAFE_SPOT_7.toWorldArea().contains(LocalPlayer.get())) {

						Movement.walkTo(SAFE_SPOT_7);
						return Rand.nextInt(1600, 2000);
					}
					else {
						step = 8;
					}
				}
			}
			else if(step == 8) {

				var sixth_elemental = NPCs.getNearest(SIXTH_ELEMENTAL);

				if (sixth_elemental.getOrientation() == 1536 && sixth_elemental.getWorldLocation().getX() < 2914
						&& sixth_elemental.getWorldLocation().getX() > 2910)
				{
					if(!SAFE_SPOT_8.toWorldArea().contains(LocalPlayer.get())) {

						Movement.walkTo(SAFE_SPOT_8);
						return Rand.nextInt(1000, 1200);
					}
					else {
						step = 9;
					}
				}
			}
			else if(step == 9) {

				TileObjects.getNearest(13406).interact("Pick-Fruit");
				step = 0;
				return Rand.nextInt(2600, 3600);
			}
		}

		return 10;
	}

	@Provides
	VitalGardenConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalGardenConfig.class);
	}
}
