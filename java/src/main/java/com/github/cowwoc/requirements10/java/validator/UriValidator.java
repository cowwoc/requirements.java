/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

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

	/**
	 * Ensures that the URI is a valid URL.
	 *
	 * @return a validator for the URL representation of the URI
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the URI cannot be converted to a URL
	 */
	UrlValidator asUrl();
}