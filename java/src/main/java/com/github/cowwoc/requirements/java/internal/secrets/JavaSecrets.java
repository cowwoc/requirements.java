/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.secrets;

import com.github.cowwoc.requirements.java.DefaultJavaRequirements;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.CloseableLock;
import com.github.cowwoc.requirements.java.internal.util.ReentrantStampedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A mechanism for calling package-private methods in another package without using reflection.
 *
 * @see <a href="https://stackoverflow.com/a/46723089/14731">https://stackoverflow.com/a/46723089/14731</a>
 */
public final class JavaSecrets
{
	/**
	 * The singleton instance.
	 */
	public static final JavaSecrets INSTANCE = new JavaSecrets();
	private SecretRequirements secretRequirements;
	private boolean initialized;
	private final ReentrantStampedLock lock = new ReentrantStampedLock();
	private final Logger log = LoggerFactory.getLogger(JavaSecrets.class);

	/**
	 * Prevent construction.
	 */
	private JavaSecrets()
	{
	}

	/**
	 * Initializes the fields.
	 */
	private void initOnce()
	{
		if (lock.optimisticRead(() -> initialized))
			return;
		try (CloseableLock ignored = lock.write())
		{
			if (initialized)
				return;
			// Force static initializers to run
			ClassLoader cl = getClass().getClassLoader();
			try
			{
				Class.forName(DefaultJavaRequirements.class.getName(), true, cl);
				initialized = true;
			}
			catch (ClassNotFoundException e)
			{
				log.error("", e);
			}
		}
	}

	/**
	 * @param secretRequirements an instance of {@code SecretRequirements}
	 */
	public void setSecretRequirements(SecretRequirements secretRequirements)
	{
		try (CloseableLock ignored = lock.write())
		{
			assert (secretRequirements != null);
			this.secretRequirements = secretRequirements;
		}
	}

	/**
	 * @param scope the application configuration
	 * @return a new {@code JavaRequirements} instance associated with {@code scope}
	 */
	public JavaRequirements createRequirements(ApplicationScope scope)
	{
		try (CloseableLock ignored = lock.read())
		{
			initOnce();
			return secretRequirements.create(scope);
		}
	}
}