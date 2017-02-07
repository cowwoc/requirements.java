/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

/**
 * An interface that forwards all method invocations to a {@code GuavaVerifiers} instance.
 *
 * @author Gili Tzabari
 */
public interface ForwardingGuavaVerifiers extends GuavaVerifiers
{
	/**
	 * @return the {@code GuavaVerifiers} instance to forward calls to
	 */
	GuavaVerifiers guavaVerifiers();
}
