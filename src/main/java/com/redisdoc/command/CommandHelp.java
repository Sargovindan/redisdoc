package com.redisdoc.command;

import java.util.ArrayList;
import java.util.Iterator;

import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.command.util.CommandHandler;
import com.redisdoc.command.util.CommandListener;
import com.redisdoc.exception.CommandParameterException;

public class CommandHelp extends AbstractCommand {

	private CommandHandler handler;

	public CommandHelp(CommandHandler handler) {
		this.handler = handler;
	}

	private void printHelp(CommandListener listener) {
		String[] usage = listener.getUsage();
		for (String string : usage) {
			System.out.println(string);
		}
		System.out.println();
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		if (strings.length == 1) {
			System.out.println("Available commands:");
			Iterator<CommandListener> iterator = handler.iterator();
			while (iterator.hasNext()) {
				CommandListener listener = (CommandListener) iterator.next();
				printHelp(listener);
			}
		} else {
			CommandListener listener = handler.get(strings[1]);
			if (listener == null) {
				throw new CommandParameterException("There is no such command!");
			}
			printHelp(listener);
		}
	}

	public String[] getCommand() {
		return new String[] { "help" };
	}

	public String[] getUsage() {
		return new String[] { "help", "show help for all commands", "help <command>", "show help for specific command" };
	}

	public int[] getParamCount() {
		return new int[] { 1, 2 };
	}

	@Override
	public String[] getArguments() {
		ArrayList<String> allCommandNames = new ArrayList<String>();
		Iterator<CommandListener> iterator = handler.iterator();
		while (iterator.hasNext()) {
			CommandListener listener = (CommandListener) iterator.next();
			String[] commandNames = listener.getCommand();
			for (String commandName : commandNames) {
				allCommandNames.add(commandName);
			}
		}
		return allCommandNames.toArray(new String[] {});
	}

}
