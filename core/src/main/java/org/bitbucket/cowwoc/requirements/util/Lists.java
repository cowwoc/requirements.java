/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * List helper functions.
 *
 * @author Gili Tzabari
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

		UNMODIFIABLE_CLASS_NAMES = new HashSet<>(3);
		UNMODIFIABLE_CLASS_NAMES.add(randomAccessList);
		UNMODIFIABLE_CLASS_NAMES.add(emptyList);
		UNMODIFIABLE_CLASS_NAMES.add(sequentialList);
	}

	/**
	 * The same as {@link java.util.Collections#unmodifiableList(List)} except that the input list
	 * is returned unmodified if it is already unmodifiable.
	 *
	 * @param <T>  the class of the objects in the list
	 * @param list the list for which an unmodifiable view is to be returned.
	 * @return {@code list} if the list is already unmodifiable; otherwise, a new unmodifiable view
	 *         of the specified list
	 */
	public static <T> List<T> unmodifiable(List<T> list)
	{
		if (isUnmodifiable(list))
			return list;
		return java.util.Collections.unmodifiableList(list);
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
