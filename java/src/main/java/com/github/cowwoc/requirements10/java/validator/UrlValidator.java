/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.net.URL;

/**
 * Validates the state of a {@link URL}.
 */
public interface UrlValidator extends
	ValidatorComponent<UrlValidator, URL>,
	ObjectComponent<UrlValidator, URL>
{
	/**
	 * Ensures that the URL is a valid URI.
	 * <p>
	 * Technically-speaking, there is no such thing as an invalid URI format. Per
	 * <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC3986</a>, any String can be
	 * represented as a relative URI, but Java's implementation is based on an
	 * <a href="https://tools.ietf.org/html/rfc2396">older specification</a> where this was not the
	 * case.
	 *
	 * @return a validator for the URI representation of the URL
	 * @throws NullPointerException if the value is null
	 * @see <a href="http://stackoverflow.com/a/27644491/14731">Discussion of Java URI vs RFC3986</a>
	 */
	UriValidator asUri();
}