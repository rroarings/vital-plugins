package dev.vital.fisher.tasks;

import dev.vital.fisher.VitalFisher;
import dev.vital.fisher.VitalFisherConfig;
import dev.vital.quester.tools.Tools;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.widgets.Production;

public class GoCook implements ScriptTask
{
    VitalFisherConfig config;

    public GoCook(VitalFisherConfig config)
    {
        this.config = config;
    }

    boolean cook_items = false;

    @Override
    public boolean validate()
    {
        cook_items = Inventory.isFull() && Inventory.query().results().stream().anyMatch(x -> x.getName().contains("Raw"));

        return config.cookFish() && cook_items;
    }

    @Override
    public int execute()
    {
        var cooking_object = TileObjects.getNearest(config.cookingObject());
        if (cooking_object == null || !Reachable.isInteractable(cooking_object))
        {
            if (!Movement.isWalking())
            {
                Movement.walkTo(VitalFisher.cook_location);
            }

            return -1;
        }

        if (Tools.isAnimating(5))
        {
            return -1;
        }

        if (Production.isOpen())
        {
            Production.chooseOption(1);
            return -3;
        }

        var raw_food = Inventory.getFirst(x -> x.getName().contains("Raw"));
        if (raw_food != null)
        {
            raw_food.useOn(cooking_object);
        }

        return -1;
    }
}
