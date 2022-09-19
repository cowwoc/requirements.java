/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.natives.terminal;

import java.util.Comparator;

/**
 * The ANSI escape codes supported by the terminal.
 */
public enum TerminalEncoding
{
	/**
	 * Terminal does not support any colors.
	 */
	NONE,
	/**
	 * Terminal supports 8 colors.
	 */
	XTERM_8_COLORS,
	/**
	 * Terminal supports 16 colors.
	 */
	XTERM_16_COLORS,
	/**
	 * Terminal supports 256 colors.
	 */
	XTERM_256_COLORS,
	/**
	 * Terminal supports 16 millions.
	 */
	RGB_888_COLORS;

	/**
	 * Returns a comparator that sorts encodings based on the number of colors that they support, from the most
	 * to the least number of colors.
	 *
	 * @return a comparator that sorts encodings based on the number of colors that they support, from the most
	 * to the least number of colors
	 */
	public static Comparator<TerminalEncoding> sortByDecreasingRank()
	{
		return Comparator.reverseOrder();
	}
}