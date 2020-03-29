/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util.test;

import com.github.cowwoc.requirements.java.internal.util.Strings;
import org.testng.annotations.Test;

public final class StringsTest
{
	@Test
	public void lastConsecutiveIndexOf()
	{
		int result = Strings.lastConsecutiveIndexOf("textex", " ");
		assert (result == -1) : result;

		result = Strings.lastConsecutiveIndexOf("  text", " ");
		assert (result == -1) : result;

		result = Strings.lastConsecutiveIndexOf("text  ", " ");
		assert (result == 4) : result;

		result = Strings.lastConsecutiveIndexOf("      ", " ");
		assert (result == 0) : result;
	}

	/**
	 * BUG: If the length of target is less than or equal to the return value, the method would
	 * return zero (which is incorrect).
	 */
	@Test
	public void lastConsecutiveIndexOf_resultLessThanOrEqualToLengthOfTarget()
	{
		int result = Strings.lastConsecutiveIndexOf("1 ", " ");
		assert (result == 1) : result;
	}
}
