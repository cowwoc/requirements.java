/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * Design guide:
 * <p>
 * <ul>
 * <li>Any method that returns a new verifier (e.g. MapVerifier.keySet()) is expected to provide an
 * overload that consumes the verifier instead of returning it.</li>
 * <li>Users own the actual value: Users are expected to pass method arguments directly into this
 * API. As such, verifiers may not modify the actual value. The value passed into and out of
 * verifiers is owned by users.</li>
 * <li>{@link GlobalConfiguration} is a mechanism for exposing a selective subset of
 * {@link JvmScope} to users.</li>
 * </ul>
 * <h1>JNI Strategy</h1>
 * <p>
 * <ul>
 * <li>Libraries can only be loaded once per JVM (in theory, they can be unloaded when the enclosing
 * ClassLoader gets unloaded but this behavior is not required by the specification and its timing
 * is unpredictable).</li>
 * <li>A single native library can/should implement native methods across multiple Java classes. In
 * other words, there isn't a one-to-one mapping between Java classes and native libraries.</li>
 * <li>As such, we load native libraries outside the Java classes that depend on them.</li>
 * </ul>
 */
