/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.terminal;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_16_COLORS;

public final class TerminalEncodingTest
{
	@Test
	public void sortOrder()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Requirements requirements = new Requirements(scope);

			List<TerminalEncoding> availableEncodings = new ArrayList<>(Arrays.asList(XTERM_16_COLORS, NONE));
			availableEncodings.sort(TerminalEncoding.sortByDecreasingRank());
			requirements.requireThat(availableEncodings.get(0), "availableEncoding.get(0)").isEqualTo(XTERM_16_COLORS);

			availableEncodings = new ArrayList<>(Arrays.asList(NONE, XTERM_16_COLORS));
			availableEncodings.sort(TerminalEncoding.sortByDecreasingRank());
			requirements.requireThat(availableEncodings.get(0), "availableEncoding.get(0)").isEqualTo(XTERM_16_COLORS);
		}
	}
}
