/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.core.annotations.Beta;

/**
 * This convenience class constructs a {@link UnifiedVerifier} with the default configuration.
 * This class' assertion status determines whether {@code assertThat()} carries out a verification
 * or does nothing.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
@SuppressWarnings(
	{
		"AssertWithSideEffects", "NestedAssignment"
	})
public final class Requirements
{
	private static final UnifiedVerifier DELEGATE;

	static
	{
		boolean assertionsEnabled = false;
		assert (assertionsEnabled = true);
		DELEGATE = new UnifiedVerifier(assertionsEnabled);
	}

	/**
	 * @return true if assertions are enabled for this class
	 */
	@Beta
	public static boolean assertionsAreEnabled()
	{
		return DELEGATE.assertionsAreEnabled();
	}

	/**
	 * Verifies a {@code Multimap}.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Multimap, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Prevent construction.
	 */
	private Requirements()
	{
	}
}
