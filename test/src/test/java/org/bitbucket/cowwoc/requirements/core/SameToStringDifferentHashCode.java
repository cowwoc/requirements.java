/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

/**
 * A class whose instances have the same toString() but are never equal.
 */
public final class SameToStringDifferentHashCode
{
	@Override
	public String toString()
	{
		return "SameToStringDifferentHashCode.toString()";
	}
}
