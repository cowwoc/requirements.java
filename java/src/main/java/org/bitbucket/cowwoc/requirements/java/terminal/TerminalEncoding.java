/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.terminal;

import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffWriter;
import org.bitbucket.cowwoc.requirements.java.internal.diff.Rgb888Color;
import org.bitbucket.cowwoc.requirements.java.internal.diff.TextOnly;
import org.bitbucket.cowwoc.requirements.java.internal.diff.Xterm16Color;
import org.bitbucket.cowwoc.requirements.java.internal.diff.Xterm256Color;
import org.bitbucket.cowwoc.requirements.java.internal.diff.Xterm8Color;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SharedSecrets;
import org.bitbucket.cowwoc.requirements.java.internal.scope.DefaultJvmScope;

import java.util.Comparator;
import java.util.Set;

/**
 * The ANSI escape codes supported by the terminal.
 */
public enum TerminalEncoding
{
	/**
	 * Terminal does not support any colors.
	 */
	NONE
	{
		@Override
		DiffWriter diff(String actual, String expected)
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
		DiffWriter diff(String actual, String expected)
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
		DiffWriter diff(String actual, String expected)
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
		DiffWriter diff(String actual, String expected)
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
		DiffWriter diff(String actual, String expected)
		{
			return new Rgb888Color(actual, expected);
		}
	};

	static
	{
		SharedSecrets.INSTANCE.secretTerminalEncoding = (encoding, actual, expected) -> encoding.diff(actual, expected);
	}

	/**
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return a diff writer that is appropriate for this encoding
	 * @throws NullPointerException if any of the arguments are null
	 */
	abstract DiffWriter diff(String actual, String expected);

	/**
	 * @return the detected encoding
	 * @deprecated replaced by {@code Terminal.getSupportedTypes().iterator().next()}
	 */
	@Deprecated
	public static TerminalEncoding detect()
	{
		Set<TerminalEncoding> supportedTypes = DefaultJvmScope.INSTANCE.getTerminal().
			getSupportedTypes();
		if (supportedTypes.isEmpty())
			throw new IllegalArgumentException("Terminal.getSupportedTypes() may not be empty");
		return supportedTypes.iterator().next();
	}

	/**
	 * @return a comparator that sorts encodings based on their ranking, from most-desirable to
	 *         least-desirable
	 */
	public static Comparator<TerminalEncoding> sortByDecreasingRank()
	{
		return Comparator.reverseOrder();
	}
}
