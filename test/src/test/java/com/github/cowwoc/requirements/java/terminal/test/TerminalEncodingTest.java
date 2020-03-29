/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.terminal.test;

import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_16_COLORS;

public final class TerminalEncodingTest
{
	@Test
	public void sortOrder()
	{
		List<TerminalEncoding> availableEncodings = new ArrayList<>(Arrays.asList(XTERM_16_COLORS, NONE));
		availableEncodings.sort(TerminalEncoding.sortByDecreasingRank());
		requireThat(availableEncodings.get(0), "availableEncoding.get(0)").isEqualTo(XTERM_16_COLORS);

		availableEncodings = new ArrayList<>(Arrays.asList(NONE, XTERM_16_COLORS));
		availableEncodings.sort(TerminalEncoding.sortByDecreasingRank());
		requireThat(availableEncodings.get(0), "availableEncoding.get(0)").isEqualTo(XTERM_16_COLORS);
	}
}
