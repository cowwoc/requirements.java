/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test.java;

/**
 * A class whose instances have the same multiline toString() but are never equal.
 */
public final class SameMultilineWithDifferentHashCode
{
	@Override
	public String toString()
	{
		return """
			line1
			line2
			line3
			SameMultilineWithDifferentHashCode.toString()""";
	}
}