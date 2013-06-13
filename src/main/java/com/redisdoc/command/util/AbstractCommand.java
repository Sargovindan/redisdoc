package com.redisdoc.command.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.redisdoc.command.util.RedisClient.DatabaseType;
import com.redisdoc.exception.CommandParameterException;

/**
 * Extend this class to implement a new command
 * 
 */
public abstract class AbstractCommand implements CommandListener {

	protected static final RedisClient jedisDatabase;
	protected static final RedisClient jedisRedisdoc;

	static {
		jedisDatabase = new RedisClient(DatabaseType.DATABASE);
		jedisRedisdoc = new RedisClient(DatabaseType.REDISDOC);
	}

	/**
	 * Call this method at the very end to disconnect connections to redis
	 */
	public static void disconnectRedisClients() {
		try {
			if (jedisDatabase != null) {
				jedisDatabase.disconnect();
			}
		} catch (Exception ex) {
			// do nothing
		}
		try {
			if (jedisRedisdoc != null) {
				jedisRedisdoc.disconnect();
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	private final String[] splitCommand(final String command) {
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
		while (m.find())
			list.add(m.group(1).replace("\"", ""));
		return list.toArray(new String[] {});
	}

	public final void execute(final String command) throws CommandParameterException {
		String[] strings = splitCommand(command);

		boolean valid = false;
		int[] paramCount = getParamCount();
		for (int param : paramCount) {
			if (param == strings.length) {
				valid = true;
			}
		}

		if (!valid) {
			String errorMessage = "Incorrect usage! Try: ";
			String[] usage = getUsage();
			for (String string : usage) {
				errorMessage += string + "\n";
			}
			errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
			throw new CommandParameterException(errorMessage);
		}
		executeCommand(strings);
	}

	/**
	 * Method, which is called in execute() after command parameters has been
	 * split into a field of strings.
	 * 
	 * @param strings
	 *            Command split into field of strings (for example ["keys",
	 *            "*"])
	 * @throws CommandParameterException
	 */
	public abstract void executeCommand(String[] strings) throws CommandParameterException;

	public final boolean isCalled(final String command) {
		if (command == null) {
			return false;
		}

		String[] commands = getCommand();

		for (String c : commands) {

			if (command.indexOf(" ") != -1 && command.startsWith(c + " ")) {
				return true;
			}

			if (command.indexOf(" ") == -1 && command.startsWith(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Most commands don't have any arguments. So this is the default. If a
	 * command has any arguments, just override this method.
	 */
	public String[] getArguments() {
		return new String[0];
	}
}
