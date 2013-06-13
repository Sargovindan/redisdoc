package com.redisdoc.command;

import jline.console.ConsoleReader;

import com.redisdoc.RedisdocProperties;
import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandSelect extends AbstractCommand {

	private ConsoleReader console;

	public CommandSelect(ConsoleReader console) {
		this.console = console;
	}

	public String[] getCommand() {
		return new String[] { "select" };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		try {
			int db = Integer.parseInt(strings[1].trim());
			if (db >= 0) {
				RedisdocProperties.setProperty(RedisdocProperties.DATABASE_DBINDEX, db);
				jedisDatabase.select(db);
				console.setPrompt("command[" + db + "]>");
			} else {
				throw new CommandParameterException("Error! Database must be number >= 0!");
			}
		} catch (NumberFormatException e) {
			throw new CommandParameterException("Error! Database must be number!");
		}
	}

	public String[] getUsage() {
		return new String[] { "select <number>", "select redis database with your keys" };
	}

	public int[] getParamCount() {
		return new int[] { 2 };
	}

}
