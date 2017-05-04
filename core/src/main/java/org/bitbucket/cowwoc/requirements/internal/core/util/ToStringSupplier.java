/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.util;

import java.util.function.Supplier;

/**
 * A {@link Supplier} whose {@code toString()} method returns the {@code Supplier} value.
 *
 * @author Gili Tzabari
 */
public final class ToStringSupplier
{
	private final Supplier<String> supplier;

	/**
	 * Creates a new adapter.
	 *
	 * @param supplier return the object's String representation
	 * @throws NullPointerException if {@code supplier} is null
	 */
	public ToStringSupplier(Supplier<String> supplier)
	{
		if (supplier == null)
			throw new NullPointerException("supplier may not be null");
		this.supplier = supplier;
	}

	@Override
	public String toString()
	{
		return supplier.get();
	}
}
