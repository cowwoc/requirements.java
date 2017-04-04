/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.URL;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;

/**
 * Verifies a {@link URL}.
 *
 * @author Gili Tzabari
 */
public interface UrlVerifier extends ObjectCapabilities<UrlVerifier, URL>
{
	/**
	 * Returns a verifier for the URI representation of the value.
	 * <p>
	 * Technically-speaking, there is no such thing as an invalid URI format. Per
	 * <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC3986</a>, any String can be
	 * represented as a relative URI, but Java's implementation is based on an
	 * <a href="https://tools.ietf.org/html/rfc2396">older specification</a> where this was not the
	 * case.
	 *
	 * @return a verifier for the URI representation of the value
	 * @throws IllegalArgumentException if the actual value cannot be converted to a URI
	 * @see <a href="http://stackoverflow.com/a/27644491/14731">Discussion of Java URI vs RFC3986</a>
	 */
	UriVerifier asUri();

	/**
	 * Ensures that the actual value contains a valid URI format.
	 *
	 * @param consumer verifies URIs
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain a valid URI format
	 */
	UrlVerifier asUri(Consumer<UriVerifier> consumer);
}
