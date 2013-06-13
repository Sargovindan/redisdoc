package com.redisdoc.command;

import com.redisdoc.RedisdocProperties;
import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandDselect extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "dselect" };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		try {
			int rddb = Integer.parseInt(strings[1].trim());
			if (rddb >= 0) {
				RedisdocProperties.setProperty(RedisdocProperties.REDISDOC_DBINDEX, rddb);
				jedisRedisdoc.select(rddb);
			} else {
				throw new CommandParameterException("Error! Database must be number >= 0!");
			}
		} catch (NumberFormatException e) {
			throw new CommandParameterException("Error! Database must be number!");
		}
	}

	public String[] getUsage() {
		return new String[] { "dselect <number>", "select redis database for documentation" };
	}

	public int[] getParamCount() {
		return new int[] { 2 };
	}
}
