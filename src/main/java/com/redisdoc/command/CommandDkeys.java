package com.redisdoc.command;

import java.util.Iterator;
import java.util.Set;

import com.redisdoc.RedisdocProperties;
import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.command.util.RedisClient.OutputSet;
import com.redisdoc.exception.CommandParameterException;

public class CommandDkeys extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "dkeys" };
	}

	public String[] getUsage() {
		return new String[] { "dkeys <pattern>", "list documentation keys using pattern" };
	}

	public int[] getParamCount() {
		return new int[] { 2 };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		OutputSet outputSet = jedisRedisdoc.keys(strings[1], true);
		Set<String> keys = outputSet.keys;
		for (String key : keys) {
			String value = jedisRedisdoc.get(key);
			String keyShortName = key.replace(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PREFIX), "");
			Set<String> documentedKeys = jedisDatabase.keys(keyShortName);

			// if Redisdoc == Database, then documentedKeys can contain keys
			// from Redisdoc, so remove them here
			Iterator<String> iterator = documentedKeys.iterator();
			while (iterator.hasNext()) {
				String dkey = (String) iterator.next();
				if (dkey.startsWith(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PREFIX))) {
					iterator.remove();
				}
			}

			System.out.println("KEY = " + keyShortName);
			System.out.println("VALUE = " + value);
			System.out.println("NUMBER OF DOCUMENTED KEYS = " + documentedKeys.size());
			System.out.println();
		}
		if (outputSet.cropped) {
			System.out.println("Too many keys. Showing first " + RedisdocProperties.getProperty(RedisdocProperties.MAX_KEYS_OUTPUT) + " keys.");
		}
	}
}
