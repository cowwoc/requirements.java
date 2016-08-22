/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

/**
 * Supported terminal types.
 *
 * @author Gili Tzabari
 */
enum TerminalType
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
			return new Xterm16Color(actual, expected);
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
}
