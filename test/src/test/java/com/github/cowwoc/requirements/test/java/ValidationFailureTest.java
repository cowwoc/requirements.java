/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ValidationFailureTest
{
	/**
	 * Ensures that users can disable formatting for validation failure messages.
	 */
	@Test
	public void messageWithoutFormatting()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			String actual = "int[6]";
			String expected = "int[5]";

			String expectedMessage = """
				actual must be equal to "int[5]".

				Actual: "int[6]\"""";
			List<String> expectedMessages = Collections.singletonList(expectedMessage);

			List<ValidationFailure> actualFailures = new Requirements(scope).
				withoutDiff().
				validateThat(actual, "actual").isEqualTo(expected).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	// DiffTest.diffArraySize_8Colors() ensures that ValidationFailure.createException() returns a formatted
	// message.
}
