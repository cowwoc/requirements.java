/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

/**
 * Generates the singular or plural form of an element type.
 */
public enum Pluralizer
{
	CHARACTER
	{
		@Override
		public String nameOf(int count)
		{
			if (count == 1)
				return "character";
			return "characters";
		}
	},
	KEY
	{
		@Override
		public String nameOf(int count)
		{
			if (count == 1)
				return "key";
			return "keys";
		}
	},
	VALUE
	{
		@Override
		public String nameOf(int count)
		{
			if (count == 1)
				return "value";
			return "values";
		}
	},
	ENTRY
	{
		@Override
		public String nameOf(int count)
		{
			if (count == 1)
				return "entry";
			return "entries";
		}
	},
	ELEMENT
	{
		@Override
		public String nameOf(int count)
		{
			if (count == 1)
				return "element";
			return "elements";
		}
	};

	/**
	 * @param count the number of elements
	 * @return the singular or plural form of the element type (in lowercase)
	 */
	public abstract String nameOf(int count);
}
