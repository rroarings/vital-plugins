package dev.vital.quester.tasks;

import net.unethicalite.api.commons.Time;
import net.unethicalite.api.input.Keyboard;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class CameraTask {

    boolean task_completed;
    int sleep_amount;

    public CameraTask(int sleep_amount) {
        this.task_completed = false;
        this.sleep_amount = sleep_amount;
    }

    public void moveRight() {

        Keyboard.pressed(VK_RIGHT);
        Time.sleepTicks(this.sleep_amount);
        Keyboard.released(VK_RIGHT);

        this.task_completed = true;
    }

    public void moveLeft() {

        Keyboard.pressed(VK_LEFT);
        Time.sleepTicks(this.sleep_amount);
        Keyboard.released(VK_LEFT);

        this.task_completed = true;
    }

    public boolean taskCompleted() {
        return this.task_completed;
    }
}

