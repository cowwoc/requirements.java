/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import java.util.function.Consumer;

/**
 * Requirements that can be verified in isolation of other instances.
 * <p>
 * Interfaces that extend {@code Isolatable} are not meant to be extended, but may be implemented.
 *
 * @param <T> the type of requirements to isolate
 * @author Gili Tzabari
 */
public interface Isolatable<T>
{
	/**
	 * Verifies requirements without modifying the current instance. This can be used to combine
	 * requirements that operate on nested elements:
	 * <pre><code>
	 * Map&lt;String, Integer&gt; nameToId = ...;
	 * Requirements.requireThat(nameToId, "nameToId").keySet().contains("John Smith");
	 * Requirements.requireThat(nameToId, "nameToId").values().doesNotContain(0, "RESERVED_ID");
	 *
	 * // Can be rewritten as:
	 * Requirements.requireThat(nameToId, "nameToId").
	 *   isolate(p -&gt; p.keySet().contains("John Smith")).
	 *   isolate(p -&gt; p.values().doesNotContain(0, "RESERVED_ID"));
	 * </code></pre>
	 *
	 * @param consumer the code to execute in isolation
	 * @return this
	 */
	T isolate(Consumer<T> consumer);
}
