/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Process helper functions.
 */
final class Processes
{
	private static final String NEWLINE = System.lineSeparator();

	/**
	 * Prevent construction.
	 */
	private Processes()
	{
	}

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
		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), UTF_8)))
		{
			while (true)
			{
				String line = in.readLine();
				if (line == null)
					break;
				result.append(line).append(NEWLINE);
			}
		}
		return result.toString();
	}
}
