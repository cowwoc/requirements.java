/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.net.URI;
import java.util.function.Function;

/**
 * Validates the state of a {@link URI} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface UriValidator extends
	Validator<UriValidator>,
	ObjectPart<UriValidator, URI>
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