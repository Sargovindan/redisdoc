package com.redisdoc.command;

import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandInfoget extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "dinfoget" };
	}

	public String[] getUsage() {
		return new String[] { "dinfoget", "get database information - author, database name and description" };
	}

	public int[] getParamCount() {
		return new int[] { 1 };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		String name = jedisRedisdoc.get("info:name");
		if (name == null) {
			name = "<undefined>";
		}
		System.out.println("db name:     " + name);

		String author = jedisRedisdoc.get("info:author");
		if (author == null) {
			author = "<undefined>";
		}
		System.out.println("author:      " + author);

		String description = jedisRedisdoc.get("info:description");
		if (description == null) {
			description = "<undefined>";
		}
		System.out.println("description: " + description);
	}

}
