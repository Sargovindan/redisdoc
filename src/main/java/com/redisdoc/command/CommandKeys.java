package com.redisdoc.command;

import java.util.Set;

import com.redisdoc.RedisdocProperties;
import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.command.util.RedisClient.OutputSet;
import com.redisdoc.exception.CommandParameterException;

public class CommandKeys extends AbstractCommand {

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		OutputSet outputSet = jedisDatabase.keys(strings[1], true);
		Set<String> keys = outputSet.keys;

		for (String key : keys) {
			System.out.println(key);
		}
		if (outputSet.cropped) {
			System.out.println("Too many keys. Showing first " + RedisdocProperties.getProperty(RedisdocProperties.MAX_KEYS_OUTPUT) + " keys.");
		}
	}

	public String[] getCommand() {
		return new String[] { "keys" };
	}

	public String[] getUsage() {
		return new String[] { "keys <pattern>", "select keys using pattern" };
	}

	public int[] getParamCount() {
		return new int[] { 2 };
	}
}
