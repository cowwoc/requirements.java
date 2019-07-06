/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleBigDecimalValidator;

import java.math.BigDecimal;

/**
 * Validates the requirements of a {@link BigDecimal} value.
 */
public interface BigDecimalValidator extends ExtensibleBigDecimalValidator<BigDecimalValidator>
{
}