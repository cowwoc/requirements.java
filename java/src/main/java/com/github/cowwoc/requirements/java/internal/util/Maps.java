package com.github.cowwoc.requirements.java.internal.util;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Map helper functions.
 */
public final class Maps
{
	private Maps()
	{
	}

	public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(
		Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper,
		Supplier<M> supplier)
	{
		// Based on https://stackoverflow.com/a/44415975/14731
		return Collector.of(supplier,
			(m, t) ->
			{
				K k = keyMapper.apply(t);
				U v = Objects.requireNonNull(valueMapper.apply(t));
				if (m.putIfAbsent(k, v) != null)
					throw duplicateKey(k, m.get(k), v);
			},
			(m1, m2) ->
			{
				m2.forEach((k, v) ->
				{
					if (m1.putIfAbsent(k, v) != null)
						throw duplicateKey(k, m1.get(k), v);
				});
				return m1;
			});
	}

	private static IllegalStateException duplicateKey(Object k, Object v1, Object v2)
	{
		return new IllegalStateException("Key " + k + " is associated with multiple values: " + v1 + " and " +
			v2);
	}
}