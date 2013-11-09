package org.bitbucket.cowwoc.preconditions;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Verifies preconditions of a Path parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class PathPreconditions extends Preconditions<Path>
{
	/**
	 * Creates new PathPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	PathPreconditions(String name, Path parameter)
	{
		super(name, parameter);
	}

	@Override
	public PathPreconditions isEqualTo(Object value) throws IllegalArgumentException
	{
		return (PathPreconditions) super.isEqualTo(value);
	}

	@Override
	public PathPreconditions stateIsNotNull() throws NullPointerException
	{
		return (PathPreconditions) super.stateIsNotNull();
	}

	@Override
	public PathPreconditions isNotNull() throws NullPointerException
	{
		return (PathPreconditions) super.isNotNull();
	}

	/**
	 * Ensures that a duration is positive.
	 * <p/>
	 * @return this
	 * @throws IllegalStateException if path does not exist
	 */
	public PathPreconditions exists() throws IllegalStateException
	{
		if (!Files.exists(parameter))
			throw new IllegalStateException(name + " refers to a non-existent path: " + parameter);
		return this;
	}
}
