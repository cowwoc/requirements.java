package com.github.cowwoc.requirements.test;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.XTERM_16_COLORS;

public final class ColoredDiffTest
{
	private final Logger log = LoggerFactory.getLogger(ColoredDiffTest.class);

	@Test
	public void example1()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			log.info("Example 1");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat("", "Actual").isEqualTo("text");
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
			log.info("Example 2");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat("text", "Actual").isEqualTo("");
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
			log.info("Example 3");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat("foo", "Actual").isEqualTo("   foo");
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
			log.info("Example 4");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat("foosball", "Actual").isEqualTo("ballroom");
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
			log.info("Example 5");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat("null", "Actual").isEqualTo(null);
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
			log.info("Example 6");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat("first\nsecond\nfoo\nforth\nfifth", "Actual").
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
			log.info("Example 7");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat("Foo\nBar", "Actual").isEqualTo("Bar");
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
			log.info("Example 8");
			log.info("---------");
			try
			{
				new TestValidatorsImpl(scope).requireThat(List.of("1", "foo\nbar", "3"), "Actual").
					isEqualTo(List.of("1", "bar\nfoo"), "Expected");
			}
			catch (IllegalArgumentException e)
			{
				log.info(e.getMessage());
			}
		}
	}
}