/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.guava.impl.GuavaVerifiersImpl;

/**
 * An entry point for verifying API requirements.
 * <p>
 * The assertion status of the {@link Configuration} class determines whether {@code assertThat()}
 * carries out a verification or does nothing.
 * <p>
 * This class is immutable.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 * @see GuavaVerifiers
 */
@SuppressWarnings(
	{
		"AssertWithSideEffects", "NestedAssignment"
	})
public final class Requirements
{
	private static final GuavaVerifiers DELEGATE = new GuavaVerifiersImpl();

	/**
	 * @return true if assertions are enabled for this class
	 */
	public static boolean assertionsAreEnabled()
	{
		return DELEGATE.assertionsAreEnabled();
	}

	/**
	 * Verifies a {@code Multimap}.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param name   the name of the parameter
	 * @param actual the actual value of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MultimapVerifier<K, V> requireThat(String name, Multimap<K, V> actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(Multimap, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param name   the name of the parameter
	 * @param actual the actual value of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MultimapVerifier<K, V> assertThat(String name, Multimap<K, V> actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Prevent construction.
	 */
	private Requirements()
	{
	}
}
