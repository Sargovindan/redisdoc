package com.redisdoc.command;

import java.util.Set;

import com.redisdoc.RedisdocProperties;
import com.redisdoc.command.util.AbstractCommand;
import com.redisdoc.command.util.RedisClient;
import com.redisdoc.exception.CommandParameterException;

/**
 * list all undocumented keys from database
 * 
 */
public class CommandDundkeys extends AbstractCommand {

	public String[] getCommand() {
		return new String[] { "dundkeys" };
	}

	public String[] getUsage() {
		return new String[] { "dundkeys", "retrieves undocumented keys from your database",
				"contains several optional parameters, that can be combined", "dundkeys --type", "show undocumented keys with their types",
				"dundkeys --detail", "show undocumented keys with their values or length", "dundkeys --ttl", "show undocumented keys with ttl" };
	}

	@Override
	public String[] getArguments() {
		return new String[] { "--type", "--detail", "--ttl" };
	}

	public int[] getParamCount() {
		return new int[] { 1, 2, 3, 4 };
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

		// get all database keys
		Set<String> databaseKeys = jedisDatabase.keys("*");

		// remove all documented keys from database keys set
		Set<String> redisdocKeys = jedisRedisdoc.keys("*");
		for (String redisdocKey : redisdocKeys) {
			// if documentation is in the same database, remove it from results
			if (RedisdocProperties.getProperty(RedisdocProperties.DATABASE_DBINDEX) == RedisdocProperties
					.getProperty(RedisdocProperties.REDISDOC_DBINDEX)) {
				databaseKeys.remove(redisdocKey);
			}
			Set<String> documentedKeys = jedisDatabase.keys(redisdocKey.replace(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PREFIX),
					""));
			databaseKeys.removeAll(documentedKeys);
		}

		// crop result
		int originalSize = databaseKeys.size();
		databaseKeys = RedisClient.limitResults(databaseKeys);

		// print the rest to console
		for (String key : databaseKeys) {
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
		if (originalSize != databaseKeys.size()) {
			System.out.println("Too many keys. Showing first " + RedisdocProperties.getProperty(RedisdocProperties.MAX_KEYS_OUTPUT) + " keys.");
		}
	}
}
