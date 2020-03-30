package com.github.cowwoc.requirements.wiki;

import java.util.List;

import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;

public final class ColoredDiff
{
	public static void main(String[] args)
	{
		try
		{
			requireThat("", "actual").isEqualTo("text");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		try
		{
			requireThat("text", "actual").isEqualTo("");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		try
		{
			requireThat("foo", "actual").isEqualTo("   foo");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		try
		{
			requireThat("foosball", "actual").isEqualTo("ballroom");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		try
		{
			requireThat("null", "actual").isEqualTo(null);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		try
		{
			requireThat("first\nsecond\nfoo\nforth\nfifth", "actual").
				isEqualTo("first\nsecond\nbar\nforth\nfifth");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		try
		{
			requireThat("Foo\nBar", "actual").isEqualTo("Bar");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println();
		try
		{
			requireThat(List.of("1", "foo\nbar", "3"), "actual").
				isEqualTo(List.of("1", "bar\nfoo", "3"));
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
