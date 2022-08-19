package dev.vital.quester.tasks;

public class BasicTask
{
	boolean task_completed;
	TaskFunction task_function;
	public BasicTask(TaskFunction task_function)
	{
		this.task_completed = false;
		this.task_function = task_function;
	}

	public int execute()
	{

		int sleep = this.task_function.execute();
		if (sleep == 0)
		{
			task_completed = true;
		}

		return sleep;
	}

	public void setCompletionFlag(boolean flag)
	{
		this.task_completed = flag;
	}

	public boolean taskCompleted()
	{
		return this.task_completed;
	}

	public interface TaskFunction
	{
		int execute();
	}
}

