/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

/**
 * A terminal that supports ANSI color codes.
 *
 * @author Gili Tzabari
 */
interface ColoredDiff
{
	/**
	 * @return the ANSI code to insert before padding
	 */
	String getColorForPadding();

	/**
	 * @return the ANSI code to insert before text that should be inserted
	 */
	String getColorForInsert();

	/**
	 * @return the ANSI code to insert before text that should be deleted
	 */
	String getColorForDelete();
}
