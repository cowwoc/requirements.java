/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.annotations.Beta;
import java.util.Optional;

/**
 * The result of calculating the difference between two strings.
 *
 * @author Gili Tzabari
 */
@Beta
public final class DiffResult
{
	private final String actual;
	private final String expected;
	private final Optional<String> middle;

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual string
	 * @param expected the expected string
	 * @param middle   an optional line to display after "actual" and before "expected" (null if not present)
	 * @throws NullPointerException if {@code actual} or {@code expected} are null
	 */
	public DiffResult(String actual, String expected, String middle) throws NullPointerException
	{
		if (actual == null)
			throw new NullPointerException("actual may not be null");
		if (expected == null)
			throw new NullPointerException("expected may not be null");
		this.actual = actual;
		this.expected = expected;
		this.middle = Optional.ofNullable(middle);
	}

	/**
	 * @return the differences from {@code actual}'s perspective
	 */
	public String getActual()
	{
		return actual;
	}

	/**
	 * @return the differences from {@code expected}'s perspective
	 */
	public String getExpected()
	{
		return expected;
	}

	/**
	 * @return an optional line to display after "actual" and before "expected"
	 */
	public Optional<String> getMiddle()
	{
		return middle;
	}
}
