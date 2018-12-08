/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.terminal;

import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;
import org.bitbucket.cowwoc.requirements.java.internal.scope.DefaultJvmScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.JvmScope;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bitbucket.cowwoc.requirements.DefaultRequirements.requireThat;
import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;
import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.XTERM_16COLOR;

public final class TerminalEncodingTest
{
	@Test
	public void sortOrder()
	{
		List<TerminalEncoding> availableEncodings = new ArrayList<>(Arrays.asList(XTERM_16COLOR, NONE));
		Collections.sort(availableEncodings, TerminalEncoding.sortByDecreasingRank());
		requireThat("availableEncoding.get(0)", availableEncodings.get(0)).isEqualTo(XTERM_16COLOR);

		availableEncodings = new ArrayList<>(Arrays.asList(NONE, XTERM_16COLOR));
		Collections.sort(availableEncodings, TerminalEncoding.sortByDecreasingRank());
		requireThat("availableEncoding.get(0)", availableEncodings.get(0)).isEqualTo(XTERM_16COLOR);
	}

	@Test
	public void detect()
	{
		@SuppressWarnings("deprecation")
		TerminalEncoding expected = TerminalEncoding.detect();
		try (JvmScope jvm = DefaultJvmScope.INSTANCE)
		{
			Terminal terminal = jvm.getTerminal();
			TerminalEncoding actual = terminal.getSupportedTypes().iterator().next();
			requireThat("actual", actual).isEqualTo("expected", expected);
		}
	}
}
