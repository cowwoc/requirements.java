/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.terminal;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.terminal.TerminalEncoding;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.XTERM_16_COLORS;

public final class TerminalEncodingTest
{
	@Test
	public void sortOrder()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);

			List<TerminalEncoding> availableEncodings = new ArrayList<>(Arrays.asList(XTERM_16_COLORS, NONE));
			availableEncodings.sort(TerminalEncoding.sortByDecreasingRank());
			validators.requireThat(availableEncodings.getFirst(), "availableEncodings.getFirst()").
				isEqualTo(XTERM_16_COLORS);

			availableEncodings = new ArrayList<>(Arrays.asList(NONE, XTERM_16_COLORS));
			availableEncodings.sort(TerminalEncoding.sortByDecreasingRank());
			validators.requireThat(availableEncodings.getFirst(), "availableEncoding.getFirst()").
				isEqualTo(XTERM_16_COLORS);
		}
	}
}
