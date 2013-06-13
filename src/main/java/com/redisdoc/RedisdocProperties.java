package com.redisdoc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class RedisdocProperties {

	public static final String DATABASE_HOSTNAME = "database.host";
	public static final String DATABASE_PORT = "database.port";
	public static final String REDISDOC_HOSTNAME = "redisdoc.host";
	public static final String REDISDOC_PORT = "redisdoc.port";
	public static final String REDISDOC_PREFIX = "redisdoc.prefix";
	public static final String MAX_KEYS_OUTPUT = "max.keys.output";
	public static final String DATABASE_DBINDEX = "database.dbindex";
	public static final String REDISDOC_DBINDEX = "redisdoc.dbindex";

	private static Properties properties;

	static {
		properties = new Properties();
		File propertiesFile = new File("redisdoc.properties");
		if (propertiesFile.exists()) {
			try {
				properties.load(new FileReader(propertiesFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// default values
			setProperty(MAX_KEYS_OUTPUT, 100);
			setProperty(REDISDOC_PREFIX, "redisdoc:");
			setProperty(DATABASE_HOSTNAME, "localhost");
			setProperty(DATABASE_PORT, 6379);
			setProperty(REDISDOC_HOSTNAME, "localhost");
			setProperty(REDISDOC_PORT, 6379);
			setProperty(REDISDOC_DBINDEX, 0);
			setProperty(DATABASE_DBINDEX, 0);
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public static void setProperty(String key, int value) {
		properties.setProperty(key, Integer.toString(value));
	}

}
