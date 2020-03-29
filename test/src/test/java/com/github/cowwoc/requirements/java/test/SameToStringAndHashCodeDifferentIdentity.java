/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.test;

/**
 * A class whose instances have the same toString() and hashCode() but are never equal.
 */
public final class SameToStringAndHashCodeDifferentIdentity
{
	@Override
	public String toString()
	{
		return "SameToStringAndHashCodeDifferentIdentity.toString()";
	}

	@Override
	public int hashCode()
	{
		return 0;
	}
}
