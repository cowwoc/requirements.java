/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.net.URL;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link URL}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 */
public interface UrlVerifier extends ExtensibleObjectVerifier<UrlVerifier, URL>
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
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asUri()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer verifies URIs
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	UrlVerifier asUri(Consumer<UriVerifier> consumer);
}
