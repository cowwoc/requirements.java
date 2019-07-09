/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.net.URI;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link URI}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 */
public interface UriVerifier extends ExtensibleObjectVerifier<UriVerifier, URI>
{
	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is not absolute
	 */
	UriVerifier isAbsolute();

	/**
	 * Returns a verifier for the URL representation of the value.
	 *
	 * @return a verifier for the URL representation of the value
	 * @throws IllegalArgumentException if the actual value cannot be converted to a URL
	 */
	UrlVerifier asUrl();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asUrl()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer verifies URLs
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	UriVerifier asUrl(Consumer<UrlVerifier> consumer);
}
