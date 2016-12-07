/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import java.util.function.Consumer;

/**
 * Verifies requirements using a duplicate verifier reference. Regardless of what operations are
 * run, the original reference remains unmodified.
 * <p>
 * Interfaces that extend {@code Isolatable} may be implemented, but are not meant to be extended.
 *
 * @param <T> the type of verifier to isolate
 * @author Gili Tzabari
 */
public interface Isolatable<T>
{
	/**
	 * Verifies requirements without modifying the verifier reference. This can be used to combine
	 * verifications on nested elements:
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
