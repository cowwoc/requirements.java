package org.bitbucket.cowwoc.requirements.natives.terminal;

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
	 * Terminal supports an 8-color palette.
	 */
	XTERM_8COLOR,
	/**
	 * Terminal supports a 16-color palette.
	 */
	XTERM_16COLOR,
	/**
	 * Terminal supports a 256-color palette.
	 */
	XTERM_256COLOR,
	/**
	 * Terminal supports a 24-bit color palette.
	 */
	RGB_888COLOR;

	/**
	 * @return a comparator that sorts encodings based on their ranking, from most-desirable to least-desirable
	 */
	public static Comparator<TerminalEncoding> sortByDecreasingRank()
	{
		return Comparator.reverseOrder();
	}
}
