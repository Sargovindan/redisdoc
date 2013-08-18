package com.redisdoc.command;

import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandInfoset extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "dinfoset" };
	}

	public String[] getUsage() {
		return new String[] { "dinfoset <db name> <author> <description>", "set database information - author, database name and description" };
	}

	public int[] getParamCount() {
		return new int[] { 4 };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		jedisRedisdoc.set("info:name", strings[1]);
		jedisRedisdoc.set("info:author", strings[2]);
		jedisRedisdoc.set("info:description", strings[3]);
	}

}
