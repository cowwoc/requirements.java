/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.testng.annotations.BeforeSuite;

public final class TestSuite
{
	@BeforeSuite
	public void beforeSuite()
	{
		DefaultRequirements.getGlobalConfiguration().withoutLibraryRemovedFromStackTrace();
	}
}
