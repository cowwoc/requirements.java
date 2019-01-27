/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.generator.internal.secrets;

import org.bitbucket.cowwoc.requirements.generator.ApiGenerator;

/**
 * @see SharedSecrets
 */
public interface SecretApiGenerator
{
	/**
	 * Indicates that {@code ApplicationScope}-related methods should be generated.
	 *
	 * @param generator the {@code ApiGenerator}
	 */
	void exportScope(ApiGenerator generator);
}
