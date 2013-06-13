package com.redisdoc.command;

import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandDdel extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "ddel" };
	}

	public String[] getUsage() {
		return new String[] { "ddel <doc-name>", "deletes documentation key" };
	}

	public int[] getParamCount() {
		return new int[] { 2 };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		jedisRedisdoc.del(strings[1]);
	}

}
