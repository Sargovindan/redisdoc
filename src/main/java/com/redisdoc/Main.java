package com.redisdoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import jline.console.completer.StringsCompleter;

import com.redisdoc.command.CommandClear;
import com.redisdoc.command.CommandDdel;
import com.redisdoc.command.CommandDget;
import com.redisdoc.command.CommandDkeys;
import com.redisdoc.command.CommandDselect;
import com.redisdoc.command.CommandDset;
import com.redisdoc.command.CommandDundkeys;
import com.redisdoc.command.CommandInfoget;
import com.redisdoc.command.CommandHelp;
import com.redisdoc.command.CommandKeys;
import com.redisdoc.command.CommandQuit;
import com.redisdoc.command.CommandSelect;
import com.redisdoc.command.CommandInfoset;
import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.command.util.CommandHandler;
import com.redisdoc.command.util.CommandListener;
import com.redisdoc.exception.RedisdocException;

public class Main {

	private static void welcome() {
		System.out.println("welcome to redisdoc");
		System.out.println("start with help");
		System.out.println("autocompletion works using TAB key");
	}

	public static void main(String[] args) throws IOException {

		welcome();

		ConsoleReader console = new ConsoleReader();
		console.setPrompt("command[" + RedisdocProperties.getProperty(RedisdocProperties.DATABASE_DBINDEX) + "]>");
		console.setBellEnabled(false);
		String line = null;

		try {
			// All commands must be registered here. To create a new command,
			// create a class that extends AbstractCommand, implement abstract
			// methods and register your command here.
			CommandHandler handler = new CommandHandler();
			handler.register(new CommandHelp(handler));
			handler.register(new CommandClear(console));
			CommandQuit commandQuit = new CommandQuit();
			handler.register(commandQuit);
			handler.register(new CommandSelect(console));
			handler.register(new CommandDselect());
			handler.register(new CommandKeys());
			handler.register(new CommandInfoset());
			handler.register(new CommandInfoget());
			handler.register(new CommandDset());
			handler.register(new CommandDget());
			handler.register(new CommandDdel());
			handler.register(new CommandDkeys());
			handler.register(new CommandDundkeys());

			// auto complete feature in console
			AggregateCompleter aggregateCompleter = new AggregateCompleter();
			Iterator<CommandListener> iterator = handler.iterator();
			while (iterator.hasNext()) {
				CommandListener commandListener = (CommandListener) iterator.next();
				StringsCompleter completerCommands = new StringsCompleter(commandListener.getCommand());
				StringsCompleter completerArguments = new StringsCompleter(commandListener.getArguments());
				List<Completer> completers = new ArrayList<Completer>();
				completers.add(completerCommands);
				for (int i = 0; i < commandListener.getArguments().length; i++) {
					completers.add(completerArguments);
				}
				completers.add(new NullCompleter());
				ArgumentCompleter argumentCompleter = new ArgumentCompleter(completers);
				aggregateCompleter.getCompleters().add(argumentCompleter);
			}
			console.addCompleter(aggregateCompleter);

			while ((line = console.readLine()) != null) {
				if (commandQuit.isCalled(line)) {
					break;
				}
				try {
					handler.notify(line.trim());
				} catch (RedisdocException ex) {
					System.out.println(ex.getMessage());
				}
			}

		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			AbstractCommand.disconnectRedisClients();
			try {
				TerminalFactory.get().restore();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
