/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.annotations.Beta;
import java.io.Console;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import org.bitbucket.cowwoc.requirements.util.Strings;

/**
 * Generates a diff of two Strings.
 *
 * @author Gili Tzabari
 */
@Beta
public final class DiffGenerator
{
	/**
	 * Extracts the Windows version number from the output of the "ver" command.
	 */
	private static final Pattern WINDOWS_VERSION = Pattern.compile(
		"Microsoft Windows \\[Version (\\d+)\\.(\\d+)\\.(\\d+)\\]");

	/**
	 * The terminal type.
	 */
	private final TerminalType terminal;

	/**
	 * Creates a new instance.
	 */
	public DiffGenerator()
	{
		this.terminal = getTerminalType();
	}

	/**
	 * Generates the diff of two strings.
	 * <p>
	 * NOTE: Colors are disabled when stdin or stdout are redirected. To override this behavior, set
	 * the system property "org.bitbucket.cowwoc.requirements.forceAnsi" to "true".
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return a writer
	 * @throws NullPointerException if any of the arguments are null
	 */
	public DiffResult diff(String actual, String expected) throws NullPointerException
	{
		DiffMatchPatch diffEngine = new DiffMatchPatch();
		LinkedList<Diff> components = diffEngine.diffMain(actual, expected);
		diffEngine.diffCleanupSemantic(components);

		DiffWriter writer = terminal.diff(actual, expected);
		for (Diff component: components)
		{
			switch (component.operation)
			{
				case EQUAL:
				{
					writer.unchanged(component.text);
					break;
				}
				case INSERT:
				{
					writer.inserted(component.text);
					break;
				}
				case DELETE:
				{
					writer.deleted(component.text);
					break;
				}
				default:
					throw new AssertionError(component.operation.name());
			}
		}
		return new DiffResult(writer.getActual(), writer.getExpected(), writer.getMiddle().orElse(null));
	}

	/**
	 * @return the terminal type supported by the system
	 */
	private TerminalType getTerminalType()
	{
		String force = System.getProperty("org.bitbucket.cowwoc.requirements.terminal");
		if (force != null)
			return TerminalType.valueOf(force);
		// System.console() returns null when either stdin or stdout are redirected. This doesn't
		// necessarily mean that ANSI colors aren't supported, but we choose to err on the side of
		// caution. Users can override this behavior by setting the system property
		// "org.bitbucket.cowwoc.requirements.terminal" to any value found in TerminalType.
		Console console = System.console();
		if (console == null)
			return TerminalType.NONE;

		String osName = System.getProperty("os.name");
		if (Strings.startsWith(osName, "windows", true))
		{
			String version;
			try
			{
				version = Processes.run("cmd.exe", "/c", "ver");
			}
			catch (IOException e)
			{
				assert (false): e;
				return TerminalType.NONE;
			}
			Matcher matcher = WINDOWS_VERSION.matcher(version);
			if (!matcher.find())
			{
				assert (false): "Unexpected version: " + version;
				return TerminalType.NONE;
			}
			int major;
			int build;
			try
			{
				major = Integer.parseInt(matcher.group(1));
				build = Integer.parseInt(matcher.group(3));
			}
			catch (NumberFormatException e)
			{
				assert (false): e;
				return TerminalType.NONE;
			}
			if (major > 10 || (major == 10 && build >= 10586))
				return TerminalType.XTERM_16COLOR;
			return TerminalType.NONE;
		}
		String term = System.getenv("TERM");
		if (term == null)
			return TerminalType.NONE;
		// * See http://tldp.org/HOWTO/Text-Terminal-HOWTO-16.html for an overview of terminfo.
		// * Run "toe -a" or see http://invisible-island.net/ncurses/terminfo.src.html for a list of
		// possible terminal types.
		// * Run "infocmp <type>" to list the capabilities of a specific terminal type.
		// * Following the approach set out in http://stackoverflow.com/a/39033815/14731, we don't
		// attempt to support all possible terminal types. Instead, we support the mainstream types
		// and require users to emulate them if the native terminal does not support it.
		switch (term)
		{
			case "xterm":
			{
				// Used by older Linux deployments (e.g. routers)
				return TerminalType.XTERM;
			}
			case "xterm-256color":
			{
				// Used by OSX 10.9
				return TerminalType.XTERM_256COLOR;
			}
		}
		assert (false): "Unknown terminal: " + term;
		return TerminalType.NONE;
	}

	/**
	 * @param propertyName the name of a system property
	 * @return null if the property is not set or is set to a non-boolean value; otherwise, returns
	 *         the boolean value
	 */
	private Boolean getBoolean(String propertyName)
	{
		String value = System.getProperty(DiffGenerator.class.getName() + ".enabled");
		if (value == null)
			return null;
		if (value.equalsIgnoreCase("true"))
			return true;
		if (value.equalsIgnoreCase("false"))
			return false;
		return null;
	}
}
