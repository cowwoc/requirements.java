/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * List helper functions.
 */
public final class Lists
{
	/**
	 * The class names of known unmodifiable lists.
	 * <p>
	 * See http://stackoverflow.com/a/19841066/14731 for the decision to use a Set.
	 * See http://stackoverflow.com/a/4129172/14731 for the decision to look up classes.
	 */
	private static final Set<Class<?>> UNMODIFIABLE_CLASS_NAMES;

	static
	{
		Class<?> randomAccessList = Collections.unmodifiableList(Collections.emptyList()).getClass();
		Class<?> sequentialList = Collections.unmodifiableList(new LinkedList<>()).getClass();
		Class<?> emptyList = Collections.emptyList().getClass();

		UNMODIFIABLE_CLASS_NAMES = new HashSet<>(Arrays.asList(randomAccessList, emptyList,
			sequentialList));
	}

	/**
	 * The same as {@link Collections#unmodifiableList(List)} except that the input list
	 * is returned unmodified if it is already unmodifiable.
	 *
	 * @param <T>  the type of elements in the list
	 * @param list a list
	 * @return {@code list} if the list is already unmodifiable; otherwise, an unmodifiable list
	 *         containing the same elements as the original list
	 */
	public static <T> List<T> unmodifiable(List<T> list)
	{
		if (isUnmodifiable(list))
			return list;
		return Collections.unmodifiableList(list);
	}

	/**
	 * @param list a list
	 * @return {@code true} if the list is unmodifiable; {@code false} if the list *may* be modifiable
	 */
	public static boolean isUnmodifiable(List<?> list)
	{
		return UNMODIFIABLE_CLASS_NAMES.contains(list.getClass());
	}

	/**
	 * Prevent construction.
	 */
	private Lists()
	{
	}
}
