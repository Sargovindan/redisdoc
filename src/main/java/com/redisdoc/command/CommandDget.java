package com.redisdoc.command;

import java.util.Set;

import com.redisdoc.RedisdocProperties;
import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.command.util.RedisClient.OutputSet;
import com.redisdoc.exception.CommandParameterException;

public class CommandDget extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "dget" };
	}

	public String[] getUsage() {
		return new String[] { "dget <doc-name>", "documentation key detail", "contains several optional parameters, which can be combined",
				"dget --type <doc-name>", "show keys with their types", "dget --detail <doc-name>", "show keys with their values or length",
				"dget --ttl <doc-name>", "show keys with ttl" };
	}

	@Override
	public String[] getArguments() {
		return new String[] { "--type", "--detail", "--ttl" };
	}

	public int[] getParamCount() {
		return new int[] { 2, 3, 4, 5 };
	}

	@Override
	public void executeCommand(String[] strings) throws CommandParameterException {
		boolean showTypes = false;
		boolean showDetail = false;
		boolean showTtl = false;
		if (strings.length > 1) {
			for (String string : strings) {
				if (string.equals("--type")) {
					showTypes = true;
				}
				if (string.equals("--detail")) {
					showDetail = true;
				}
				if (string.equals("--ttl")) {
					showTtl = true;
				}
			}
		}

		OutputSet outputSet = jedisDatabase.keys(
				strings[strings.length - 1].replace(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PREFIX), ""), true);
		Set<String> keys = outputSet.keys;

		for (String key : keys) {
			if (RedisdocProperties.getProperty(RedisdocProperties.DATABASE_DBINDEX) == RedisdocProperties
					.getProperty(RedisdocProperties.REDISDOC_DBINDEX)
					&& key.startsWith(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PREFIX))) {
				// do not show this key - it is a documentation
			} else {

				String type = jedisDatabase.type(key);
				String ttl = jedisDatabase.ttl(key) + "";
				String detail = jedisDatabase.detail(key);
				System.out.println("KEY: " + key);
				if (showTypes && showTtl) {
					System.out.println(String.format("TYPE: %6s; TTL: %s", type, ttl));
				} else if (showTtl) {
					System.out.println(String.format("TTL: %s", ttl));
				} else if (showTypes) {
					System.out.println(String.format("TYPE: %6s", type));
				}
				if (showDetail) {
					System.out.println("DETAIL: " + detail);
				}
				System.out.println();
			}
		}
		if (outputSet.cropped) {
			System.out.println("Too many keys. Showing first " + RedisdocProperties.getProperty(RedisdocProperties.MAX_KEYS_OUTPUT) + " keys.");
		}

	}

}
