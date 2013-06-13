package com.redisdoc.command;

import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandDset extends AbstractCommand {

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		// save documentation
		jedisRedisdoc.set(strings[1], strings[2]);
	}

	public String[] getCommand() {
		return new String[] { "dset" };
	}

	public String[] getUsage() {
		return new String[] { "dset <doc-name (pattern)> <description>", "set documentation key (pattern) with description" };
	}

	public int[] getParamCount() {
		return new int[] { 3 };
	}
}
