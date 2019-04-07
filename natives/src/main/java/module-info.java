/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Optional features that require access to native libraries, such as the use of ANSI colors.
 */
module org.bitbucket.cowwoc.requirements.natives {
	requires org.bitbucket.cowwoc.pouch;
	requires org.bitbucket.cowwoc.requirements.annotations;

	exports org.bitbucket.cowwoc.requirements.natives.internal.util to org.bitbucket.cowwoc.requirements.java,
		org.bitbucket.cowwoc.requirements.maven_plugin, org.bitbucket.cowwoc.requirements.test;
	exports org.bitbucket.cowwoc.requirements.natives.internal.terminal to
		org.bitbucket.cowwoc.requirements.java, org.bitbucket.cowwoc.requirements.test;

	exports org.bitbucket.cowwoc.requirements.natives.terminal;
}