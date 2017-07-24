/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.terminal;

import java.util.Comparator;
import java.util.Set;
import static org.bitbucket.cowwoc.requirements.core.Requirements.requireThat;
import org.bitbucket.cowwoc.requirements.internal.core.diff.DiffWriter;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Rgb888Color;
import org.bitbucket.cowwoc.requirements.internal.core.diff.TextOnly;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Xterm16Color;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Xterm256Color;
import org.bitbucket.cowwoc.requirements.internal.core.diff.Xterm8Color;
import org.bitbucket.cowwoc.requirements.internal.core.scope.DefaultJvmScope;

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
	 * @deprecated replaced by {@code Terminal.getSupportedTypes().iterator().next()}
	 */
	@Deprecated
	public static TerminalEncoding detect()
	{
		Set<TerminalEncoding> supportedTypes = DefaultJvmScope.INSTANCE.getTerminal().
			getSupportedTypes();
		requireThat("Terminal.getSupportedTypes()", supportedTypes).isNotEmpty();
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
