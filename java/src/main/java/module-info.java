/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Classes used to verify
 * <a href="https://en.wikipedia.org/wiki/Precondition">preconditions</a>,
 * <a href="https://en.wikipedia.org/wiki/Postcondition">postconditions</a> and
 * <a href="https://en.wikipedia.org/wiki/Invariant_(mathematics)#Invariants_in_computer_science">invariants</a>
 * of core Java types.
 */
module org.bitbucket.cowwoc.requirements.java
{
	requires org.bitbucket.cowwoc.requirements.natives;
	requires org.bitbucket.cowwoc.diffmatchpatch;
	requires org.slf4j;
	requires org.bitbucket.cowwoc.pouch;

	exports org.bitbucket.cowwoc.requirements.java;
	exports org.bitbucket.cowwoc.requirements.java.annotations;
	exports org.bitbucket.cowwoc.requirements.java.capabilities;

	exports org.bitbucket.cowwoc.requirements.java.internal to org.bitbucket.cowwoc.requirements.guava,
		org.bitbucket.cowwoc.requirements.test, org.bitbucket.cowwoc.requirements.benchmark.java,
		org.bitbucket.cowwoc.requirements.benchmark.guava;
	exports org.bitbucket.cowwoc.requirements.java.internal.scope to org.bitbucket.cowwoc.requirements.guava,
		org.bitbucket.cowwoc.requirements.test, org.bitbucket.cowwoc.requirements.benchmark.java,
		org.bitbucket.cowwoc.requirements.benchmark.guava;
	exports org.bitbucket.cowwoc.requirements.java.internal.terminal to org.bitbucket.cowwoc.requirements.test;
	exports org.bitbucket.cowwoc.requirements.java.internal.diff to org.bitbucket.cowwoc.requirements.test;
	exports org.bitbucket.cowwoc.requirements.java.internal.secrets to org.bitbucket.cowwoc.requirements.guava;
	exports org.bitbucket.cowwoc.requirements.java.internal.util to org.bitbucket.cowwoc.requirements.guava,
		org.bitbucket.cowwoc.requirements.test;
}