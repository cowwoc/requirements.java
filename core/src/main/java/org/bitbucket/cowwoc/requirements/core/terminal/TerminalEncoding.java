/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.terminal;

import org.bitbucket.cowwoc.requirements.internal.core.terminal.OperatingSystem;
import org.bitbucket.cowwoc.requirements.internal.core.diff.DiffWriter;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Rgb888Color;
import org.bitbucket.cowwoc.requirements.internal.core.diff.TextOnly;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Xterm16Color;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Xterm256Color;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Xterm8Color;
import static org.bitbucket.cowwoc.requirements.internal.core.terminal.OperatingSystem.Type.WINDOWS;
import org.bitbucket.cowwoc.requirements.internal.core.terminal.OperatingSystem.Version;

/**
 * The ANSI escape codes supported by the terminal.
 *
 * @author Gili Tzabari
 */
public enum TerminalEncoding
{
	/**
	 * Terminal does not support any colors.
	 */
	NONE
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new TextOnly(actual, expected);
		}
	},
	/**
	 * Terminal supports an 8-color palette.
	 */
	XTERM_8COLOR
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new Xterm8Color(actual, expected);
		}
	},
	/**
	 * Terminal supports a 16-color palette.
	 */
	XTERM_16COLOR
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new Xterm16Color(actual, expected);
		}
	},
	/**
	 * Terminal supports a 256-color palette.
	 */
	XTERM_256COLOR
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new Xterm256Color(actual, expected);
		}
	},
	/**
	 * Terminal supports a 24-bit color palette.
	 */
	RGB_888COLOR
	{
		@Override
		public DiffWriter diff(String actual, String expected)
		{
			return new Rgb888Color(actual, expected);
		}
	};

	/**
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return a diff writer that is appropriate for this encoding
	 * @throws NullPointerException if any of the arguments are null
	 */
	public abstract DiffWriter diff(String actual, String expected);

	/**
	 * @return the detected encoding
	 */
	public static TerminalEncoding detect()
	{
		OperatingSystem os = OperatingSystem.detect();
		if (os.type == WINDOWS)
		{
			// Windows 10.0.10586 added 16-bit color support:
			// http://www.nivot.org/blog/post/2016/02/04/Windows-10-TH2-%28v1511%29-Console-Host-Enhancements
			if (os.version.compareTo(new Version(10, 0, 10586)) == 0)
				return XTERM_16COLOR;
			// build 14931 added 24-bit color support:
			// https://blogs.msdn.microsoft.com/commandline/2016/09/22/24-bit-color-in-the-windows-console/
			if (os.version.compareTo(new Version(10, 0, 14931)) >= 0)
				return RGB_888COLOR;
			return NONE;
		}
		String term = System.getenv("TERM");
		if (term == null)
			return NONE;
		// Following the approach set out in http://stackoverflow.com/a/39033815/14731, we don't
		// attempt to support all possible terminal encodings. Instead, we support mainstream encodings
		// and require the terminal to support or emulate them.
		switch (term)
		{
			case "xterm":
			{
				// Used by older Linux deployments (e.g. routers)
				return XTERM_8COLOR;
			}
			case "xterm-256color":
			{
				// Used by Linux and OSX 10.9+
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
