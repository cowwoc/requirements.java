/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Optional features that require access to native libraries, such as the use of ANSI colors.
 */
module com.github.cowwoc.requirements.natives
{
	requires com.github.cowwoc.pouch.core;

	exports com.github.cowwoc.requirements.natives.internal.util to com.github.cowwoc.requirements.java,
		com.github.cowwoc.requirements.maven_plugin, com.github.cowwoc.requirements.test;
	exports com.github.cowwoc.requirements.natives.internal.terminal to
		com.github.cowwoc.requirements.java, com.github.cowwoc.requirements.test;

	exports com.github.cowwoc.requirements.natives.terminal;
}