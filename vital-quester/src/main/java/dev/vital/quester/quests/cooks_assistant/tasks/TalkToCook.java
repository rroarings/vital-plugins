package dev.vital.quester.quests.cooks_assistant.tasks;

import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.ScriptTask;
import net.unethicalite.api.widgets.Dialog;
import dev.vital.quester.tools.Tools;

public class TalkToCook implements ScriptTask
{
    private final WorldPoint cooks_point = new WorldPoint(3210, 3213, 0);

    VitalQuesterConfig config;

    public TalkToCook(VitalQuesterConfig config)
    {
        this.config = config;
    }

    @Override
    public boolean validate()
    {
        return Inventory.contains(ItemID.EGG) && Inventory.contains(ItemID.BUCKET_OF_MILK) && Inventory.contains(ItemID.POT_OF_FLOUR);
    }

    @Override
    public int execute()
    {
        if(Dialog.isOpen()) {
            if(Dialog.isViewingOptions()){
                if(Tools.getDialogueHeader().contains("Start the Cook's Assistant quest?")) {
                    Dialog.chooseOption("Yes.");
                }
                else if(Tools.getDialogueHeader().contains("Select an Option")) {
                    Dialog.chooseOption("What's wrong?");
                }
            }
        }
        else {
            if (Tools.interactWith("Cook", "Talk-to", cooks_point, Tools.EntityType.NPC) == -5) {
                return -5;
            }
        }
        return -1;
    }
}
