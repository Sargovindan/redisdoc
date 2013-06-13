package com.redisdoc.command.util;

import java.util.ArrayList;
import java.util.Iterator;

import com.redisdoc.exception.RedisdocException;
import com.redisdoc.exception.UnknownCommandException;

public class CommandHandler {
	private ArrayList<CommandListener> listeners = new ArrayList<CommandListener>();

	public void register(CommandListener listener) {
		// command must have some methods implemented, ensure here that it does
		boolean valid = true;
		if (listener.getCommand() == null) {
			valid = false;
		}
		if (listener.getParamCount() == null) {
			valid = false;
		}
		if (listener.getUsage() == null) {
			valid = false;
		}

		if (valid) {
			listeners.add(listener);
		} else {
			System.out.println("Cannot add command " + listener.getClass().getName() + "! Implement required methods!");
		}
	}

	public Iterator<CommandListener> iterator() {
		return listeners.iterator();
	}

	public CommandListener get(String searchedCommand) {
		for (CommandListener listener : listeners) {
			String[] commands = listener.getCommand();
			for (String command : commands) {
				if (command.equals(searchedCommand)) {
					return listener;
				}
			}
		}
		return null;
	}

	public void notify(String command) throws RedisdocException {
		boolean called = false;
		for (CommandListener commandListener : listeners) {
			if (commandListener.isCalled(command)) {
				commandListener.execute(command);
				called = true;
				break;
			}
		}
		if (!called) {
			throw new UnknownCommandException();
		}
	}

}
