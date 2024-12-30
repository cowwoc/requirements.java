package com.github.cowwoc.requirements10.test;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.XTERM_16_COLORS;

public final class ColoredDiffTest
{
	private final Logger log = LoggerFactory.getLogger(ColoredDiffTest.class);

	@Test
	public void example1()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 1");
			log.info("---------");
			try
			{
				validators.requireThat("", "actual").isEqualTo("text");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}

	@Test
	public void example2()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 2");
			log.info("---------");
			try
			{
				validators.requireThat("text", "actual").isEqualTo("");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}

	@Test
	public void example3()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 3");
			log.info("---------");
			try
			{
				validators.requireThat("foo", "actual").isEqualTo("   foo");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}

	@Test
	public void example4()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 4");
			log.info("---------");
			try
			{
				validators.requireThat("foosball", "actual").isEqualTo("ballroom");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}

	@Test
	public void example5()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 5");
			log.info("---------");
			try
			{
				validators.requireThat("null", "actual").isEqualTo(null);
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}

	@Test
	public void example6()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 6");
			log.info("---------");
			try
			{
				validators.requireThat("first\nsecond\nfoo\nforth\nfifth", "actual").
					isEqualTo("first\nsecond\nbar\nforth\nfifth");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}

	@Test
	public void example7()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 7");
			log.info("---------");
			try
			{
				validators.requireThat("Foo\nBar", "actual").isEqualTo("Bar");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}

	@Test
	public void example8()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			TestValidators validators = TestValidators.of(scope);

			log.info("Example 8");
			log.info("---------");
			try
			{
				validators.requireThat(List.of("1", "foo\nbar", "3"), "actual").
					isEqualTo(List.of("1", "bar\nfoo"), "expected");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}
}