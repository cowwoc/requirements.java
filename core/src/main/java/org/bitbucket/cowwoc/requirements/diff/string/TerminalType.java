/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import static org.bitbucket.cowwoc.requirements.diff.string.OperatingSystem.Type.WINDOWS;

/**
 * Supported terminal types.
 *
 * @author Gili Tzabari
 */
public enum TerminalType
{
	NONE
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new TextOnly(actual, expected);
		}
	},
	XTERM
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new Xterm(actual, expected);
		}
	},
	XTERM_16COLOR
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new XTerm16Color(actual, expected);
		}
	},
	XTERM_256COLOR
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new XTerm256Color(actual, expected);
		}
	};

	/**
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return a diff writer
	 * @throws NullPointerException if any of the arguments are null
	 */
	public abstract DiffWriter diff(String actual, String expected);

	/**
	 * @return the terminal type supported by the console
	 */
	public static TerminalType detected()
	{
		OperatingSystem os = OperatingSystem.current();
		if (os.type == WINDOWS)
		{
			if (os.version.major > 10 || (os.version.major == 10 && os.version.build >= 10586))
			{
				// 24-bit color support introduced in https://blogs.msdn.microsoft.com/commandline/2016/09/22/24-bit-color-in-the-windows-console/
				if (os.version.build >= 14931)
					return XTERM_256COLOR;
				return XTERM_16COLOR;
			}
			return NONE;
		}
		String term = System.getenv("TERM");
		if (term == null)
			return NONE;
		// Following the approach set out in http://stackoverflow.com/a/39033815/14731, we don't
		// attempt to support all possible terminal types. Instead, we support the mainstream types
		// and require users to emulate them if the native terminal does not support it.
		switch (term)
		{
			case "xterm":
			{
				// Used by older Linux deployments (e.g. routers)
				return XTERM;
			}
			case "xterm-256color":
			{
				// Used by Linux, OSX 10.9+ and Windows 10+
				return XTERM_256COLOR;
			}
			default:
			{
				assert (false): "Unknown terminal: " + term;
				return NONE;
			}
		}
	}
}
