/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.secrets;

/**
 * A mechanism for calling package-private methods in another package without using reflection.
 *
 * @see <a href="https://stackoverflow.com/a/46723089/14731">https://stackoverflow.com/a/46723089/14731</a>
 */
public final class SharedSecrets
{
	public static final SharedSecrets INSTANCE = new SharedSecrets();
	public SecretConfiguration secretConfiguration;

	/**
	 * Prevent construction.
	 */
	private SharedSecrets()
	{
	}
}
