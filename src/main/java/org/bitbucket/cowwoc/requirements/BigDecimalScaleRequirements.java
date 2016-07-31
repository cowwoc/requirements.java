/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;

/**
 * Verifies requirements of a {@link BigDecimal#scale()}.
 * <p>
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface BigDecimalScaleRequirements
	extends PrimitiveIntegerRequirements<BigDecimalScaleRequirements>
{
}
