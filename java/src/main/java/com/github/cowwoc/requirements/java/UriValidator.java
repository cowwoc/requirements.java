/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.net.URI;
import java.util.function.Consumer;

/**
 * Validates the requirements of a {@link URI}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 */
public interface UriValidator extends ExtensibleObjectValidator<UriValidator, URI>
{
	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return the updated validator
	 */
	UriValidator isAbsolute();

	/**
	 * Returns a validator for the URL representation of the value.
	 *
	 * @return a validator for the URL representation of the value
	 */
	UrlValidator asUrl();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asUrl()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates URLs
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	UriValidator asUrl(Consumer<UrlValidator> consumer);
}