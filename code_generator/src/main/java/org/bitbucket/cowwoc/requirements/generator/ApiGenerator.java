/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.generator;

import org.bitbucket.cowwoc.requirements.generator.internal.secrets.SharedSecrets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Generates {@code Requirements} and {@code DefaultRequirements} endpoints. The contents of these classes
 * depend on which plugins are enabled.
 */
public final class ApiGenerator
{
	static
	{
		SharedSecrets.INSTANCE.secretApiGenerator = generator -> generator.exportScope();
	}

	private boolean guavaEnabled;
	private boolean exportScope;

	/**
	 * The command-line entry point for this class.
	 *
	 * @param args the command-line arguments
	 * @throws IOException if an I/O error occurs while writing the files
	 */
	public static void main(String[] args) throws IOException
	{
		if (args.length != 1)
		{
			System.err.println("Generates the API endpoints (Requirements, DefaultRequirements)");
			System.err.println();
			System.err.println("Usage: ApiGenerator <root>");
			System.err.println();
			System.err.println("Where:");
			System.err.println();
			System.err.println("<root> is the directory corresponding to the Java root package");
			System.exit(1);
		}

		Path directory = Paths.get(args[0]);
		ApiGenerator generator = new ApiGenerator();

		Logger log = LoggerFactory.getLogger(ApiGenerator.class);
		Path defaultRequirements = generator.getDefaultRequirementsPath(directory);
		if (generator.writeDefaultRequirements(defaultRequirements))
			log.info("{} was up-to-date", defaultRequirements);
		else
			log.info("Generated {}", defaultRequirements);

		Path requirements = generator.getRequirementsPath(directory);
		if (generator.writeRequirements(requirements))
			log.info("{} was up-to-date", requirements);
		else
			log.info("Generated {}", requirements);
	}

	/**
	 * Indicates if the user will have the guava plugin enabled (in which case additional methods are
	 * generated for it).
	 *
	 * @param value true if the Guava plugin is enabled
	 */
	public void setGuavaEnabled(boolean value)
	{
		this.guavaEnabled = value;
	}

	/**
	 * Indicates that {@code ApplicationScope}-related methods should be generated. These methods are used by
	 * automated tests.
	 */
	private void exportScope()
	{
		this.exportScope = true;
	}

	/**
	 * Returns the path of the {@code DefaultRequirements.java} file.
	 *
	 * @param rootPackage the path of the root package
	 * @return the path of the {@code DefaultRequirements.java} file
	 */
	public Path getDefaultRequirementsPath(Path rootPackage)
	{
		return rootPackage.resolve("org/bitbucket/cowwoc/requirements/DefaultRequirements.java");
	}

	/**
	 * Generates {@code org.bitbucket.cowwoc.requirements.DefaultRequirements}.
	 *
	 * @param path the path of the file
	 * @return true if the file was updated
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	public boolean writeDefaultRequirements(Path path) throws IOException
	{
		Files.createDirectories(path.getParent());

		String oldValue;
		try
		{
			oldValue = Files.readString(path);
		}
		catch (NoSuchFileException unused)
		{
			oldValue = "";
		}
		try (StringWriter sw = new StringWriter();
		     BufferedWriter writer = new BufferedWriter(sw))
		{
			writer.write("/*\n" +
				" * Copyright 2013 Gili Tzabari.\n" +
				" * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0\n" +
				" */\n" +
				"package org.bitbucket.cowwoc.requirements;\n" +
				"\n");
			if (guavaEnabled)
			{
				writer.write("import com.google.common.collect.Multimap;\n" +
					"import org.bitbucket.cowwoc.requirements.guava.DefaultGuavaVerifier;\n" +
					"import org.bitbucket.cowwoc.requirements.guava.GuavaVerifier;\n" +
					"import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;\n");
			}
			writer.write("import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.BooleanVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.ClassVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.Configuration;\n" +
				"import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.GlobalConfigurable;\n" +
				"import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.IntegerVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.JavaVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.MapVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.NumberVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.ObjectVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PathVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.StringVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.UriVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.DefaultJavaVerifier;\n");
			writer.write("\n" +
				"import java.math.BigDecimal;\n" +
				"import java.net.InetAddress;\n" +
				"import java.net.URI;\n" +
				"import java.nio.file.Path;\n" +
				"import java.util.Collection;\n" +
				"import java.util.Map;\n" +
				"import java.util.Optional;\n" +
				"\n" +
				"/**\n" +
				" * Verifies API requirements using the {@link GlobalConfigurable default configuration}.\n" +
				" * <p>\n" +
				" * The assertion status of the {@link Configuration} class determines whether " +
				"{@code assertThat()} carries\n" +
				"   out a verification or does nothing.\n" +
				" * <p>\n" +
				" * This class is immutable.\n" +
				" *\n" +
				" * @see Requirements\n" +
				" * @see JavaVerifier\n");
			if (guavaEnabled)
				writer.write(" * @see GuavaVerifier\n");
			writer.write(" */\n" +
				"public final class DefaultRequirements\n" +
				"{\n" +
				"\tprivate static final JavaVerifier JAVA_VERIFIER = new DefaultJavaVerifier();\n");
			if (guavaEnabled)
			{
				writer.write("\tprivate static final GuavaVerifier GUAVA_VERIFIER = new " +
					"DefaultGuavaVerifier();\n");
			}
			writer.write("\n" +
				"\t/**\n" +
				"\t * @return true if assertions are enabled for this class\n" +
				"\t */\n" +
				"\tpublic static boolean assertionsAreEnabled()\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertionsAreEnabled();\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code Object}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the value\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T> ObjectVerifier<T> requireThat(T actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Object)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the value\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T> ObjectVerifier<T> assertThat(T actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Collection}.\n" +
				"\t *\n" +
				"\t * @param <C>    the type of the collection\n" +
				"\t * @param <E>    the type of elements in the collection\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <C extends Collection<E>, E> CollectionVerifier<C, E>\n" +
				"\trequireThat(C actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Collection)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param <C>    the type of the collection\n" +
				"\t * @param <E>    the type of elements in the collection\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <C extends Collection<E>, E> CollectionVerifier<C, E>\n" +
				"\tassertThat(C actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code byte} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveByteArrayVerifier requireThat(byte[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, byte[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveByteArrayVerifier assertThat(byte[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code short} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveShortArrayVerifier requireThat(short[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, short[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveShortArrayVerifier assertThat(short[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code int} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveIntegerArrayVerifier requireThat(int[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, int[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveIntegerArrayVerifier assertThat(int[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code long} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveLongArrayVerifier requireThat(long[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, long[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveLongArrayVerifier assertThat(long[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code float} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveFloatArrayVerifier requireThat(float[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, float[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveFloatArrayVerifier assertThat(float[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code double} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveDoubleArrayVerifier requireThat(double[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, double[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveDoubleArrayVerifier assertThat(double[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code boolean} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveBooleanArrayVerifier requireThat(boolean[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, boolean[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveBooleanArrayVerifier assertThat(boolean[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code char} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveCharacterArrayVerifier requireThat(char[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, char[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveCharacterArrayVerifier assertThat(char[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an array.\n" +
				"\t *\n" +
				"\t * @param <E>    the type of elements in the array\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <E> ArrayVerifier<E> requireThat(E[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Object[])} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param <E>    the type of elements in the array\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <E> ArrayVerifier<E> assertThat(E[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Comparable}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of objects that the value may be compared to\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, " +
				"String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Comparable)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of objects that the value may be compared to\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual, " +
				"String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code byte}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, byte)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveNumberVerifier<Byte> assertThat(byte actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code short}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveNumberVerifier<Short> requireThat(short actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, short)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveNumberVerifier<Short> assertThat(short actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code int}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveIntegerVerifier<Integer> requireThat(int actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, int)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveIntegerVerifier<Integer> assertThat(int actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code Integer}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static IntegerVerifier<Integer> requireThat(Integer actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Integer)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static IntegerVerifier<Integer> assertThat(Integer actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code long}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveIntegerVerifier<Long> requireThat(long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, long)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveIntegerVerifier<Long> assertThat(long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Long}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static IntegerVerifier<Long> requireThat(Long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Long)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static IntegerVerifier<Long> assertThat(Long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code float}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, float)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveFloatingPointVerifier<Float> assertThat(float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code double}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, double)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveFloatingPointVerifier<Double> assertThat(double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code boolean}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveBooleanVerifier requireThat(boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, boolean)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveBooleanVerifier assertThat(boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, char)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveCharacterVerifier assertThat(char actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code char}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PrimitiveCharacterVerifier requireThat(char actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Number}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the number\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(" +
				"T actual,\n" +
				"\t                                                                                       " +
				"String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Number)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the number\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual, " +
				"String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Boolean}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static BooleanVerifier requireThat(Boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Boolean)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static BooleanVerifier assertThat(Boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Float}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static FloatingPointVerifier<Float> requireThat(Float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Float)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static FloatingPointVerifier<Float> assertThat(Float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Double}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static FloatingPointVerifier<Double> requireThat(Double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Double)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static FloatingPointVerifier<Double> assertThat(Double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code BigDecimal}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static BigDecimalVerifier requireThat(BigDecimal actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, BigDecimal)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static BigDecimalVerifier assertThat(BigDecimal actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Map}.\n" +
				"\t *\n" +
				"\t * @param <K>    the type of key in the map\n" +
				"\t * @param <V>    the type of value in the map\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Map)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <K>    the type of key in the map\n" +
				"\t * @param <V>    the type of value in the map\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Path}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PathVerifier requireThat(Path actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Path)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static PathVerifier assertThat(Path actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code String}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static StringVerifier requireThat(String actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static StringVerifier assertThat(String actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Uri}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static UriVerifier requireThat(URI actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, URI)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static UriVerifier assertThat(URI actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Class}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of class\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T> ClassVerifier<T> requireThat(Class<T> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Class)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of class\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static <T> ClassVerifier<T> assertThat(Class<T> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code Optional}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static OptionalVerifier requireThat(Optional<?> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Optional)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static OptionalVerifier assertThat(Optional<?> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code InetAddress}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static InetAddressVerifier requireThat(InetAddress actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, InetAddress)} but does nothing if assertions are " +
				"disabled for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic static InetAddressVerifier assertThat(InetAddress actual, String name)\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.assertThat(actual, name);\n" +
				"\t}\n");
			if (guavaEnabled)
			{
				writer.write("\n" +
					"\t/**\n" +
					"\t * Verifies the requirements of a {@code Multimap}.\n" +
					"\t *\n" +
					"\t * @param <K>    the type of key in the multimap\n" +
					"\t * @param <V>    the type of value in the multimap\n" +
					"\t * @param actual the actual value\n" +
					"\t * @param name   the name of the value\n" +
					"\t * @return a verifier for the value\n" +
					"\t * @throws NullPointerException     if {@code name} is null\n" +
					"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
					"\t */\n" +
					"\tpublic static <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)\n" +
					"\t{\n" +
					"\t\treturn GUAVA_VERIFIER.requireThat(actual, name);\n" +
					"\t}\n" +
					"\n" +
					"\t/**\n" +
					"\t * Same as {@link #requireThat(String, Multimap)} but does nothing if assertions are " +
					"disabled.\n" +
					"\t *\n" +
					"\t * @param <K>    the type of key in the multimap\n" +
					"\t * @param <V>    the type of value in the multimap\n" +
					"\t * @param actual the actual value\n" +
					"\t * @param name   the name of the value\n" +
					"\t * @return a verifier for the value\n" +
					"\t * @throws NullPointerException     if name is null\n" +
					"\t * @throws IllegalArgumentException if name is empty\n" +
					"\t */\n" +
					"\tpublic static <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)\n" +
					"\t{\n" +
					"\t\treturn GUAVA_VERIFIER.assertThat(actual, name);\n" +
					"\t}\n" +
					"\n");
			}
			writer.write("\n" +
				"\t/**\n" +
				"\t * @return the global configuration shared by all verifiers\n" +
				"\t */\n" +
				"\tpublic static GlobalConfigurable getGlobalConfiguration()\n" +
				"\t{\n" +
				"\t\treturn JAVA_VERIFIER.getGlobalConfiguration();\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Prevent construction.\n" +
				"\t */\n" +
				"\tprivate DefaultRequirements()\n" +
				"\t{\n" +
				"\t}\n" +
				"}\n");
			writer.close();

			String newValue = sw.toString();
			if (oldValue.equals(newValue))
				return false;
			try (FileWriter fw = new FileWriter(path.toFile()))
			{
				fw.write(newValue);
			}
		}
		return true;
	}

	/**
	 * Returns the path of the {@code Requirements.java} file.
	 *
	 * @param rootPackage the path of the root package
	 * @return the path of the {@code Requirements.java} file
	 */
	public Path getRequirementsPath(Path rootPackage)
	{
		return rootPackage.resolve("org/bitbucket/cowwoc/requirements/Requirements.java");
	}

	/**
	 * Generates {@code org.bitbucket.cowwoc.requirements.Requirements}.
	 *
	 * @param path the path of the file
	 * @return true if the file was updated
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	public boolean writeRequirements(Path path) throws IOException
	{
		Files.createDirectories(path.getParent());

		String oldValue;
		try
		{
			oldValue = Files.readString(path);
		}
		catch (NoSuchFileException unused)
		{
			oldValue = "";
		}
		try (StringWriter sw = new StringWriter();
		     BufferedWriter writer = new BufferedWriter(sw))
		{
			writer.write("/*\n" +
				" * Copyright 2013 Gili Tzabari.\n" +
				" * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0\n" +
				" */\n" +
				"package org.bitbucket.cowwoc.requirements;\n" +
				"\n");
			if (guavaEnabled)
			{
				writer.write("import com.google.common.collect.Multimap;\n" +
					"import org.bitbucket.cowwoc.requirements.guava.DefaultGuavaVerifier;\n" +
					"import org.bitbucket.cowwoc.requirements.guava.GuavaVerifier;\n" +
					"import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;\n");
			}
			writer.write("import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.BooleanVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.ClassVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.Configurable;\n" +
				"import org.bitbucket.cowwoc.requirements.java.Configuration;\n" +
				"import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.GlobalConfigurable;\n" +
				"import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.IntegerVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.JavaVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.MapVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.NumberVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.ObjectVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PathVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.StringVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.UriVerifier;\n" +
				"import org.bitbucket.cowwoc.requirements.java.DefaultJavaVerifier;\n");
			if (exportScope)
				writer.write("import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;\n");
			writer.write("\n" +
				"import java.math.BigDecimal;\n" +
				"import java.net.InetAddress;\n" +
				"import java.net.URI;\n" +
				"import java.nio.file.Path;\n" +
				"import java.util.Collection;\n" +
				"import java.util.Map;\n" +
				"import java.util.Optional;\n" +
				"import java.util.function.Function;\n" +
				"\n" +
				"/**\n" +
				" * An entry point for verifying API requirements.\n" +
				" * <p>\n" +
				" * This class holds its own configuration whereas {@link DefaultRequirements} always uses the\n" +
				" * {@link GlobalConfigurable global configuration}.\n" +
				" * <p>\n" +
				" * The assertion status of the {@link Configuration} class determines whether " +
				"{@code assertThat()} carries\n" +
				" * out a verification or does nothing.\n" +
				" * <p>\n" +
				" * This class is immutable.\n" +
				" *\n" +
				" * @see DefaultRequirements\n" +
				" * @see JavaVerifier\n");
			if (guavaEnabled)
				writer.write(" * @see GuavaVerifier\n");
			writer.write(" */\n" +
				"public final class Requirements implements Configurable\n" +
				"{\n" +
				"\tprivate final JavaVerifier javaVerifier;\n");
			if (guavaEnabled)
				writer.write("\tprivate final GuavaVerifier guavaVerifier;\n");
			writer.write("\n" +
				"\tpublic Requirements()\n" +
				"\t{\n" +
				"\t\tthis.javaVerifier = new DefaultJavaVerifier();\n");
			if (guavaEnabled)
				writer.write("\t\tthis.guavaVerifier = new DefaultGuavaVerifier();\n");
			writer.write("\t}\n");
			if (exportScope)
			{
				writer.write("\n" +
					"\t/**\n" +
					"\t * This constructor is meant to be used by automated tests, not by users.\n" +
					"\t *\n" +
					"\t * @param scope the application configuration\n" +
					"\t * @throws AssertionError if any of the arguments are null\n" +
					"\t */\n" +
					"\tpublic Requirements(ApplicationScope scope)\n" +
					"\t{\n" +
					"\t\tassert (scope != null) : \"scope may not be null\";\n" +
					"\t\tthis.javaVerifier = new DefaultJavaVerifier(scope);\n");
				if (guavaEnabled)
					writer.write("\t\tthis.guavaVerifier = new DefaultGuavaVerifier(scope);\n");
				writer.write("\t}\n");
			}
			writer.write("\n" +
				"\t/**\n" +
				"\t * @param javaVerifier  the Java verifier to delegate to\n");
			if (guavaEnabled)
				writer.write("\t * @param guavaVerifier the Guava verifier to delegate to\n");
			writer.write("\t * @throws AssertionError if any of the arguments are null\n" +
				"\t */\n" +
				"\tprivate Requirements(JavaVerifier javaVerifier");
			if (guavaEnabled)
				writer.write(", GuavaVerifier guavaVerifier");
			writer.write(")\n" +
				"\t{\n" +
				"\t\tassert (javaVerifier != null) : \"javaVerifier may not be null\";\n");
			if (guavaEnabled)
				writer.write("\t\tassert (guavaVerifier != null) : \"guavaVerifier may not be null\";\n");
			writer.write("\t\tthis.javaVerifier = javaVerifier;\n");
			if (guavaEnabled)
				writer.write("\t\tthis.guavaVerifier = guavaVerifier;\n");
			writer.write("\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * @return true if assertions are enabled for this class\n" +
				"\t */\n" +
				"\tpublic boolean assertionsAreEnabled()\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertionsAreEnabled();\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements withAssertionsEnabled()\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withAssertionsEnabled();\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements withAssertionsDisabled()\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withAssertionsDisabled();\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements withException(Class<? extends RuntimeException> exception)\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withException(exception);\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements withDefaultException()\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withDefaultException();\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements withDiff()\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withDiff();\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements withoutDiff()\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withoutDiff();\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements addContext(String name, Object value)\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.addContext(name, value);\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic <T> Requirements withStringConverter(Class<T> type, Function<T, String> converter)\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withStringConverter(type, converter);\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic <T> Requirements withoutStringConverter(Class<T> type)\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tConfiguration newConfig = config.withoutStringConverter(type);\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Requirements withConfiguration(Configuration newConfig)\n" +
				"\t{\n" +
				"\t\tConfiguration config = javaVerifier.getConfiguration();\n" +
				"\t\tif (newConfig.equals(config))\n" +
				"\t\t\treturn this;\n" +
				"\t\tJavaVerifier newJavaVerifier = javaVerifier.withConfiguration(newConfig);\n");
			if (guavaEnabled)
				writer.write("\t\tGuavaVerifier newGuavaVerifier = guavaVerifier.withConfiguration(newConfig);\n");
			writer.write("\t\treturn new Requirements(newJavaVerifier");
			if (guavaEnabled)
				writer.write(", newGuavaVerifier");
			writer.write(");\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Configuration getConfiguration()\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.getConfiguration();\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic GlobalConfigurable getGlobalConfiguration()\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.getGlobalConfiguration();\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code Object}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the value\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T> ObjectVerifier<T> requireThat(T actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Object)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the value\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T> ObjectVerifier<T> assertThat(T actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Collection}.\n" +
				"\t *\n" +
				"\t * @param <C>    the type of the collection\n" +
				"\t * @param <E>    the type of elements in the collection\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(C actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Collection)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param <C>    the type of the collection\n" +
				"\t * @param <E>    the type of elements in the collection\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(C actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code byte} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveByteArrayVerifier requireThat(byte[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, byte[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveByteArrayVerifier assertThat(byte[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code short} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveShortArrayVerifier requireThat(short[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, short[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveShortArrayVerifier assertThat(short[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code int} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveIntegerArrayVerifier requireThat(int[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, int[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveIntegerArrayVerifier assertThat(int[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code long} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveLongArrayVerifier requireThat(long[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, long[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveLongArrayVerifier assertThat(long[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code float} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveFloatArrayVerifier requireThat(float[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, float[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveFloatArrayVerifier assertThat(float[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code double} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveDoubleArrayVerifier requireThat(double[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, double[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveDoubleArrayVerifier assertThat(double[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code boolean} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveBooleanArrayVerifier requireThat(boolean[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, boolean[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveBooleanArrayVerifier assertThat(boolean[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a primitive {@code char} array.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveCharacterArrayVerifier requireThat(char[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, char[])} but does nothing if assertions are disabled.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if name is null\n" +
				"\t * @throws IllegalArgumentException if name is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveCharacterArrayVerifier assertThat(char[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an array.\n" +
				"\t *\n" +
				"\t * @param <E>    the type of elements in the array\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <E> ArrayVerifier<E> requireThat(E[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Object[])} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param <E>    the type of elements in the array\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <E> ArrayVerifier<E> assertThat(E[] actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Comparable}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of objects that the value may be compared to\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Comparable)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of objects that the value may be compared to\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual, String " +
				"name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code byte}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, byte)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveNumberVerifier<Byte> assertThat(byte actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code short}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveNumberVerifier<Short> requireThat(short actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, short)} but does nothing if assertions are disabled " +
				"for this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveNumberVerifier<Short> assertThat(short actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code int}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveIntegerVerifier<Integer> requireThat(int actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, int)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveIntegerVerifier<Integer> assertThat(int actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code Integer}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic IntegerVerifier<Integer> requireThat(Integer actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Integer)} but does nothing if assertions are disabled " +
				"for this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic IntegerVerifier<Integer> assertThat(Integer actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code long}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveIntegerVerifier<Long> requireThat(long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, long)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveIntegerVerifier<Long> assertThat(long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Long}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic IntegerVerifier<Long> requireThat(Long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Long)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic IntegerVerifier<Long> assertThat(Long actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code float}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, float)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveFloatingPointVerifier<Float> assertThat(float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code double}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, double)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveFloatingPointVerifier<Double> assertThat(double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code boolean}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveBooleanVerifier requireThat(boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, boolean)} but does nothing if assertions are disabled " +
				"for this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveBooleanVerifier assertThat(boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, char)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveCharacterVerifier assertThat(char actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code char}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PrimitiveCharacterVerifier requireThat(char actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Number}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the number\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual, " +
				"String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Number)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of the number\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual, " +
				"String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Boolean}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic BooleanVerifier requireThat(Boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Boolean)} but does nothing if assertions are disabled " +
				"for this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic BooleanVerifier assertThat(Boolean actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Float}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic FloatingPointVerifier<Float> requireThat(Float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Float)} but does nothing if assertions are disabled " +
				"for this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic FloatingPointVerifier<Float> assertThat(Float actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Double}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic FloatingPointVerifier<Double> requireThat(Double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Double)} but does nothing if assertions are disabled " +
				"for this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic FloatingPointVerifier<Double> assertThat(Double actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code BigDecimal}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic BigDecimalVerifier requireThat(BigDecimal actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, BigDecimal)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic BigDecimalVerifier assertThat(BigDecimal actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Map}.\n" +
				"\t *\n" +
				"\t * @param <K>    the type of key in the map\n" +
				"\t * @param <V>    the type of value in the map\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Map)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <K>    the type of key in the map\n" +
				"\t * @param <V>    the type of value in the map\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Path}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PathVerifier requireThat(Path actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Path)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic PathVerifier assertThat(Path actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code String}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic StringVerifier requireThat(String actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic StringVerifier assertThat(String actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Uri}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic UriVerifier requireThat(URI actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, URI)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic UriVerifier assertThat(URI actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of a {@code Class}.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of class\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T> ClassVerifier<T> requireThat(Class<T> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Class)} but does nothing if assertions are disabled for " +
				"this class.\n" +
				"\t *\n" +
				"\t * @param <T>    the type of class\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic <T> ClassVerifier<T> assertThat(Class<T> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code Optional}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic OptionalVerifier requireThat(Optional<?> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, Optional)} but does nothing if assertions are disabled " +
				"for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic OptionalVerifier assertThat(Optional<?> actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Verifies the requirements of an {@code InetAddress}.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic InetAddressVerifier requireThat(InetAddress actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.requireThat(actual, name);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Same as {@link #requireThat(String, InetAddress)} but does nothing if assertions are " +
				"disabled for this\n" +
				"\t * class.\n" +
				"\t *\n" +
				"\t * @param actual the actual value\n" +
				"\t * @param name   the name of the value\n" +
				"\t * @return a verifier for the value\n" +
				"\t * @throws NullPointerException     if {@code name} is null\n" +
				"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
				"\t */\n" +
				"\tpublic InetAddressVerifier assertThat(InetAddress actual, String name)\n" +
				"\t{\n" +
				"\t\treturn javaVerifier.assertThat(actual, name);\n" +
				"\t}\n");
			if (guavaEnabled)
			{
				writer.write("\n" +
					"\t/**\n" +
					"\t * Verifies the requirements of a {@code Multimap}.\n" +
					"\t *\n" +
					"\t * @param <K>    the type of key in the multimap\n" +
					"\t * @param <V>    the type of value in the multimap\n" +
					"\t * @param actual the actual value\n" +
					"\t * @param name   the name of the value\n" +
					"\t * @return a verifier for the value\n" +
					"\t * @throws NullPointerException     if {@code name} is null\n" +
					"\t * @throws IllegalArgumentException if {@code name} is empty\n" +
					"\t */\n" +
					"\tpublic <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)\n" +
					"\t{\n" +
					"\t\treturn guavaVerifier.requireThat(actual, name);\n" +
					"\t}\n" +
					"\n" +
					"\t/**\n" +
					"\t * Same as {@link #requireThat(String, Multimap)} but does nothing if assertions are " +
					"disabled.\n" +
					"\t *\n" +
					"\t * @param <K>    the type of key in the multimap\n" +
					"\t * @param <V>    the type of value in the multimap\n" +
					"\t * @param actual the actual value\n" +
					"\t * @param name   the name of the value\n" +
					"\t * @return a verifier for the value\n" +
					"\t * @throws NullPointerException     if name is null\n" +
					"\t * @throws IllegalArgumentException if name is empty\n" +
					"\t */\n" +
					"\tpublic <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)\n" +
					"\t{\n" +
					"\t\treturn guavaVerifier.assertThat(actual, name);\n" +
					"\t}\n");
			}
			writer.write("}\n");
			writer.close();

			String newValue = sw.toString();
			if (oldValue.equals(newValue))
				return false;
			try (FileWriter fw = new FileWriter(path.toFile()))
			{
				fw.write(newValue);
			}
		}
		return true;
	}
}
