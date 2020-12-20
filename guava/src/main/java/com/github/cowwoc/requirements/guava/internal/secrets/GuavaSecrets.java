/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava.internal.secrets;

import com.github.cowwoc.requirements.guava.DefaultGuavaRequirements;
import com.github.cowwoc.requirements.guava.GuavaRequirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A mechanism for calling package-private methods in another package without using reflection.
 *
 * @see <a href="https://stackoverflow.com/a/46723089/14731">https://stackoverflow.com/a/46723089/14731</a>
 */
public final class GuavaSecrets
{
	/**
	 * The singleton instance.
	 */
	public static final GuavaSecrets INSTANCE = new GuavaSecrets();
	/**
	 * An instance of {@code SecretRequirements}.
	 */
	private SecretRequirements secretRequirements;
	/**
	 * Indicates if lazy-initialization of the fields is complete.
	 */
	private boolean initialized;
	private final Logger log = LoggerFactory.getLogger(GuavaSecrets.class);

	/**
	 * Prevent construction.
	 */
	private GuavaSecrets()
	{
	}

	/**
	 * Initializes the fields.
	 */
	private synchronized void initOnce()
	{
		if (initialized)
			return;
		// Force static initializers to run
		ClassLoader cl = getClass().getClassLoader();
		try
		{
			Class.forName(DefaultGuavaRequirements.class.getName(), true, cl);
			initialized = true;
		}
		catch (ClassNotFoundException e)
		{
			log.error("", e);
		}
	}

	/**
	 * @param secretRequirements an instance of {@code SecretRequirements}
	 */
	public synchronized void setSecretRequirements(SecretRequirements secretRequirements)
	{
		assert (secretRequirements != null);
		this.secretRequirements = secretRequirements;
	}

	/**
	 * @param scope the application configuration
	 * @return a new {@code GuavaRequirements} instance associated with {@code scope}
	 */
	public synchronized GuavaRequirements createRequirements(ApplicationScope scope)
	{
		initOnce();
		return secretRequirements.create(scope);
	}
}
