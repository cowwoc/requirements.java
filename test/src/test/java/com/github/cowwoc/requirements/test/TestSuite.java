/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test;

import com.github.cowwoc.requirements.java.GlobalRequirements;
import org.testng.annotations.BeforeSuite;

public final class TestSuite
{
	@BeforeSuite
	public void beforeSuite()
	{
		GlobalRequirements.withoutCleanStackTrace();
	}
}
