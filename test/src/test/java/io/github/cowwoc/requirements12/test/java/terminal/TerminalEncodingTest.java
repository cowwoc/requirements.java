/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.test.java.terminal;

import io.github.cowwoc.requirements12.java.TerminalEncoding;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.test.TestValidators;
import io.github.cowwoc.requirements12.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.cowwoc.requirements12.java.TerminalEncoding.NONE;
import static io.github.cowwoc.requirements12.java.TerminalEncoding.XTERM_16_COLORS;

public final class TerminalEncodingTest
{
	@Test
	public void sortOrder()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

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
