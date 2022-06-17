package dev.vital.quester.tasks;

import dev.vital.quester.VitalQuesterConfig;

public interface ScriptTask
{
	/**
	 * The check to validate the execution of the task.
	 *
	 * @return whether the task should be executed or not.
	 * @param quester_config
	 */
	boolean validate(VitalQuesterConfig quester_config);

	/**
	 * The loop logic.
	 *
	 * @return the amount of milliseconds to sleep after execution.
	 */
	int execute();

	/**
	 * @return true if the task blocks subsequent tasks.
	 */
	default boolean blocking()
	{
		return true;
	}
}

