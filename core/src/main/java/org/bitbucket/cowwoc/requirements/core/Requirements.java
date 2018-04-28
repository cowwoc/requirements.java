/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.bitbucket.cowwoc.requirements.internal.core.impl.CoreVerifiersImpl;
import org.bitbucket.cowwoc.requirements.internal.core.scope.DefaultJvmScope;

/**
 * An entry point for verifying API requirements.
 * <p>
 * The assertion status of the {@link Configuration} class determines whether {@code assertThat()}
 * carries out a verification or does nothing.
 * <p>
 * This class is immutable.
 *
 * @author Gili Tzabari
 * @see CoreVerifiers
 */
@SuppressWarnings(
	{
		"AssertWithSideEffects", "NestedAssignment"
	})
public final class Requirements
{
	private static final CoreVerifiersImpl DELEGATE = new CoreVerifiersImpl();

	/**
	 * @return true if assertions are enabled for this class
	 */
	public static boolean assertionsAreEnabled()
	{
		return DELEGATE.assertionsAreEnabled();
	}

	/**
	 * Verifies an {@code Object}.
	 *
	 * @param <T>    the type of the value
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ObjectVerifier<T> requireThat(String name, T actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Object)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of the value
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ObjectVerifier<T> assertThat(String name, T actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Collection}.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <C extends Collection<E>, E> CollectionVerifier<C, E>
		requireThat(String name, C actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Collection)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <C extends Collection<E>, E> CollectionVerifier<C, E>
		assertThat(String name, C actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code byte} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveByteArrayVerifier requireThat(String name, byte[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, byte[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveByteArrayVerifier assertThat(String name, byte[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code short} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveShortArrayVerifier requireThat(String name, short[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, short[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveShortArrayVerifier assertThat(String name, short[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code int} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveIntegerArrayVerifier requireThat(String name, int[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, int[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveIntegerArrayVerifier assertThat(String name, int[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code long} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveLongArrayVerifier requireThat(String name, long[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, long[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveLongArrayVerifier assertThat(String name, long[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code float} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveFloatArrayVerifier requireThat(String name, float[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, float[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveFloatArrayVerifier assertThat(String name, float[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code double} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveDoubleArrayVerifier requireThat(String name, double[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, double[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveDoubleArrayVerifier assertThat(String name, double[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code boolean} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveBooleanArrayVerifier requireThat(String name, boolean[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, boolean[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveBooleanArrayVerifier assertThat(String name, boolean[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a primitive {@code char} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveCharacterArrayVerifier requireThat(String name, char[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, char[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PrimitiveCharacterArrayVerifier assertThat(String name, char[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies an array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> ArrayVerifier<E> requireThat(String name, E[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Object[])} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <E>    the type of elements in the array
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> ArrayVerifier<E> assertThat(String name, E[] actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the value may be compared to
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(String name,
		T actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Comparable)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <T>    the type of objects that the value may be compared to
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(String name,
		T actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code byte}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveNumberVerifier<Byte> requireThat(String name, byte actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, byte)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveNumberVerifier<Byte> assertThat(String name, byte actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code short}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveNumberVerifier<Short> requireThat(String name, short actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, short)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveNumberVerifier<Short> assertThat(String name, short actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies an {@code int}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveIntegerVerifier<Integer> requireThat(String name, int actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, int)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveIntegerVerifier<Integer> assertThat(String name, int actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies an {@code Integer}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static IntegerVerifier<Integer> requireThat(String name, Integer actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Integer)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static IntegerVerifier<Integer> assertThat(String name, Integer actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code long}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveIntegerVerifier<Long> requireThat(String name, long actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, long)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveIntegerVerifier<Long> assertThat(String name, long actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Long}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static IntegerVerifier<Long> requireThat(String name, Long actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Long)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static IntegerVerifier<Long> assertThat(String name, Long actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies an {@code float}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveFloatingPointVerifier<Float> requireThat(String name, float actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, float)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveFloatingPointVerifier<Float> assertThat(String name, float actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code double}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveFloatingPointVerifier<Double> requireThat(String name, double actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, double)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveFloatingPointVerifier<Double> assertThat(String name, double actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code boolean}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveBooleanVerifier requireThat(String name, boolean actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, boolean)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveBooleanVerifier assertThat(String name, boolean actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, char)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveCharacterVerifier assertThat(String name, char actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code char}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PrimitiveCharacterVerifier requireThat(String name, char actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Verifies a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(
		String name, T actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Number)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of the number
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(
		String name, T actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Boolean}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BooleanVerifier requireThat(String name, Boolean actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Boolean)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BooleanVerifier assertThat(String name, Boolean actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Float}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static FloatingPointVerifier<Float> requireThat(String name, Float actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Float)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static FloatingPointVerifier<Float> assertThat(String name, Float actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Double}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static FloatingPointVerifier<Double> requireThat(String name, Double actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Double)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static FloatingPointVerifier<Double> assertThat(String name, Double actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code BigDecimal}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BigDecimalVerifier requireThat(String name, BigDecimal actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, BigDecimal)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BigDecimalVerifier assertThat(String name, BigDecimal actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MapVerifier<K, V> requireThat(String name, Map<K, V> actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Map)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MapVerifier<K, V> assertThat(String name, Map<K, V> actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Path}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PathVerifier requireThat(String name, Path actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Path)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PathVerifier assertThat(String name, Path actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code String}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static StringVerifier requireThat(String name, String actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static StringVerifier assertThat(String name, String actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Uri}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static UriVerifier requireThat(String name, URI actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, URI)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static UriVerifier assertThat(String name, URI actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ClassVerifier<T> requireThat(String name, Class<T> actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Class)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of class
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ClassVerifier<T> assertThat(String name, Class<T> actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies an {@code Optional}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static OptionalVerifier requireThat(String name, Optional<?> actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, Optional)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static OptionalVerifier assertThat(String name, Optional<?> actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * Verifies an {@code InetAddress}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static InetAddressVerifier requireThat(String name, InetAddress actual)
	{
		return DELEGATE.requireThat(name, actual);
	}

	/**
	 * Same as {@link #requireThat(String, InetAddress)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static InetAddressVerifier assertThat(String name, InetAddress actual)
	{
		return DELEGATE.assertThat(name, actual);
	}

	/**
	 * @return the global configuration shared by all verifiers
	 */
	public static GlobalConfiguration globalConfiguration()
	{
		return DefaultJvmScope.INSTANCE.getGlobalConfiguration();
	}

	/**
	 * Prevent construction.
	 */
	private Requirements()
	{
	}
	// TODO: Rethink Object.isIn() because it leads to Collection.isIn(Collection<Collection<E>>).
	// Ideally we want, Collection.contains(E) but the message needs to say
	// "E must be in Collection" as opposed to "Collection must contain E"
}
