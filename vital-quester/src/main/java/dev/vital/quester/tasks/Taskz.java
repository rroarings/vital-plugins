package dev.vital.quester.tasks;

import com.google.inject.Inject;
import dev.vital.quester.VitalQuesterConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Taskz implements ScriptTask
{
	@Inject
	public VitalQuesterConfig quester_config;
}
