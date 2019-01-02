/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.secrets;

import org.bitbucket.cowwoc.requirements.java.Configuration;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * @see SharedSecrets
 */
public interface SecretConfiguration
{
	/**
	 * Returns a list of name-value pairs to append to the exception message. Null elements denote
	 * empty lines.
	 *
	 * @param configuration a configuration
	 * @return an unmodifiable list of name-value pairs to append to the exception message
	 * @see Configuration#addContext(String, Object)
	 */
	List<Entry<String, Object>> getContext(Configuration configuration);

	/**
	 * @param configuration a configuration
	 * @param o             an object
	 * @return the String representation of the object
	 * @see Configuration#withStringConverter(Class, Function)
	 */
	String toString(Configuration configuration, Object o);
}
