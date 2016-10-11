/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.time.Instant;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class ComparableRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Instant parameter = Instant.now();
		Requirements.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Instant parameter = Instant.now();
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void isLessThanVariable()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(before, "before").isLessThan(after, "after");
	}

	@Test
	public void isLessThanConstant()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(before, "before").isLessThan(after);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsEqual()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isLessThan(instant, "instant");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsEqual()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isLessThan(instant);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsBefore()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(after, "after").isLessThan(before, "before");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsBefore()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(after, "after").isLessThan(before);
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isLessThanOrEqualTo(instant, "instant");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isLessThanOrEqualTo(instant);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_expectedIsBefore()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(after, "after").isLessThanOrEqualTo(before, "before");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_expectedIsAfter()
	{
		Instant before = Instant.now();
		Instant after = before.minusMillis(1);
		Requirements.requireThat(before, "before").isLessThanOrEqualTo(after);
	}

	@Test
	public void isGreaterThanVariable()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(after, "after").isGreaterThan(before, "before");
	}

	@Test
	public void isGreaterThanConstant()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(after, "after").isGreaterThan(before);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsEqual()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isGreaterThan(instant, "instant");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsEqual()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isGreaterThan(instant);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsBefore()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(before, "before").isGreaterThan(after, "after");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsBefore()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(before, "before").isGreaterThan(after);
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isGreaterThanOrEqualTo(instant, "instant");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		Instant instant = Instant.now();
		Requirements.requireThat(instant, "instant").isGreaterThanOrEqualTo(instant);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedIsBefore()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(before, "before").isGreaterThanOrEqualTo(after, "after");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_expectedIsBefore()
	{
		Instant before = Instant.now();
		Instant after = before.plusMillis(1);
		Requirements.requireThat(before, "before").isGreaterThanOrEqualTo(after);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Instant parameter = null;
		new AssertionVerifier(false).requireThat(parameter, "parameter").isNotNull();
	}
}
