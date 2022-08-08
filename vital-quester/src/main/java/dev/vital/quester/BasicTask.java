package dev.vital.quester;

public class BasicTask {
    public interface TaskFunction {
        int execute();
    }
    boolean task_completed;
    TaskFunction task_function;

    public BasicTask(TaskFunction task_function) {
        this.task_completed = false;
        this.task_function = task_function;
    }

    public int execute() {

        int sleep = this.task_function.execute();
        if(sleep == 0) {
            task_completed = true;
        }

        return sleep;
    }

    public boolean taskCompleted() {
        return this.task_completed;
    }
}

