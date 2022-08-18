package dev.vital.quester.tasks;

import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DialogTask {

    boolean task_completed;
    String name;
    WorldPoint point;
    List<String> dialog_options;
    boolean has_opened;

    public DialogTask(String name, WorldPoint point, String... dialog_options) {
        this.task_completed = false;
        this.name = name;
        this.point = point;
        this.dialog_options = null;
        this.has_opened = false;
        if(dialog_options != null) {
            this.dialog_options = new ArrayList<>();

            Collections.addAll(this.dialog_options, dialog_options);
        }
    }

    public int execute() {

        this.task_completed = this.dialog_options.isEmpty();

        if(this.task_completed) {
            return -1;
        }

        return Tools.talkTo(name, point, dialog_options);
    }

    public boolean taskCompleted() {
        return this.task_completed;
    }
}
