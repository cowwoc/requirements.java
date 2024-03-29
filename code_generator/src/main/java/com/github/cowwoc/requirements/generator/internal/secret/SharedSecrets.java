/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.generator.internal.secret;

/**
 * A mechanism for calling package-private methods in another package without using reflection.
 *
 * @see <a href="https://stackoverflow.com/a/46723089/14731">https://stackoverflow.com/a/46723089/14731</a>
 */
public final class SharedSecrets
{
	/**
	 * The singleton instance.
	 */
	public static final SharedSecrets INSTANCE = new SharedSecrets();
	/**
	 * An instance of {@code SecretApiGenerator}.
	 */
	public SecretApiGenerator secretApiGenerator;

	/**
	 * Prevent construction.
	 */
	private SharedSecrets()
	{
	}
}
