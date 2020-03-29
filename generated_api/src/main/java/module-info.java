/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * The {@code Requirements}, {@code DefaultRequirements} endpoints. The contents of these classes depend on
 * which plugins are enabled.
 */
module com.github.cowwoc.requirements
{
	requires com.github.cowwoc.requirements.java;
	requires com.github.cowwoc.requirements.guava;
	requires com.google.common;

	exports com.github.cowwoc.requirements;
}