/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/**
 * Verifies preconditions of a Long parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class LongPreconditions extends NumberPreconditions<LongPreconditions, Long>
{
	/**
	 * Creates new LongPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	LongPreconditions(Long parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is within range.
	 * <p/>
	 * @param range the range
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	public LongPreconditions isIn(Range<Long> range) throws IllegalArgumentException
	{
		return isIn(range, DiscreteDomain.longs());
	}
}
