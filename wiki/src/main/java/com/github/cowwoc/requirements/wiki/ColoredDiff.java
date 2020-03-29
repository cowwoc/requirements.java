package com.github.cowwoc.requirements.wiki;

import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;

public final class ColoredDiff
{
	public static void main(String[] args)
	{
		try
		{
			requireThat("actual", "actual").isEqualTo("ballroom");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			requireThat("actual", "actual").isEqualTo("text");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			requireThat("text", "actual").isEqualTo("");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			requireThat("foo", "actual").isEqualTo("   foo");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			requireThat("null", "actual").isEqualTo(null);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			requireThat("first\nsecond\nfoo\nforth\nfifth", "actual").
				isEqualTo("first\nsecond\nbar\nforth\nfifth");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			requireThat("Foo\nBar", "actual").isEqualTo("Bar");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
