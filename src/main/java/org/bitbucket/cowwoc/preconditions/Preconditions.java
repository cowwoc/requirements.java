package org.bitbucket.cowwoc.preconditions;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Verifies preconditions of a parameter.
 * <p/>
 * @author Gili Tzabari
 * @param <T> the type of the parameter
 */
public class Preconditions<T>
{
	/**
	 * Creates new Preconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	Preconditions(String name, T parameter)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		this.name = name;
		this.parameter = parameter;
	}

	/**
	 * Creates new Preconditions.
	 * <p>
	 * @param <T>       the type of the parameter
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <T> Preconditions<T> valueOf(String name, T parameter)
	{
		return new Preconditions<>(name, parameter);
	}

	/**
	 * Creates new CollectionPreconditions.
	 * <p>
	 * @param <E>       the type of element in the collection
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <E> CollectionPreconditions<E> valueOf(String name, Collection<E> parameter)
	{
		return new CollectionPreconditions<>(name, parameter);
	}

	/**
	 * Creates new IntegerPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static IntegerPreconditions valueOf(String name, Integer parameter)
	{
		return new IntegerPreconditions(name, parameter);
	}

	/**
	 * Creates new LongPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static LongPreconditions valueOf(String name, Long parameter)
	{
		return new LongPreconditions(name, parameter);
	}

	/**
	 * Creates new MapPreconditions.
	 * <p>
	 * @param <K>       the type of key in the map
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <K> MapPreconditions<K> valueOf(String name, Map<K, ?> parameter)
	{
		return new MapPreconditions<>(name, parameter);
	}

	/**
	 * Creates new PathPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static PathPreconditions valueOf(String name, Path parameter)
	{
		return new PathPreconditions(name, parameter);
	}

	/**
	 * Creates new StringPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static StringPreconditions valueOf(String name, String parameter)
	{
		return new StringPreconditions(name, parameter);
	}

	/**
	 * Creates new UriPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static UriPreconditions valueOf(String name, URI parameter)
	{
		return new UriPreconditions(name, parameter);
	}

	protected final String name;
	protected final T parameter;

	/**
	 * Ensures that the parameter is not null.
	 * <p/>
	 * @return this
	 * @throws NullPointerException if parameter is null
	 */
	public Preconditions<T> isNotNull() throws NullPointerException
	{
		if (parameter == null)
			throw new NullPointerException(name + " may not be null");
		return this;
	}

	/**
	 * Ensures that the parameter is not null.
	 * <p/>
	 * @return this
	 * @throws IllegalStateException if parameter is null
	 */
	public Preconditions<T> stateIsNotNull() throws IllegalStateException
	{
		if (parameter == null)
			throw new IllegalStateException(name + " may not be null");
		return this;
	}

	/**
	 * Ensures that the parameter is equal to a value.
	 * <p/>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if parameter is not equal to value
	 */
	public Preconditions<T> isEqualTo(Object value)
		throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			throw new IllegalArgumentException(name + " must be equal to " + value + ". Was: " + parameter);
		return this;
	}
}
