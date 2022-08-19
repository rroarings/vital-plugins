package dev.vital.quester.quests.tutorial_island;

import com.google.common.collect.ImmutableSet;
import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.tutorial_island.tasks.AttackChicken;
import dev.vital.quester.quests.tutorial_island.tasks.ChooseAppearance;
import dev.vital.quester.quests.tutorial_island.tasks.ChooseName;
import dev.vital.quester.quests.tutorial_island.tasks.ChooseName2;
import dev.vital.quester.quests.tutorial_island.tasks.ChooseName3;
import dev.vital.quester.quests.tutorial_island.tasks.CookShrimp;
import dev.vital.quester.quests.tutorial_island.tasks.CutLogs;
import dev.vital.quester.quests.tutorial_island.tasks.EnterCave;
import dev.vital.quester.quests.tutorial_island.tasks.EquipDagger;
import dev.vital.quester.quests.tutorial_island.tasks.EquipSwordAndShield;
import dev.vital.quester.quests.tutorial_island.tasks.FinishTutorial;
import dev.vital.quester.quests.tutorial_island.tasks.Fish;
import dev.vital.quester.quests.tutorial_island.tasks.GetCopper;
import dev.vital.quester.quests.tutorial_island.tasks.GetTin;
import dev.vital.quester.quests.tutorial_island.tasks.GielinorGuide;
import dev.vital.quester.quests.tutorial_island.tasks.GielinorGuide2;
import dev.vital.quester.quests.tutorial_island.tasks.GoToCombatInstructor;
import dev.vital.quester.quests.tutorial_island.tasks.KillRat;
import dev.vital.quester.quests.tutorial_island.tasks.KillRat2;
import dev.vital.quester.quests.tutorial_island.tasks.LeaveChapel;
import dev.vital.quester.quests.tutorial_island.tasks.LeaveChefsRoom;
import dev.vital.quester.quests.tutorial_island.tasks.LeaveRatCave;
import dev.vital.quester.quests.tutorial_island.tasks.MakeBread;
import dev.vital.quester.quests.tutorial_island.tasks.MakeDough;
import dev.vital.quester.quests.tutorial_island.tasks.MakeFire;
import dev.vital.quester.quests.tutorial_island.tasks.OpenAccountManager;
import dev.vital.quester.quests.tutorial_island.tasks.OpenBank;
import dev.vital.quester.quests.tutorial_island.tasks.OpenBankDoor;
import dev.vital.quester.quests.tutorial_island.tasks.OpenBankDoor2;
import dev.vital.quester.quests.tutorial_island.tasks.OpenChefDoor;
import dev.vital.quester.quests.tutorial_island.tasks.OpenCombat;
import dev.vital.quester.quests.tutorial_island.tasks.OpenEquipment;
import dev.vital.quester.quests.tutorial_island.tasks.OpenEquipmentStats;
import dev.vital.quester.quests.tutorial_island.tasks.OpenFriends;
import dev.vital.quester.quests.tutorial_island.tasks.OpenGate;
import dev.vital.quester.quests.tutorial_island.tasks.OpenGuideDoor;
import dev.vital.quester.quests.tutorial_island.tasks.OpenInventory;
import dev.vital.quester.quests.tutorial_island.tasks.OpenMagic;
import dev.vital.quester.quests.tutorial_island.tasks.OpenPolls;
import dev.vital.quester.quests.tutorial_island.tasks.OpenPrayer;
import dev.vital.quester.quests.tutorial_island.tasks.OpenQuestDoor;
import dev.vital.quester.quests.tutorial_island.tasks.OpenQuests;
import dev.vital.quester.quests.tutorial_island.tasks.OpenRatGate;
import dev.vital.quester.quests.tutorial_island.tasks.OpenSettings;
import dev.vital.quester.quests.tutorial_island.tasks.OpenStats;
import dev.vital.quester.quests.tutorial_island.tasks.SmeltOre;
import dev.vital.quester.quests.tutorial_island.tasks.SmithDagger;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToAccountGuide;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToAccountGuide2;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToBrotherBrace;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToBrotherBrace2;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToBrotherBrace3;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToChef;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToCombatInstructor;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToCombatInstructor2;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToCombatInstructor3;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToExpert;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToExpert2;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToMagicInstructor;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToMagicInstructor2;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToMiningInstructor;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToMiningInstructor2;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToQuestGuide;
import dev.vital.quester.quests.tutorial_island.tasks.TalkToQuestGuide2;
import net.unethicalite.api.account.LocalPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TutorialIsland implements ScriptTask
{
	private static final Set<Integer> TUTORIAL_ISLAND_REGIONS = ImmutableSet.of(12336, 12335, 12592, 12080, 12079, 12436);
	static List<ScriptTask> tasks = new ArrayList<>();
	VitalQuesterConfig config;

	public TutorialIsland(VitalQuesterConfig config)
	{
		this.config = config;

		tasks.clear();
		tasks.add(new ChooseName(config));
		tasks.add(new ChooseName2(config));
		tasks.add(new ChooseName3(config));
		tasks.add(new ChooseAppearance(config));
		tasks.add(new GielinorGuide(config));
		tasks.add(new OpenSettings(config));
		tasks.add(new GielinorGuide2(config));
		tasks.add(new OpenGuideDoor(config));
		tasks.add(new TalkToExpert(config));
		tasks.add(new OpenInventory(config));
		tasks.add(new Fish(config));
		tasks.add(new OpenStats(config));
		tasks.add(new TalkToExpert2(config));
		tasks.add(new CutLogs(config));
		tasks.add(new MakeFire(config));
		tasks.add(new CookShrimp(config));
		tasks.add(new OpenGate(config));
		tasks.add(new OpenChefDoor(config));
		tasks.add(new TalkToChef(config));
		tasks.add(new MakeDough(config));
		tasks.add(new MakeBread(config));
		tasks.add(new LeaveChefsRoom(config));
		tasks.add(new TalkToExpert2(config));
		tasks.add(new OpenQuestDoor(config));
		tasks.add(new TalkToQuestGuide(config));
		tasks.add(new OpenQuests(config));
		tasks.add(new TalkToQuestGuide2(config));
		tasks.add(new EnterCave(config));
		tasks.add(new TalkToMiningInstructor(config));
		tasks.add(new GetTin(config));
		tasks.add(new GetCopper(config));
		tasks.add(new SmeltOre(config));
		tasks.add(new TalkToMiningInstructor2(config));
		tasks.add(new SmithDagger(config));
		tasks.add(new GoToCombatInstructor(config));
		tasks.add(new TalkToCombatInstructor(config));
		tasks.add(new OpenEquipment(config));
		tasks.add(new OpenEquipmentStats(config));
		tasks.add(new EquipDagger(config));
		tasks.add(new TalkToCombatInstructor2(config));
		tasks.add(new EquipSwordAndShield(config));
		tasks.add(new OpenCombat(config));
		tasks.add(new OpenRatGate(config));
		tasks.add(new KillRat(config));
		tasks.add(new TalkToCombatInstructor3(config));
		tasks.add(new KillRat2(config));
		tasks.add(new LeaveRatCave(config));
		tasks.add(new OpenBank(config));
		tasks.add(new OpenPolls(config));
		tasks.add(new OpenBankDoor(config));
		tasks.add(new TalkToAccountGuide(config));
		tasks.add(new OpenAccountManager(config));
		tasks.add(new TalkToAccountGuide2(config));
		tasks.add(new OpenBankDoor2(config));
		tasks.add(new TalkToBrotherBrace(config));
		tasks.add(new OpenPrayer(config));
		tasks.add(new TalkToBrotherBrace2(config));
		tasks.add(new OpenFriends(config));
		tasks.add(new TalkToBrotherBrace3(config));
		tasks.add(new LeaveChapel(config));
		tasks.add(new TalkToMagicInstructor(config));
		tasks.add(new OpenMagic(config));
		tasks.add(new TalkToMagicInstructor2(config));
		tasks.add(new AttackChicken(config));
		tasks.add(new FinishTutorial(config));
	}

	@Override
	public boolean validate()
	{
		return (config.currentQuest().equals(QuestList.TUTORIAL_ISLAND) || config.automaticOptimal()) && TUTORIAL_ISLAND_REGIONS.contains(LocalPlayer.get().getWorldLocation().getRegionID());
	}

	@Override
	public int execute()
	{

		for (ScriptTask task : tasks)
		{
			if (task.validate())
			{
				int sleep = task.execute();
				if (task.blocking())
				{
					return sleep;
				}
			}
		}

		return -1;
	}
}
