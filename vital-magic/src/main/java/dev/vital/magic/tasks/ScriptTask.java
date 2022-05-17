package dev.vital.magic.tasks;

public interface ScriptTask
{
	boolean validate();

	int execute();

	default boolean blocking()
	{
		return true;
	}
}
