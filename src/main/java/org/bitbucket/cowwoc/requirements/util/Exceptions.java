/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

/**
 * Exception helper functions.
 *
 * @author Gili Tzabari
 */
public final class Exceptions
{
	private static final Lookup LOOKUP = MethodHandles.lookup();

	/**
	 * Throws an exception with the specified message and cause.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param cause   the cause of the exception
	 * @return the exception
	 */
	public static <E extends RuntimeException> RuntimeException createException(Class<E> type,
		String message, Throwable cause)
	{
		try
		{
			MethodType constructorType;
			if (cause != null)
				constructorType = MethodType.methodType(void.class, String.class, Throwable.class);
			else
				constructorType = MethodType.methodType(void.class, String.class);
			MethodHandle constructor = LOOKUP.findConstructor(type, constructorType);
			// Convert from the actual exception type to RuntimeException
			constructor = constructor.asType(constructor.type().changeReturnType(RuntimeException.class));

			RuntimeException result;
			if (cause == null)
				result = (RuntimeException) constructor.invokeExact(message);
			else
				result = (RuntimeException) constructor.invokeExact(message, cause);
			return result;
		}
		catch (Throwable e)
		{
			throw new AssertionError(e);
		}
	}

	/**
	 * Prevent construction.
	 */
	private Exceptions()
	{
	}
}
