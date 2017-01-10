/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * Design guide:
 * <p>
 * <ul>
 * <li>Any method that returns a new verifier (e.g. MapVerifier.keySet()) is expected to provide
 * an overload that consumes the verifier instead of returning it.</li>
 * </ul>
 */
