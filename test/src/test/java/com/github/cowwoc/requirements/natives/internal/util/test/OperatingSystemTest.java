/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.natives.internal.util.test;

import com.github.cowwoc.requirements.natives.internal.util.OperatingSystem.Version;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;

public final class OperatingSystemTest
{
	@Test
	public void parseVersion()
	{
		Version actual = Version.parseVersion("1.2.3");
		Version expected = new Version(1, 2, 3);
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void parseWindowsVersion()
	{
		Version actual = Version.parseWindowsVersion("Microsoft Windows [Version 10.0.16299.125]");
		Version expected = new Version(10, 0, 16299, 125);
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void majorVersion()
	{
		Version actual = Version.parseVersion("1");
		Version expected = new Version(1);
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void majorMinorVersion()
	{
		Version actual = Version.parseVersion("1.2");
		Version expected = new Version(1, 2);
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void majorMinorBuildVersion()
	{
		Version actual = Version.parseVersion("1.2.3");
		Version expected = new Version(1, 2, 3);
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void majorMinorBuildRevisionVersion()
	{
		Version actual = Version.parseVersion("1.2.3.4");
		Version expected = new Version(1, 2, 3, 4);
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void missingBuildComesFirst()
	{
		Version first = Version.parseVersion("1.2");
		Version second = new Version(1, 2, 0);
		requireThat(first, "first").isLessThan(second, "second");
	}

	@Test
	public void missingMinorComesFirst()
	{
		Version first = Version.parseVersion("1");
		Version second = new Version(1, 0);
		requireThat(first, "first").isLessThan(second, "second");
	}

	@Test
	public void missingRevisionComesFirst()
	{
		Version first = Version.parseVersion("1.0.0");
		Version second = new Version(1, 0, 0, 0);
		requireThat(first, "first").isLessThan(second, "second");
	}

	@Test
	public void toStringMissingMinor()
	{
		String expected = "1";
		String actual = Version.parseVersion(expected).toString();
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void toStringMissingBuild()
	{
		String expected = "1.0";
		String actual = Version.parseVersion(expected).toString();
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}

	@Test
	public void toStringMissingRevision()
	{
		String expected = "1.0.0";
		String actual = Version.parseVersion(expected).toString();
		requireThat(actual, "actual").isEqualTo(expected, "expected");
	}
}
