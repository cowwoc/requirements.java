/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.ObjectCapabilities;

import java.net.URI;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link URI}.
 */
public interface UriVerifier extends ObjectCapabilities<UriVerifier, URI>
{
	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return this
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
	 * <a
	 * href="https://bitbucket.org/cowwoc/requirements/wiki/Home#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies URLs
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain a valid URL format
	 */
	UriVerifier asUrl(Consumer<UrlVerifier> consumer);
}
