package dev.vital.quester;

import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DialogTask {

    public interface TaskFunction {
        int execute(List<String> dialog_options);
    }

    boolean task_completed;
    String name;
    WorldPoint point;
    List<String> dialog_options;

    public DialogTask(String name, WorldPoint point, String... dialog_options) {
        this.task_completed = false;
        this.name = name;
        this.point = point;
        this.dialog_options = new ArrayList<>();

        if(dialog_options != null) {
            Collections.addAll(this.dialog_options, dialog_options);
        }
    }

    public int execute() {
        return Tools.talkTo(name, point, dialog_options);
    }

    public boolean taskCompleted() {
        return this.dialog_options.isEmpty();
    }
}
