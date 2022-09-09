/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.natives.internal.util;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements.natives.internal.util.OperatingSystem.Version;
import org.testng.annotations.Test;

public final class OperatingSystemTest
{
	@Test
	public void parseVersion()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version actual = Version.parseVersion("1.2.3");
			Version expected = new Version(1, 2, 3);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void parseWindowsVersion()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version actual = Version.parseWindowsVersion("Microsoft Windows [Version 10.0.16299.125]");
			Version expected = new Version(10, 0, 16299, 125);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void majorVersion()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version actual = Version.parseVersion("1");
			Version expected = new Version(1);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void majorMinorVersion()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version actual = Version.parseVersion("1.2");
			Version expected = new Version(1, 2);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void majorMinorBuildVersion()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version actual = Version.parseVersion("1.2.3");
			Version expected = new Version(1, 2, 3);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void majorMinorBuildRevisionVersion()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version actual = Version.parseVersion("1.2.3.4");
			Version expected = new Version(1, 2, 3, 4);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void missingBuildComesFirst()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version first = Version.parseVersion("1.2");
			Version second = new Version(1, 2, 0);
			new Requirements(scope).requireThat(first, "first").isLessThan(second, "second");
		}
	}

	@Test
	public void missingMinorComesFirst()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version first = Version.parseVersion("1");
			Version second = new Version(1, 0);
			new Requirements(scope).requireThat(first, "first").isLessThan(second, "second");
		}
	}

	@Test
	public void missingRevisionComesFirst()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Version first = Version.parseVersion("1.0.0");
			Version second = new Version(1, 0, 0, 0);
			new Requirements(scope).requireThat(first, "first").isLessThan(second, "second");
		}
	}

	@Test
	public void toStringMissingMinor()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			String expected = "1";
			String actual = Version.parseVersion(expected).toString();
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void toStringMissingBuild()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			String expected = "1.0";
			String actual = Version.parseVersion(expected).toString();
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void toStringMissingRevision()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			String expected = "1.0.0";
			String actual = Version.parseVersion(expected).toString();
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}
}