package dev.vital.quester.quests.sheep_shearer.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DepositTask;

public class BankInventory implements ScriptTask
{
	VitalQuesterConfig config;
	DepositTask deposit_all = new DepositTask(null);

	public BankInventory(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return config.sheepShearerBankInventory() && !deposit_all.taskCompleted();
	}

	@Override
	public int execute()
	{

		return deposit_all.execute();
	}
}
