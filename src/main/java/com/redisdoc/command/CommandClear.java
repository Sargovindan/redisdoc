package com.redisdoc.command;

import java.io.IOException;

import jline.console.ConsoleReader;

import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.exception.CommandParameterException;

public class CommandClear extends AbstractCommand {

	private ConsoleReader console;

	public CommandClear(ConsoleReader console) {
		this.console = console;
	}

	public String[] getCommand() {
		return new String[] { "clear" };
	}

	public String[] getUsage() {
		return new String[] { "clear", "clear screen" };
	}

	public int[] getParamCount() {
		return new int[] { 1 };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		try {
			console.clearScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
