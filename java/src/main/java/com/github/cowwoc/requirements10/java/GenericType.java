package com.github.cowwoc.requirements10.java;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashSet;
import java.util.Set;

/**
 * A reference to a type that may contain type parameters. Based on Neal Gafter's
 * <a href="https://gafter.blogspot.com/2006/12/super-type-tokens.html">blog post</a>.
 * <p>
 * Sample usage:
 * {@snippet :
 *  GenericType<List<Integer>> type = new GenericType<List<Integer>>(){};
 *  Object unknown = List.of(1, 2, 3);
 *  List<Integer> actual = requireThat(value, "actual").isList(type).getActual();
 *}
 *
 * @param <T> the type of the class modelled by the {@code GenericType} object. For example, the type of
 *            {@code String.class} is {@code GenericType<String>}.
 */
public abstract class GenericType<T>
{
	private final Type type;

	/**
	 * Wraps a {@code Class}.
	 *
	 * @param <T>  the type of the class
	 * @param type the class
	 * @return the type corresponding to the class, or {@code null} if {@code type} is null
	 */
	public static <T> GenericType<T> from(Class<T> type)
	{
		if (type == null)
			return null;
		return new GenericType<>(type)
		{
		};
	}

	/**
	 * Wraps a {@code Type}.
	 *
	 * @param <T>  the type of the {@code Type}
	 * @param type the type
	 * @return the type corresponding to the {@code Type}, or {@code null} if {@code type} is null
	 */
	public static <T> GenericType<T> from(Type type)
	{
		if (type == null)
			return null;
		return new GenericType<>(type)
		{
		};
	}

	/**
	 * Returns the type of a value.
	 *
	 * @param <T>   the type of the value
	 * @param value a value
	 * @return the type of the value, or {@code null} if the value is null
	 */
	public static <T> Object of(T value)
	{
		if (value == null)
			return null;
		return from(value.getClass());
	}

	/**
	 * Creates a new type.
	 */
	public GenericType()
	{
		Type superClass = getClass().getGenericSuperclass();
		this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
	}

	/**
	 * Creates a new type.
	 *
	 * @param type the {@code Type} to wrap
	 * @throws AssertionError if {@code type} is null
	 */
	private GenericType(Type type)
	{
		assert (type != null);
		this.type = type;
	}

	/**
	 * Returns the fully qualified name of the type.
	 *
	 * @return the fully qualified name of the type
	 */
	public String getName()
	{
		return switch (type)
		{
			case GenericArrayType array -> from(array.getGenericComponentType()).getName() + "[]";
			case ParameterizedType parameterizedType -> parameterizedType.getTypeName();
			case Class<?> aClass -> aClass.getTypeName();
			default -> throw new AssertionError(unexpectedType(type));
		};
	}

	private static String unexpectedType(Type type)
	{
		return "Unexpected type: " + type + ".\n" +
			"Please report to the authors.";
	}

	/**
	 * Determines if the argument extends or implements this type.
	 *
	 * @param <C>   the type of the child
	 * @param child the child type
	 * @return {@code true} if the argument extends or implements this type
	 */
	public <C> boolean isSupertypeOf(GenericType<C> child)
	{
		return child != null && child.isSubtypeOf(this);
	}

	/**
	 * Determines if this type extends or implements the argument.
	 *
	 * @param <C>    the type of the parent
	 * @param parent the parent type
	 * @return {@code true} if this type extends or implements the argument
	 */
	public <C> boolean isSubtypeOf(GenericType<C> parent)
	{
		if (parent == null)
			return false;
		return switch (parent.type)
		{
			case TypeVariable<?> typeVariable ->
			{
				// Given: Parent extends X & Y & Z
				// Return true if we are a subtype of one of the bounds.
				for (Type bound : typeVariable.getBounds())
					if (isSubtypeOf(from(bound)))
						yield true;
				yield false;
			}
			case ParameterizedType parameterizedType -> isSubtypeOf(from(parameterizedType.getRawType()));
			case Class<?> parentClass -> parentClass.isAssignableFrom(getRawType());
			default -> throw new AssertionError(unexpectedType(parent.type));
		};
	}

	/**
	 * @return the raw types represented by this type
	 */
	private Set<Class<? super T>> getRawTypes()
	{
		return switch (type)
		{
			case GenericArrayType array ->
			{
				Type componentType = array.getGenericComponentType();
				yield Set.of(toRawType(getClassOfArray(from(componentType).getRawType())));
			}
			case ParameterizedType pt -> Set.of(toRawType(pt.getRawType()));
			case TypeVariable<?> tv -> typesToRawTypes(tv.getBounds());
			case WildcardType wildcard -> typesToRawTypes(wildcard.getUpperBounds());
			case Class<?> theClass -> Set.of(toRawType(theClass));
			default -> throw new AssertionError(unexpectedType(type));
		};
	}

	/**
	 * Converts a type to a {@code Class<? super T>}.
	 * <p>
	 * {@code Class<? super List<Integer>>} matches any superclass of {@code List<Integer>} including
	 * {@code List}; therefore, the raw type of {@code T} matches {@code Class<? super T>}.
	 *
	 * @param type the type
	 * @return the type as a {@code Class<? super T>}
	 */
	private Class<? super T> toRawType(Type type)
	{
		@SuppressWarnings("unchecked")
		Class<? super T> superT = (Class<? super T>) type;
		return superT;
	}

	/**
	 * Converts an array of types to their corresponding raw types.
	 *
	 * @param types an array of types
	 * @return a set of the types converted to their corresponding raw types
	 */
	private Set<Class<? super T>> typesToRawTypes(Type[] types)
	{
		Set<Class<? super T>> classes = new HashSet<>();
		for (Type bound : types)
			classes.add(toRawType(bound));
		return classes;
	}

	private static <T> Class<T[]> getClassOfArray(Class<T> componentType)
	{
		Object array = Array.newInstance(componentType, 0);
		@SuppressWarnings("unchecked")
		Class<T[]> temp = (Class<T[]>) array.getClass();
		return temp;
	}

	/**
	 * Determines if the argument is an instance of this type.
	 *
	 * @param object an object
	 * @return {@code true} if the argument is an instance of this type
	 */
	public boolean isTypeOf(Object object)
	{
		if (object == null)
			return false;

		return switch (type)
		{
			case GenericArrayType array -> this.equals(from(object.getClass()));
			case ParameterizedType parameterizedType -> from(parameterizedType.getRawType()).
				isTypeOf(object);
			case Class<?> aClass -> aClass.isInstance(object);
			default -> throw new AssertionError(unexpectedType(type));
		};
	}

	/**
	 * Determines if this object represents a primitive type.
	 *
	 * @return {@code true} if the type is {@code boolean}, {@code char}, {@code byte}, {@code short},
	 * {@code int}, {@code long}, {@code float}, or {@code double}
	 */
	public boolean isPrimitive()
	{
		return type instanceof Class<?> c && c.isPrimitive();
	}

	/**
	 * Returns the raw representation of this type.
	 *
	 * @return the raw representation of this type
	 */
	public Class<? super T> getRawType()
	{
		// Classes have a single raw type.
		// The raw type of Types with bounds (e.g. wildcards) is equal to their first bound.
		return getRawTypes().iterator().next();
	}

	@Override
	public int hashCode()
	{
		return type.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof GenericType<?> other && other.type.equals(type);
	}

	@Override
	public String toString()
	{
		return getName();
	}
}