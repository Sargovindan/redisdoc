package com.redisdoc.command;

import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandQuit extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "quit", "exit" };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		System.out.println("bye");
	}

	public String[] getUsage() {
		return new String[] { "quit OR exit", "quit application" };
	}

	public int[] getParamCount() {
		return new int[] { 1 };
	}

}
