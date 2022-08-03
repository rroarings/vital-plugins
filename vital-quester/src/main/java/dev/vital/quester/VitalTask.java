package dev.vital.quester;

public class VitalTask {
    public interface TaskFunction {
        boolean execute();
    }
    boolean task_completed;
    TaskFunction task_function;

    public VitalTask(TaskFunction task_function) {
        this.task_completed = false;
        this.task_function = task_function;
    }

    public boolean execute() {
        if (!task_completed) {
            task_completed = this.task_function.execute();
        }

        return task_completed;
    }

    public boolean taskCompleted() {
        return this.task_completed;
    }
}
