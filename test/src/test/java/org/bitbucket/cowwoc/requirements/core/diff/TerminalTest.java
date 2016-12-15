/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.diff;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.stream.Collectors;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class TerminalTest
{
	@Test
	public void loadNativeLibrary()
	{
		EventBufferAppender<ILoggingEvent> eventBuffer = new EventBufferAppender<>();
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.addAppender(eventBuffer);
		eventBuffer.start();

		try (SingletonScope scope = new TestSingletonScope(TerminalType.XTERM_16COLOR))
		{
			Terminal terminal = scope.getTerminal();
			TerminalType type = terminal.getType();
		}
		assert (eventBuffer.events.isEmpty()): "Unexpected events:\n" +
			eventBuffer.events.stream().map(e -> e.getFormattedMessage()).collect(Collectors.toList());
	}
}
