package com.redisdoc.command.util;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.redisdoc.RedisdocProperties;

public class RedisClient {

	public enum DatabaseType {
		DATABASE, REDISDOC
	}

	private Jedis jedis;

	private DatabaseType type;

	public RedisClient(DatabaseType type) {
		this.type = type;
		int dbindex;
		String host;
		int port;
		if (type == DatabaseType.DATABASE) {
			dbindex = Integer.parseInt(RedisdocProperties.getProperty(RedisdocProperties.DATABASE_DBINDEX));
			host = RedisdocProperties.getProperty(RedisdocProperties.DATABASE_HOSTNAME);
			port = Integer.parseInt(RedisdocProperties.getProperty(RedisdocProperties.DATABASE_PORT));
		} else {
			dbindex = Integer.parseInt(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_DBINDEX));
			host = RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_HOSTNAME);
			port = Integer.parseInt(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PORT));
		}
		jedis = new Jedis(host, port);
		jedis.connect();
		jedis.select(dbindex);
	}

	/**
	 * disconnect at the end of application
	 */
	public void disconnect() {
		jedis.disconnect();
	}

	public void select(int db) {
		jedis.select(db);
	}

	public void del(String key) {
		key = addRedisdocPrefix(key);
		jedis.del(key);
	}

	/**
	 * Call redis command: "keys &lt;pattern&gt;"
	 * 
	 * @param pattern
	 *            Pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		pattern = addRedisdocPrefix(pattern);
		return jedis.keys(pattern);
	}

	public class OutputSet {
		public Set<String> keys;
		public boolean cropped;

		public OutputSet(Set<String> keys, boolean cropped) {
			this.keys = keys;
			this.cropped = cropped;
		}

	}

	/**
	 * Limit results of input set so they don't exceed KEYSMAXOUTPUT, which is
	 * defined in RedisdocProperties
	 * 
	 * @param inputSet
	 *            Input set
	 * @return Output set
	 */
	public static Set<String> limitResults(Set<String> inputSet) {
		int limit = Integer.parseInt(RedisdocProperties.getProperty(RedisdocProperties.MAX_KEYS_OUTPUT));
		if (inputSet.size() <= limit) {
			return inputSet;
		}
		Set<String> outputSet = new HashSet<String>(limit);
		int i = 0;
		for (String string : inputSet) {
			outputSet.add(string);
			i++;
			if (i == limit) {
				break;
			}
		}
		return outputSet;
	}

	/**
	 * Call redis command: "keys &lt;pattern&gt;". Maximum number of retrieved
	 * keys is set in configuration. Useful in user output.
	 * 
	 * @param pattern
	 *            Pattern
	 * @return
	 */
	public OutputSet keys(String pattern, boolean crop) {
		pattern = addRedisdocPrefix(pattern);
		Set<String> result = jedis.keys(pattern);
		int originalSize = result.size();
		if (crop) {
			result = limitResults(result);
		}
		return new OutputSet(result, originalSize != result.size());
	}

	public String get(String key) {
		key = addRedisdocPrefix(key);
		return jedis.get(key);
	}

	public void set(String key, String value) {
		key = addRedisdocPrefix(key);
		jedis.set(key, value);
	}

	/**
	 * If database is Redisdoc database and input string doesn't contain
	 * Redisdoc prefix, add prefix.
	 * 
	 * @param string
	 *            Input param with or without prefix
	 * @return Value with prefix
	 */
	private String addRedisdocPrefix(String string) {
		if (type == DatabaseType.REDISDOC) {
			if (!string.startsWith(RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PREFIX))) {
				string = RedisdocProperties.getProperty(RedisdocProperties.REDISDOC_PREFIX) + string;
			}
		}
		return string;
	}

	public String type(String key) {
		return jedis.type(key);
	}

	public String detail(String key) {
		String type = type(key);
		if ("string".equals(type)) {
			return get(key);
		} else if ("list".equals(type)) {
			return "llen = " + llen(key);
		} else if ("set".equals(type)) {
			return "scard = " + scard(key);
		} else if ("zset".equals(type)) {
			return "zcard = " + zcard(key);
		} else if ("hash".equals(type)) {
			return "hlen = " + hlen(key);
		}
		return null;
	}

	public long hlen(String key) {
		return jedis.hlen(key);
	}

	public long zcard(String key) {
		return jedis.zcard(key);
	}

	public long scard(String key) {
		return jedis.scard(key);
	}

	public long llen(String key) {
		return jedis.llen(key);
	}

	public long ttl(String key) {
		return jedis.ttl(key);
	}

}
