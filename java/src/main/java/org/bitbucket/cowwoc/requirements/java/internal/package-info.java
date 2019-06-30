/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * <h2>Design guide</h2>
 * <ul>
 * <li>Any method that returns a new verifier (e.g. MapVerifier.keySet()) is expected to provide an
 * overload that consumes the verifier instead of returning it.</li>
 * <li>Users own the actual value: Users are expected to pass method arguments directly into this
 * API. As such, verifiers may not modify the actual value. The value passed into and out of
 * verifiers is owned by users.</li>
 * <li>{@link org.bitbucket.cowwoc.requirements.java.GlobalRequirements GlobalConfiguration} is a
 * mechanism for exposing a selective subset of
 * {@link org.bitbucket.cowwoc.requirements.java.internal.scope.JvmScope JvmScope} to users.</li>
 * </ul>
 * <h2>JNI Strategy</h2>
 * <ul>
 * <li>Libraries can only be loaded once per JVM (in theory, they can be unloaded when the enclosing
 * ClassLoader gets unloaded but this behavior is not required by the specification and its timing
 * is unpredictable).</li>
 * <li>A single native library can/should implement native methods across multiple Java classes. In
 * other words, there isn't a one-to-one mapping between Java classes and native libraries.</li>
 * <li>As such, we load native libraries outside the Java classes that depend on them.</li>
 * </ul>
 */
package org.bitbucket.cowwoc.requirements.java.internal;

