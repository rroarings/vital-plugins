package dev.vital.quester.quests.tutorial_island;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.tutorial_island.tasks.*;

import java.util.ArrayList;
import java.util.List;

public class TutorialIsland implements ScriptTask
{
    VitalQuesterConfig config;

    static List<ScriptTask> tasks = new ArrayList<>();

    public TutorialIsland(VitalQuesterConfig config) {
        this.config = config;

        tasks.clear();
        tasks.add(new ChooseName(config));
        tasks.add(new ChooseName2(config));
        tasks.add(new ChooseName3(config));
        tasks.add(new ChooseAppearance(config));
        tasks.add(new GielinorGuide(config));
        tasks.add(new OpenSettings(config));
        tasks.add(new GielinorGuide2(config));
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
        return config.currentQuest().equals(QuestList.TUTORIAL_ISLAND);
    }

    @Override
    public int execute() {

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
