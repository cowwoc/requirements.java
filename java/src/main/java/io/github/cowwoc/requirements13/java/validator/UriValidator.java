/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator;

import io.github.cowwoc.requirements13.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements13.java.validator.component.ValidatorComponent;

import java.net.URI;

/**
 * Validates the state of a {@link URI}.
 */
public interface UriValidator extends
	ValidatorComponent<UriValidator, URI>,
	ObjectComponent<UriValidator, URI>
{
	/**
	 * Ensures that the URI is absolute.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the URI is relative
	 */
	UriValidator isAbsolute();
}