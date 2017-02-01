/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.function.Consumer;

/**
 * A verifier whose behavior is configurable.
 *
 * @param <S> the type of the verifier
 * @author Gili Tzabari
 */
public interface Configurable<S>
{
	/**
	 * Returns the verifier's configuration.
	 * <p>
	 * Modifying the configuration affect the behavior of this verifier, the verifier that created it,
	 * and any verifiers created by it.
	 *
	 * @return the verifier's configuration
	 */
	Configuration configuration();

	/**
	 * Returns the verifier's configuration.
	 * <p>
	 * Modifying the configuration affect the behavior of this verifier, the verifier that created it,
	 * and any verifiers created by it.
	 *
	 * @param consumer consumes the verifier's configuration
	 * @return this
	 */
	S configuration(Consumer<Configuration> consumer);
}
