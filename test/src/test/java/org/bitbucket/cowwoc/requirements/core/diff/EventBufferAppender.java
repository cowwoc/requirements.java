/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.diff;

import ch.qos.logback.core.AppenderBase;
import java.util.ArrayList;
import java.util.List;

/**
 * A Logback appender that exposes the events that it receives.
 *
 * @param <E> the type of events being logged
 * @author Gili Tzabari
 */
public final class EventBufferAppender<E> extends AppenderBase<E>
{
	public final List<E> events = new ArrayList<>(2);

	@Override
	protected void append(E event)
	{
		events.add(event);
	}
}
