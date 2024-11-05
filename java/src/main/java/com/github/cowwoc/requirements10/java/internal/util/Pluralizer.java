/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.util;

/**
 * Generates the singular or plural form of an element type.
 */
public enum Pluralizer
{
	/**
	 * The names of one or more characters.
	 */
	CHARACTER("character", "characters"),
	/**
	 * The names of one or more keys.
	 */
	KEY("key", "keys"),
	/**
	 * The names of one or more values.
	 */
	VALUE("value", "values"),
	/**
	 * The names of one or more entries.
	 */
	ENTRY("entry", "entries"),
	/**
	 * The names of one or more elements.
	 */
	ELEMENT("element", "elements");

	private final String singular;
	private final String plural;

	/**
	 * @param singular - the singular form of the element
	 * @param plural   - the plural form of the element
	 */
	Pluralizer(String singular, String plural)
	{
		this.singular = singular;
		this.plural = plural;
	}

	/**
	 * @param count the number of elements
	 * @param name  the name of the parameter containing the number of elements ({@code null} if absent)
	 * @return the singular or plural form of the element type (in lowercase)
	 */
	public String nameOf(int count, String name)
	{
		if (count == 1 && name == null)
			return singular;
		return plural;
	}
}