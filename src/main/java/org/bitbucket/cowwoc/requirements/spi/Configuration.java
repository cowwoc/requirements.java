/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.StringJoiner;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Determines the behavior of a requirements verifier.
 *
 * @author Gili Tzabari
 */
public final class Configuration
{
	/**
	 * Creates an exception message.
	 *
	 * @param message an explanation of what went wrong
	 * @param context contextual information associated with the failure (key-value pairs)
	 * @return the exception message
	 */
	private static String messageWithContext(String message, Map<String, Object> context)
	{
		StringJoiner result = new StringJoiner("\n");
		result.add(message);
		int maxKeyLength = context.keySet().stream().mapToInt(String::length).max().orElse(0);
		for (Entry<String, Object> entry: context.entrySet())
			result.add(String.format("%-" + maxKeyLength + "s: %s", entry.getKey(), entry.getValue()));
		return result.toString();
	}
	private final Class<? extends RuntimeException> exceptionOverride;

	/**
	 * Creates a new instance.
	 *
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 */
	public Configuration(Class<? extends RuntimeException> exceptionOverride)
	{
		this.exceptionOverride = exceptionOverride;
	}

	/**
	 * Overrides the type of exception that will get thrown if a requirement fails.
	 * <p>
	 * @param exception the type of exception to throw, null to disable the override
	 * @return this
	 */
	public Configuration withException(Class<? extends RuntimeException> exception)
	{
		if (Objects.equals(exception, exceptionOverride))
			return this;
		return new Configuration(exception);
	}

	/**
	 * @return the type of exception to throw, null to disable the override
	 */
	public Class<? extends RuntimeException> getExceptionOverride()
	{
		return exceptionOverride;
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @throws NullPointerException if {@code type} or {@code message} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message)
	{
		return createException(type, message, ImmutableMap.of(), null);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param cause   the cause of the exception
	 * @throws NullPointerException if {@code type} or {@code message} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		Throwable cause)
	{
		return createException(type, message, ImmutableMap.of(), cause);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @throws NullPointerException if {@code type}, {@code message} or {@code key1} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1)
	{
		return createException(type, message, ImmutableMap.of(key1, value1), null);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @param cause   the cause of the exception
	 * @throws NullPointerException if {@code type}, {@code message} or {@code key1} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1, Throwable cause)
	{
		return createException(type, message, ImmutableMap.of(key1, value1), cause);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @param key2    the second key to append to the exception message
	 * @param value2  the second value to append to the exception message
	 * @throws NullPointerException if {@code type}, {@code message}, {@code key1} or {@code key2} are
	 *                              null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1, String key2, Object value2)
	{
		return createException(type, message, ImmutableMap.of(key1, value1, key2, value2), null);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @param key2    the second key to append to the exception message
	 * @param value2  the second value to append to the exception message
	 * @param cause   the cause of the exception
	 * @throws NullPointerException if {@code type}, {@code message}, {@code key1} or {@code key2} are
	 *                              null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1, String key2, Object value2, Throwable cause)
	{
		return createException(type, message, ImmutableMap.of(key1, value1, key2, value2), cause);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @param key2    the second key to append to the exception message
	 * @param value2  the second value to append to the exception message
	 * @param key3    the third key to append to the exception message
	 * @param value3  the third value to append to the exception message
	 * @throws NullPointerException if {@code type}, {@code message}, {@code key1}, {@code key2} or
	 *                              {@code key3} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1, String key2, Object value2, String key3, Object value3)
	{
		return createException(type, message, ImmutableMap.of(key1, value1, key2, value2, key3, value3),
			null);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @param key2    the second key to append to the exception message
	 * @param value2  the second value to append to the exception message
	 * @param key3    the third key to append to the exception message
	 * @param value3  the third value to append to the exception message
	 * @param cause   the cause of the exception
	 * @throws NullPointerException if {@code type}, {@code message}, {@code key1}, {@code key2} or
	 *                              {@code key3} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1, String key2, Object value2, String key3, Object value3,
		Throwable cause)
	{
		return createException(type, message, ImmutableMap.of(key1, value1, key2, value2, key3, value3),
			cause);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @param key2    the second key to append to the exception message
	 * @param value2  the second value to append to the exception message
	 * @param key3    the third key to append to the exception message
	 * @param value3  the third value to append to the exception message
	 * @param key4    the third key to append to the exception message
	 * @param value4  the third value to append to the exception message
	 * @throws NullPointerException if {@code type}, {@code message}, {@code key1}, {@code key2},
	 *                              {@code key3} or {@code key4} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1, String key2, Object value2, String key3, Object value3, String key4,
		Object value4)
	{
		return createException(type, message,
			ImmutableMap.of(key1, value1, key2, value2, key3, value3, key4, value4), null);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param key1    the first key to append to the exception message
	 * @param value1  the first value to append to the exception message
	 * @param key2    the second key to append to the exception message
	 * @param value2  the second value to append to the exception message
	 * @param key3    the third key to append to the exception message
	 * @param value3  the third value to append to the exception message
	 * @param key4    the third key to append to the exception message
	 * @param value4  the third value to append to the exception message
	 * @param cause   the cause of the exception
	 * @throws NullPointerException if {@code type}, {@code message}, {@code key1}, {@code key2},
	 *                              {@code key3} or {@code key4} are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		String key1, Object value1, String key2, Object value2, String key3, Object value3, String key4,
		Object value4, Throwable cause)
	{
		return createException(type, message,
			ImmutableMap.of(key1, value1, key2, value2, key3, value3, key4, value4),
			cause);
	}

	/**
	 * Creates a new exception.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param context key-value pairs to append to the exception message
	 * @param cause   the cause of the exception
	 * @throws NullPointerException if any of the arguments are null
	 * @return the exception
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message,
		Map<String, Object> context, Throwable cause)
	{
		assert (type != null);
		assert (message != null);
		assert (context != null);
		Class<? extends RuntimeException> winner;
		if (exceptionOverride != null)
			winner = exceptionOverride;
		else
			winner = type;
		String messageWithContext = messageWithContext(message, context);
		return Exceptions.createException(winner, messageWithContext, cause);
	}
}
