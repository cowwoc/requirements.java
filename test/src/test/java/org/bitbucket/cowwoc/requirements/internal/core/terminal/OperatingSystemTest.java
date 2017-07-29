/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.terminal;

import static org.bitbucket.cowwoc.requirements.core.Requirements.requireThat;
import org.bitbucket.cowwoc.requirements.internal.core.terminal.OperatingSystem.Version;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class OperatingSystemTest
{
	@Test
	public void parseVersion()
	{
		Version actual = Version.getVersionUsingJava("1.2.3");
		Version expected = new Version(1, 2, 3);
		requireThat("actual", actual).isEqualTo("expected", expected);
	}

	@Test
	public void majorMinorVersion()
	{
		Version actual = Version.getVersionUsingJava("1.2");
		Version expected = new Version(1, 2);
		requireThat("actual", actual).isEqualTo("expected", expected);
	}

	@Test
	public void majorVersion()
	{
		Version actual = Version.getVersionUsingJava("1");
		Version expected = new Version(1);
		requireThat("actual", actual).isEqualTo("expected", expected);
	}

	@Test
	public void missingBuildIsZero()
	{
		Version actual = Version.getVersionUsingJava("1.2");
		Version expected = new Version(1, 2, 0);
		requireThat("actual", actual).isEqualTo("expected", expected);
	}

	@Test
	public void missingMinorIsZero()
	{
		Version actual = Version.getVersionUsingJava("1");
		Version expected = new Version(1, 0);
		requireThat("actual", actual).isEqualTo("expected", expected);
	}

	@Test
	public void toStringMissingMinor()
	{
		String expected = "1";
		String actual = Version.getVersionUsingJava(expected).toString();
		requireThat("actual", actual).isEqualTo("expected", expected);
	}

	@Test
	public void toStringMissingBuild()
	{
		String expected = "1.0";
		String actual = Version.getVersionUsingJava(expected).toString();
		requireThat("actual", actual).isEqualTo("expected", expected);
	}
}
