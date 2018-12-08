/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Process helper functions.
 */
final class Processes
{
	private static final String NEWLINE = System.getProperty("line.separator");

	/**
	 * @param command the command to run
	 * @return the output of the command
	 * @throws IOException if an I/O error occurs
	 */
	public static String run(String... command) throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
		Process process = pb.start();
		StringBuilder result = new StringBuilder(80);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream())))
		{
			boolean first = true;
			while (true)
			{
				String line = in.readLine();
				if (line == null)
					break;
				if (first)
					first = false;
				else
					result.append(NEWLINE);
				result.append(line);
			}
		}
		return result.toString();
	}

	/**
	 * Prevent construction.
	 */
	private Processes()
	{
	}
}
