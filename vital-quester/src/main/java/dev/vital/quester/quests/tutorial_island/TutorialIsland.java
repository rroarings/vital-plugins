package dev.vital.quester.quests.tutorial_island;

import com.google.common.collect.ImmutableSet;
import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.tutorial_island.tasks.*;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.GameSettings;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.widgets.Widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private static final Set<Integer> TUTORIAL_ISLAND_REGIONS = ImmutableSet.of(12336, 12335, 12592, 12080, 12079, 12436);

    @Override
    public boolean validate()
    {
        return (config.currentQuest().equals(QuestList.TUTORIAL_ISLAND) || config.automaticOptimal()) && TUTORIAL_ISLAND_REGIONS.contains(LocalPlayer.get().getWorldLocation().getRegionID());
    }

    void handleDisplay() {
        if(GameSettings.Display.getCurrentMode() == GameSettings.Display.RESIZABLE_MODERN) {

            var settings_menu = Widgets.get(164,40);
            if(settings_menu == null) {
                return;
            }

            Mouse.click(settings_menu.getClickPoint().getAwtPoint(), true);
            Time.sleepTick();

            var display_menu = Widgets.get(116, 112);
            if(display_menu == null) {
                return;
            }

            Mouse.click(display_menu.getClickPoint().getAwtPoint(), true);
            Time.sleepTick();

            var drop_down = Widgets.get(116, 27, 3);
            if(drop_down == null) {
                return;
            }

            Mouse.click(drop_down.getClickPoint().getAwtPoint(), true);
            Time.sleepTick();

            var fixed = Widgets.get(116, 84, 1);
            if(fixed == null) {
                return;
            }

            Mouse.click(fixed.getClickPoint().getAwtPoint(), true);
            Time.sleepTick();
        }
    }

    @Override
    public int execute() {

        handleDisplay();

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
