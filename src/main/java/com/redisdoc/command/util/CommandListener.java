package com.redisdoc.command.util;

import com.redisdoc.exception.CommandInternalError;
import com.redisdoc.exception.CommandParameterException;

public interface CommandListener {

	/**
	 * Whether this command should be executed. Compares beginning of a command
	 * with result of getCommand() method, which must be overriden in
	 * implementation class.
	 * 
	 * @param command
	 *            Whole command (for example keys *)
	 * @return Whether this command should be executed
	 * @throws CommandInternalError
	 */
	boolean isCalled(String command) throws CommandInternalError;

	/**
	 * Execute command
	 * 
	 * @param command
	 *            Whole command (for example keys *)
	 * @throws CommandParameterException
	 */
	void execute(String command) throws CommandParameterException;

	/**
	 * Command name (for example keys)
	 * 
	 * @return
	 */
	String[] getCommand();

	/**
	 * Command arguments (for example help keys)
	 * 
	 * @return
	 */
	String[] getArguments();

	/**
	 * Help how to use this command (for example keys &lt;select&gt;)
	 * 
	 * @return
	 */
	String[] getUsage();

	/**
	 * Expected parameters count (for example keys command expects two
	 * parameters - 1st parameter is command name "keys" and 2nd parameter is
	 * select)
	 * 
	 * @return
	 */
	int[] getParamCount();
}
