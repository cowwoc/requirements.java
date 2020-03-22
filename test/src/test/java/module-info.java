/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
module org.bitbucket.cowwoc.requirements.test
{
	requires com.google.common;
	requires org.testng;
	requires org.bitbucket.cowwoc.requirements.natives;
	requires org.bitbucket.cowwoc.requirements.guava;

	exports org.bitbucket.cowwoc.requirements.java.internal.diff.test to org.testng;
}