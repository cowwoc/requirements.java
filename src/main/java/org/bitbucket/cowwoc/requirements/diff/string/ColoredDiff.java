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
interface ColoredDiff extends DiffWriter
{
	/**
	 * @return the ANSI code to insert before neutral text
	 */
	String getColorForNeutral();

	/**
	 * @return the ANSI code to insert before inserted text
	 */
	String getColorForInsert();

	/**
	 * @return the ANSI code to insert before deleted text
	 */
	String getColorForDelete();
}
