/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.impl.AbstractCoreVerifiers;
import org.bitbucket.cowwoc.requirements.guava.ForwardingGuavaVerifiers;
import org.bitbucket.cowwoc.requirements.guava.GuavaVerifiers;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.scope.MainApplicationScope;

/**
 * An entry point for verifying API requirements.
 * <p>
 * Unlike {@link Requirements}, instances of this class are configurable.
 * <p>
 * This class is immutable.
 *
 * @author Gili Tzabari
 */
public final class Verifiers extends AbstractCoreVerifiers
	implements ForwardingGuavaVerifiers
{
	// DESIGN: Using an abstract superclass because interface default methods don't perform as well
	// in Java 8: http://stackoverflow.com/a/30314501/14731
	private static final String MODULES_HELP =
		"https://bitbucket.org/cowwoc/requirements/wiki/Modules";

	/**
	 * @param scope the application configuration
	 * @return scope
	 * @throws AssertionError if {@code scope} is null
	 */
	private static ApplicationScope verifyScope(ApplicationScope scope)
	{
		assert (scope != null): "scope may not be null";
		return scope;
	}

	private final Optional<GuavaVerifiers> guavaVerifiers;

	/**
	 * Creates new verifiers.
	 */
	public Verifiers()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * Creates new verifiers. This constructor is meant to be used by automated tests, not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public Verifiers(ApplicationScope scope)
	{
		this(verifyScope(scope), new Configuration(), scope.createGuavaVerifiers());
	}

	/**
	 * Creates new verifiers. This constructor is meant to be used by automated tests, not by users.
	 *
	 * @param scope          the application configuration
	 * @param configuration  the instance configuration
	 * @param guavaVerifiers an instance of {@code GuavaVerifiers} if the module is available
	 * @throws AssertionError if any of the arguments are null
	 */
	public Verifiers(ApplicationScope scope, Configuration configuration,
		Optional<GuavaVerifiers> guavaVerifiers)
	{
		super(scope, configuration);
		assert (guavaVerifiers != null): "guavaVerifiers may not be null";
		this.guavaVerifiers = guavaVerifiers;
	}

	@Override
	public GuavaVerifiers guavaVerifiers()
	{
		return guavaVerifiers.orElseThrow(() ->
		{
			try
			{
				Class.forName("org.bitbucket.cowwoc.requirements.guava.impl.MultimapVerifierImpl");
			}
			catch (ClassNotFoundException unused)
			{
				return new UnsupportedOperationException("requirements-guava was not loaded.\n" +
					"Classpath: " + System.getProperty("java.class.path") + "\n" +
					"See " + MODULES_HELP + " for more information");
			}
			// Found Guava on the classpath, but requirements-guava is being shadowed
			return new UnsupportedOperationException("requirements-guava must be on the classpath " +
				"before requirements-core.\n" +
				"Classpath: " + System.getProperty("java.class.path") + "\n" +
				"See " + MODULES_HELP + " for more information");
		});
	}

	@Override
	public Verifiers withAssertionsEnabled()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		Optional<GuavaVerifiers> newGuavaVerifiers = guavaVerifiers.
			map(v -> v.withConfiguration(newConfig));
		return new Verifiers(scope, newConfig, newGuavaVerifiers);
	}

	@Override
	public Verifiers withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		Optional<GuavaVerifiers> newGuavaVerifiers = guavaVerifiers.
			map(v -> v.withConfiguration(newConfig));
		return new Verifiers(scope, newConfig, newGuavaVerifiers);
	}

	@Override
	public Verifiers withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		Optional<GuavaVerifiers> newGuavaVerifiers = guavaVerifiers.
			map(v -> v.withConfiguration(newConfig));
		return new Verifiers(scope, newConfig, newGuavaVerifiers);
	}

	@Override
	public Verifiers withDefaultException()
	{
		Configuration newConfig = config.withDefaultException();
		if (newConfig.equals(config))
			return this;
		Optional<GuavaVerifiers> newGuavaVerifiers = guavaVerifiers.
			map(v -> v.withConfiguration(newConfig));
		return new Verifiers(scope, newConfig, newGuavaVerifiers);
	}

	@Override
	public Verifiers addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		Optional<GuavaVerifiers> newGuavaVerifiers = guavaVerifiers.
			map(v -> v.withConfiguration(newConfig));
		return new Verifiers(scope, newConfig, newGuavaVerifiers);
	}

	@Override
	public Verifiers withConfiguration(Configuration configuration)
	{
		if (config.equals(configuration))
			return this;
		Optional<GuavaVerifiers> newGuavaVerifiers = guavaVerifiers.
			map(v -> v.withConfiguration(configuration));
		return new Verifiers(scope, configuration, newGuavaVerifiers);
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		return config.assertionsAreEnabled();
	}
}
