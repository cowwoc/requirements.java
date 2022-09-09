/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.generator;

import com.github.cowwoc.requirements.generator.internal.secret.SharedSecrets;
import com.github.cowwoc.requirements.generator.internal.util.Generators;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.printer.configuration.DefaultConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration.ConfigOption;
import com.github.javaparser.printer.configuration.PrinterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Generates {@code Requirements} and {@code DefaultRequirements} endpoints. The contents of these classes
 * depend on which plugins are enabled.
 */
public final class ApiGenerator
{
	static
	{
		SharedSecrets.INSTANCE.secretApiGenerator = ApiGenerator::exportScope;
	}

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
			System.err.println("Where <root> is the directory corresponding to the Java root package");
			System.exit(1);
		}

		Path directory = Paths.get(args[0]);
		ApiGenerator generator = new ApiGenerator(ClassLoader.getSystemClassLoader());
		generator.writeTo(directory);
	}

	private final ClassLoader classLoader;
	private final PrinterConfiguration defaultFormatter = new DefaultPrinterConfiguration().addOption(
		new DefaultConfigurationOption(ConfigOption.END_OF_LINE_CHARACTER, "\n"));
	private boolean guavaEnabled;
	private boolean exportScope;
	private final Logger log = LoggerFactory.getLogger(ApiGenerator.class);

	/**
	 * Creates a new ApiGenerator.
	 *
	 * @param classLoader the class loader used to resolve plugin classes
	 * @throws NullPointerException if {@code classLoader} is null
	 */
	public ApiGenerator(ClassLoader classLoader)
	{
		if (classLoader == null)
			throw new IllegalArgumentException("classLoader may not be null");
		this.classLoader = classLoader;
	}

	/**
	 * Writes all API endpoints and logs the result.
	 *
	 * @param directory the directory to generate files into
	 * @throws NullPointerException if any of the arguments are null
	 * @throws IOException          if an I/O error occurs
	 */
	public void writeTo(Path directory) throws IOException
	{
		if (directory == null)
			throw new NullPointerException("directory may not be null");
		Path defaultRequirements = getDefaultRequirementsPath(directory);
		if (writeDefaultRequirements(defaultRequirements))
			log.info("Generated {}", defaultRequirements);
		else
			log.info("Skipped {} because it was up-to-date", defaultRequirements);

		Path requirements = getRequirementsPath(directory);
		if (writeRequirements(requirements))
			log.info("Generated {}", requirements);
		else
			log.info("Skipped {} because it was up-to-date", requirements);
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
	private Path getDefaultRequirementsPath(Path rootPackage)
	{
		return rootPackage.resolve("com/github/cowwoc/requirements/DefaultRequirements.java");
	}

	/**
	 * Generates {@code com.github.cowwoc.requirements.DefaultRequirements}.
	 *
	 * @param path the path of the file
	 * @return true if the file was updated
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	private boolean writeDefaultRequirements(Path path) throws IOException
	{
		StringBuilder out = new StringBuilder();
		List<CompilationUnit> plugins = getPlugins();
		addCopyright(out);
		addPackage(out);
		addImports(plugins, out);
		out.append("""
			import com.github.cowwoc.requirements.java.ThreadRequirements;

			import java.util.function.Consumer;
			import java.util.function.Function;

			/**
			 * Verifies requirements using the default {@link Configuration configuration}. Any method that exposes
			 * a {@code Requirements} instance is returning an independent copy. Any modifications applied to that
			 * copy are thrown away and do not affect the configuration of this class.
			 * <p>
			 * To retain configuration changes, instantiate and reuse the same {@code Requirements} instance across
			 * multiple runs.
			 * <p>
			 * <b>Thread-safety</b>: This class is thread-safe.
			 *
			 * @see Requirements
			 * @see JavaRequirements
			""");
		if (guavaEnabled)
			out.append(" * @see GuavaRequirements\n");
		out.append("""
			 */
			public final class DefaultRequirements
			{
			\tprivate static final Requirements REQUIREMENTS = new Requirements();

			\t/**
			\t * Returns true if assertions are enabled for this class.
			\t *
			\t * @return true if assertions are enabled for this class
			\t */
			\tpublic static boolean assertionsAreEnabled()
			\t{
			\t\treturn REQUIREMENTS.assertionsAreEnabled();
			\t}

			\t/**
			\t * Indicates that {@code assertThat()} should verify requirements.
			\t *
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t */
			\tpublic static Requirements withAssertionsEnabled()
			\t{
			\t\treturn REQUIREMENTS.copy().withAssertionsEnabled();
			\t}

			\t/**
			\t * Indicates that {@code assertThat()} shouldn't do anything.
			\t *
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t */
			\tpublic static Requirements withAssertionsDisabled()
			\t{
			\t\treturn REQUIREMENTS.copy().withAssertionsDisabled();
			\t}

			\t/**
			\t * Indicates if exceptions should show the difference between the actual and expected values.
			\t *
			\t * @return true by default
			\t */
			\tpublic static boolean isDiffEnabled()
			\t{
			\t\treturn REQUIREMENTS.isDiffEnabled();
			\t}

			\t/**
			\t * Indicates that exceptions should show the difference between the actual and expected values.
			\t *
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t */
			\tpublic static Requirements withDiff()
			\t{
			\t\treturn REQUIREMENTS.copy().withDiff();
			\t}

			\t/**
			\t * Indicates that exceptions should not show the difference between the actual and expected
			\t * values.
			\t *
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t*/
			\tpublic static Requirements withoutDiff()
			\t{
			\t\treturn REQUIREMENTS.copy().withDiff();
			\t}

			\t/**
			\t * Indicates if stack trace references to this library should be removed.
			\t *
			\t * @return {@code true} by default
			\t * @see #withCleanStackTrace()
			\t * @see #withoutCleanStackTrace()
			\t */
			\tpublic static boolean isCleanStackTrace()
			\t{
			\t\treturn REQUIREMENTS.isCleanStackTrace();
			\t}

			\t/**
			\t * Indicates that stack trace references to this library should be removed.
			\t *
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t * @see #isCleanStackTrace()
			\t */
			\tpublic static Requirements withCleanStackTrace()
			\t{
			\t\treturn REQUIREMENTS.copy().withCleanStackTrace();
			\t}

			\t/**
			\t * Indicates that stack trace references to this library should be kept.
			\t *
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t * @see #isCleanStackTrace()
			\t */
			\tpublic static Requirements withoutCleanStackTrace()
			\t{
			\t\treturn REQUIREMENTS.copy().withoutCleanStackTrace();
			\t}

			\t/**
			\t * Returns an unmodifiable map to append to the exception message.
			\t *
			\t * @return an empty map by default
			\t * @see #withContext(String, Object)
			\t */
			\tpublic static Map<String, Object> getContext()
			\t{
			\t\treturn REQUIREMENTS.getContext();
			\t}

			\t/**
			\t * Adds or updates contextual information associated with the exception message. Overrides any values
			\t * associated with the {@code name} at the {@link ThreadRequirements} level.
			\t *
			\t * @param name  the name of the parameter
			\t * @param value the value of the parameter
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t * @throws NullPointerException if {@code name} is null
			\t */
			\tpublic static Requirements withContext(String name, Object value)
			\t{
			\t\treturn REQUIREMENTS.copy().withContext(name, value);
			\t}

			\t/**
			\t * Returns the contextual information associated with this configuration.
			\t *
			\t * @param message the exception message ({@code null} if absent)
			\t * @return the contextual information associated with this configuration
			\t */
			\tpublic static String getContextMessage(String message)
			\t{
			\t\treturn REQUIREMENTS.getContextMessage(message);
			\t}

			\t/**
			\t * Returns the {@code String} representation of an object. By default, custom handlers are provided for
			\t * arrays, {@code Integer}, {@code Long}, {@code BigDecimal}, and {@code Path}.
			\t *
			\t * @param value a value
			\t * @return the {@code String} representation of the value
			\t * @see #withStringConverter(Class, Function)
			\t */
			\tpublic static String toString(Object value)
			\t{
			\t\treturn REQUIREMENTS.toString(value);
			\t}

			\t/**
			\t * Indicates that a function should be used to convert an object to a String.
			\t * <p>
			\t * Please note that {@code type} must be an exact match (e.g. configuring a converter for
			\t * {@code Set} will not match values of type {@code HashSet}).
			\t *
			\t * @param <T>       the type of object being converted
			\t * @param type      the type of object being converted (non-primitive arrays are mapped to
			\t *                  {@code Object[].class})
			\t * @param converter a function that converts an object of the specified type to a String
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t * @throws NullPointerException if any of the arguments are null
			\t */
			\tpublic static <T> Requirements withStringConverter(Class<T> type, Function<T, String> converter)
			\t{
			\t\treturn REQUIREMENTS.copy().withStringConverter(type, converter);
			\t}

			\t/**
			\t * Indicates that an object's {@code toString()} method should be used to convert it to a String.
			\t *
			\t * @param <T>  the type of object being converted
			\t * @param type the type of object being converted
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t * @throws NullPointerException if {@code type} is null
			\t */
			\tpublic static <T> Requirements withoutStringConverter(Class<T> type)
			\t{
			\t\treturn REQUIREMENTS.copy().withoutStringConverter(type);
			\t}

			\t/**
			\t * Replaces the configuration.
			\t *
			\t * @param configuration a new configuration
			\t * @return a new {@code Requirements} object that operates independently of the original instance
			\t * @throws NullPointerException if {@code configuration} is null
			\t */
			\tpublic static Requirements withConfiguration(Configuration configuration)
			\t{
			\t\treturn REQUIREMENTS.copy().withConfiguration(configuration);
			\t}

			\t/**
			\t * Verifies requirements only if {@link Configuration#assertionsAreEnabled() assertions are enabled}.
			\t *
			\t * @param <V>          the return value of the operation
			\t * @param requirements the requirements to verify
			\t * @return the return value of the operation, or {@code null} if assertions are disabled
			\t * @throws NullPointerException if {@code requirements} is null
			\t * @see #assertThat(Consumer)
			\t */
			\tpublic static <V> V assertThatAndReturn(Function<Requirements, V> requirements)
			\t{
			\t\trequireThat(requirements, "requirements").isNotNull();
			\t\tif (!assertionsAreEnabled())
			\t\t\treturn null;
			\t\tRequirements copy = REQUIREMENTS.copy();
			\t\treturn requirements.apply(copy);
			\t}

			\t/**
			\t * Verifies requirements only if assertions are enabled.
			\t *
			\t * @param requirements the requirements to verify
			\t * @throws NullPointerException if {@code requirements} is null
			\t * @see #assertThatAndReturn(Function)
			\t */
			\tpublic static void assertThat(Consumer<Requirements> requirements)
			\t{
			\t\trequireThat(requirements, "requirements").isNotNull();
			\t\tif (!assertionsAreEnabled())
			\t\t\treturn;
			\t\tRequirements copy = REQUIREMENTS.copy();
			\t\trequirements.accept(copy);
			\t}
			""");
		appendPluginMethods(plugins, true, out);
		out.append("""

			\t/**
			\t * Prevent construction.
			\t */
			\tprivate DefaultRequirements()
			\t{
			\t}
			}""");
		return Generators.writeIfChanged(path, out.toString());
	}

	/**
	 * @return the AST of enabled plugins
	 * @throws IOException if an I/O error occurs
	 */
	private List<CompilationUnit> getPlugins() throws IOException
	{
		List<String> plugins = new ArrayList<>(2);
		plugins.add("java");
		if (guavaEnabled)
			plugins.add("guava");
		return getPlugins(plugins);
	}

	/**
	 * @param plugins the list of enabled plugins
	 * @return the AST of enabled plugins
	 * @throws IOException if an I/O error occurs
	 */
	private List<CompilationUnit> getPlugins(List<String> plugins) throws IOException
	{
		List<CompilationUnit> result = new ArrayList<>();
		for (String plugin : plugins)
		{
			result.add(loadJavaFile("com.github.cowwoc.requirements." + plugin + "." +
				plugin.substring(0, 1).toUpperCase(Locale.US) + plugin.substring(1) + "Requirements"));
		}
		return result;
	}

	/**
	 * @param fullyQualifiedName a fully-qualified class name
	 * @return the AST of enabled plugins
	 * @throws IOException if an I/O error occurs
	 */
	private CompilationUnit loadJavaFile(String fullyQualifiedName) throws IOException
	{
		fullyQualifiedName = fullyQualifiedName.replaceAll("\\.", "/");
		String path = "/" + fullyQualifiedName + ".java";
		try (InputStream in = getClass().getResourceAsStream(path))
		{
			if (in == null)
				throw new IOException("Could not find " + path + " on classpath");
			return StaticJavaParser.parse(in);
		}
		catch (ParseProblemException e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Delegates calls to a plugin method.
	 *
	 * @param plugins  the list of enabled plugins
	 * @param isStatic true if the delegate method is static
	 * @param out      the buffer to write into
	 */
	private void appendPluginMethods(List<CompilationUnit> plugins, boolean isStatic, StringBuilder out)
	{
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, isStatic);

			for (MethodDeclaration method : pluginClass.getMethods())
			{
				switch (method.getNameAsString())
				{
					case "requireThat":
					case "validateThat":
						break;
					default:
						continue;
				}

				out.append("\n");
				if (isStatic)
				{
					// Copy Javadoc
					method.getJavadoc().ifPresent(javadoc ->
					{
						StringBuilder text = new StringBuilder(method.getJavadoc().get().toComment().
							toString(defaultFormatter));
						// Increase indentation
						text.insert(0, "\t");
						int index = 0;
						while (true)
						{
							index = text.indexOf("\n", index);
							if (index == -1)
								break;
							++index;
							text.insert(index, '\t');
							++index;
						}
						if (text.length() > 0 && text.charAt(text.length() - 1) == '\t')
							text.delete(text.length() - 1, text.length());
						out.append(text);
					});
				}
				else
					out.append("\t@Override\n");
				out.append("\t@CheckReturnValue\n");
				// Modifiers
				method.addModifier(Keyword.PUBLIC);
				if (isStatic)
					method.addModifier(Keyword.STATIC);
				StringJoiner joiner = new StringJoiner(" ", "", " ");
				for (Modifier modifier : method.getModifiers())
					joiner.add(modifier.getKeyword().asString());
				out.append("\t" + joiner);

				// Type parameters
				joiner = new StringJoiner(", ", "<", "> ");
				joiner.setEmptyValue("");
				for (TypeParameter parameter : method.getTypeParameters())
					joiner.add(parameter.asString());

				// Return type and method name
				out.append(joiner).append(method.getType() + " " + method.getNameAsString());

				// Arguments
				joiner = new StringJoiner(", ", "(", ")");
				for (Parameter parameter : method.getParameters())
					joiner.add(parameter.getTypeAsString() + " " + parameter.getNameAsString());
				out.append(joiner + "\n");
				out.append("\t{\n");
				out.append("\t\treturn " + delegateName + "." + method.getNameAsString() + "(actual, name);\n");
				out.append("\t}\n");
			}
		}
	}

	/**
	 * @param plugin   the type of the plugin
	 * @param isStatic true if the delegate field is static
	 * @return the name of the delegate field
	 */
	private String getDelegateName(ClassOrInterfaceDeclaration plugin, boolean isStatic)
	{
		String pluginName = plugin.getNameAsString();
		if (!isStatic)
			return Character.toLowerCase(pluginName.charAt(0)) + pluginName.substring(1);
		return "REQUIREMENTS";
	}

	/**
	 * @param plugin the type of the plugin
	 * @return a function that returns a new instance of the plugin
	 */
	private String createDelegate(ClassOrInterfaceDeclaration plugin)
	{
		String pluginName = plugin.getNameAsString();
		int requirementsIndex = pluginName.indexOf("Requirements");
		String pluginType = pluginName.substring(0, requirementsIndex);
		return "this." + getDelegateName(plugin, false) + " = " + pluginType + "Secrets.INSTANCE" +
			".createRequirements(scope);\n";
	}

	/**
	 * Returns the path of the {@code Requirements.java} file.
	 *
	 * @param rootPackage the path of the root package
	 * @return the path of the {@code Requirements.java} file
	 */
	private Path getRequirementsPath(Path rootPackage)
	{
		return rootPackage.resolve("com/github/cowwoc/requirements/Requirements.java");
	}

	/**
	 * Generates {@code com.github.cowwoc.requirements.Requirements}.
	 *
	 * @param path the path of the file
	 * @return true if the file was updated
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	private boolean writeRequirements(Path path) throws IOException
	{
		StringBuilder out = new StringBuilder();
		List<CompilationUnit> plugins = getPlugins();
		addCopyright(out);
		addPackage(out);
		addImports(plugins, out);
		if (exportScope)
		{
			out.append("""
				import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
				import com.github.cowwoc.requirements.java.internal.secrets.JavaSecrets;
				import com.github.cowwoc.requirements.guava.internal.secrets.GuavaSecrets;

				""");
		}
		out.append("""
			import java.math.BigDecimal;
			import java.net.InetAddress;
			import java.net.URI;
			import java.nio.file.Path;
			import java.util.Collection;
			import java.util.Map;
			import java.util.Optional;
			import java.util.function.Consumer;
			import java.util.function.Function;

			""");
		out.append("""
			/**
			 * Verifies requirements using a thread-specific {@link Configuration configuration}.
			 * <p>
			 * <b>Thread-safety</b>: This class is <b>not</b> thread-safe.
			 * @see DefaultRequirements
			 * @see JavaRequirements
			""");
		if (guavaEnabled)
			out.append(" * @see GuavaRequirements\n");
		out.append(" */\n" +
			"public final class Requirements implements " + getInterfaces(plugins) +
			"\n" +
			"{\n");
		appendDelegateFields(plugins, out);
		out.append("\n");
		appendDefaultConstructor(plugins, out);
		out.append("\n");
		appendDelegateConstructor(plugins, out);
		if (exportScope)
		{
			out.append("""

				\t/**
				\t * This constructor is meant to be used by automated tests, not by users.
				\t *
				\t * @param scope the application configuration
				\t * @throws AssertionError if any of the arguments are null
				\t */
				\tpublic Requirements(ApplicationScope scope)
				\t{
				\t\tassert (scope != null) : "scope may not be null";
				""");
			for (CompilationUnit plugin : plugins)
			{
				ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
				out.append("\t\t").append(createDelegate(pluginClass));
			}
			out.append("\t}\n");
		}
		out.append("""

			\t/**
			\t * Returns a copy of this configuration.
			\t *
			\t * @return a copy of this configuration
			\t */
			\tpublic Requirements copy()
			\t{
			""");

		out.append("\t\treturn new Requirements");
		StringJoiner joiner = new StringJoiner(", ", "(", ");\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String pluginName = pluginClass.getNameAsString();
			joiner.add(Character.toLowerCase(pluginName.charAt(0)) + pluginName.substring(1) + ".copy()");
		}
		out.append(joiner);

		out.append("""
			\t}

			\t@Override
			\tpublic boolean assertionsAreEnabled()
			\t{
			\t\treturn javaRequirements.assertionsAreEnabled();
			\t}

			\t@Override
			\tpublic Requirements withAssertionsEnabled()
			\t{
			""");
		appendConfigurationUpdate(plugins, "withAssertionsEnabled()", out);
		out.append("""
			\t}

			\t@Override
			\tpublic Requirements withAssertionsDisabled()
			\t{
			""");
		appendConfigurationUpdate(plugins, "withAssertionsDisabled()", out);
		out.append("""
			\t}

			\t@Override
			\tpublic boolean isDiffEnabled()
			\t{
			\t\treturn javaRequirements.isDiffEnabled();
			\t}

			\t@Override
			\tpublic Requirements withDiff()
			\t{
			""");
		appendConfigurationUpdate(plugins, "withDiff()", out);
		out.append("""
			\t}

			\t@Override
			\tpublic Requirements withoutDiff()
			\t{
			""");
		appendConfigurationUpdate(plugins, "withoutDiff()", out);
		out.append("""
			\t}

			\t@Override
			\tpublic boolean isCleanStackTrace()
			\t{
			\t\treturn javaRequirements.isCleanStackTrace();
			\t}

			\t@Override
			\tpublic Requirements withCleanStackTrace()
			\t{
			""");
		appendConfigurationUpdate(plugins, "withCleanStackTrace()", out);
		out.append("""
			\t}

			\t@Override
			\tpublic Requirements withoutCleanStackTrace()
			\t{
			""");
		appendConfigurationUpdate(plugins, "withoutCleanStackTrace()", out);
		out.append("""
			\t}

			\t@Override
			\tpublic Map<String, Object> getContext()
			\t{
			\t\treturn javaRequirements.getContext();
			\t}

			\t@Override
			\tpublic Requirements withContext(String name, Object value)
			\t{
			""");
		appendConfigurationUpdate(plugins, "withContext(name, value)", out);
		out.append("""
			\t}

			\t@Override
			\tpublic Requirements withoutContext(String name)
			\t{
			""");
		appendConfigurationUpdate(plugins, "withoutContext(name)", out);
		out.append("""
			\t}

			\t@Override
			\tpublic Requirements withoutAnyContext()
			\t{
			""");
		appendConfigurationUpdate(plugins, "withoutAnyContext()", out);
		out.append("""
			\t}

			\t@Override
			\tpublic String getContextMessage(String message)
			\t{
			\t\treturn javaRequirements.getContextMessage(message);
			\t}

			\t@Override
			\tpublic String toString(Object value)
			\t{
			\t\treturn javaRequirements.toString(value);
			\t}

			\t@Override
			\tpublic <T> Requirements withStringConverter(Class<T> type, Function<T, String> converter)
			\t{
			""");
		appendConfigurationUpdate(plugins, "withStringConverter(type, converter)", out);
		out.append("""
			\t}

			\t@Override
			\tpublic <T> Requirements withoutStringConverter(Class<T> type)
			\t{
			""");
		appendConfigurationUpdate(plugins, "withoutStringConverter(type)", out);
		out.append("""
			\t}

			\t@Override
			\tpublic Requirements withConfiguration(Configuration newConfig)
			\t{
			""");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, false);
			out.append("\t\t" + delegateName + ".withConfiguration(newConfig);\n");
		}
		out.append("""
			\t\treturn this;
			\t}

			\t/**
			\t * Verifies requirements only if {@link Configuration#assertionsAreEnabled() assertions are enabled}.
			\t *
			\t * @param <V>          the return value of the operation
			\t * @param requirements the requirements to verify
			\t * @return the return value of the operation, or {@code null} if assertions are disabled
			\t * @throws NullPointerException if {@code requirements} is null
			\t * @see #assertThat(Consumer)
			\t */
			\tpublic <V> V assertThatAndReturn(Function<Requirements, V> requirements)
			\t{
			\t\trequireThat(requirements, "requirements").isNotNull();
			\t\tif (assertionsAreEnabled())
			\t\t\treturn requirements.apply(this);
			\t\treturn null;
			\t}

			\t/**
			\t * Verifies requirements only if {@link Configuration#assertionsAreEnabled() assertions are enabled}.
			\t *
			\t * @param requirements the requirements to verify
			\t * @throws NullPointerException if {@code requirements} is null
			\t * @see #assertThatAndReturn(Function)
			\t */
			\tpublic void assertThat(Consumer<Requirements> requirements)
			\t{
			\t\trequireThat(requirements, "requirements").isNotNull();
			\t\tif (assertionsAreEnabled())
			\t\t\trequirements.accept(this);
			\t}""");
		appendPluginMethods(plugins, false, out);
		out.append("}\n");
		return Generators.writeIfChanged(path, out.toString());
	}

	/**
	 * @param plugins the list of enabled plugins
	 * @return the interfaces to implement
	 */
	private String getInterfaces(List<CompilationUnit> plugins)
	{
		StringJoiner joiner = new StringJoiner(", ");
		joiner.add("Configuration");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			joiner.add(pluginClass.getNameAsString());
		}
		return joiner.toString();
	}

	/**
	 * Declares instances of each plugin to delegate to.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void appendDelegateFields(List<CompilationUnit> plugins, StringBuilder out)
	{
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, false);
			out.append("\tprivate final " + pluginClass.getNameAsString() + " " + delegateName + ";\n ");
		}
	}

	/**
	 * Adds the default constructor.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void appendDefaultConstructor(List<CompilationUnit> plugins, StringBuilder out)
	{
		out.append("""
			\t/**
			\t * Creates a new instance of Requirements.
			\t */
			\tpublic Requirements()
			\t{
			""");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, false);
			out.append("\t\tthis." + delegateName + " = new Default" + pluginClass.getNameAsString() + "();\n");
		}
		out.append("\t}\n");
	}

	/**
	 * Adds a constructor that accepts instances of each plugin to delegate to.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void appendDelegateConstructor(List<CompilationUnit> plugins, StringBuilder out)
	{
		out.append("\t/**\n");
		documentDelegateConstructorParameters(plugins, out);
		out.append("""
			\t * @throws AssertionError if any of the arguments are null
			\t */
			\tprivate Requirements""");
		StringJoiner joiner = new StringJoiner(", ", "(", ")");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, false);
			joiner.add(pluginClass.getNameAsString() + " " + delegateName);
		}
		out.append(joiner + "\n" +
			"\t{\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, false);
			out.append("\t\tassert (" + delegateName + " != null) : \"" + delegateName + " may not be null\";\n");
		}
		out.append("\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, false);
			out.append("\t\tthis." + delegateName + " = " + delegateName + ";\n");
		}
		out.append("\t}\n");
	}

	/**
	 * Documents the delegate constructor parameters.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void documentDelegateConstructorParameters(List<CompilationUnit> plugins, StringBuilder out)
	{
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String parameterName = getDelegateName(pluginClass, false);
			out.append("\t * @param " + parameterName + "  the " + pluginClass.getNameAsString() + " instance to " +
				"delegate to\n");
		}
	}

	/**
	 * Updates a Requirements instance configuration.
	 *
	 * @param plugins the list of enabled plugins
	 * @param change  the method to invoke on the configuration
	 * @param out     the buffer to write into
	 */
	private void appendConfigurationUpdate(List<CompilationUnit> plugins, String change, StringBuilder out)
	{
		CompilationUnit firstPlugin = plugins.get(0);
		ClassOrInterfaceDeclaration pluginClass = firstPlugin.getType(0).asClassOrInterfaceDeclaration();
		String delegateName = getDelegateName(pluginClass, false);
		out.append("\t\t" + delegateName + "." + change + ";\n");
		for (CompilationUnit plugin : plugins.subList(1, plugins.size()))
		{
			pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			delegateName = getDelegateName(pluginClass, false);
			out.append("\t\t" + delegateName + "." + change + ";\n");
		}
		out.append("\t\treturn this;\n");
	}

	/**
	 * Appends copyright information.
	 *
	 * @param out the buffer to write into
	 */
	private void addCopyright(StringBuilder out)
	{
		out.append("""
			/*
			 * Copyright 2013 Gili Tzabari.
			 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
			 */
			""");
	}

	/**
	 * Appends the class package.
	 *
	 * @param out the buffer to write into
	 */
	private void addPackage(StringBuilder out)
	{
		out.append("""
			package com.github.cowwoc.requirements;

			""");
	}

	/**
	 * Appends imports.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void addImports(List<CompilationUnit> plugins, StringBuilder out)
	{
		Set<String> namesImported = new HashSet<>();
		boolean addNewline = false;
		out.append("import com.github.cowwoc.requirements.annotation.CheckReturnValue;\n");
		for (CompilationUnit plugin : plugins)
		{
			if (addNewline)
			{
				out.append("\n");
				addNewline = false;
			}
			Map<String, String> imports = new HashMap<>();
			for (ImportDeclaration anImport : plugin.getImports())
			{
				if (!namesImported.add(anImport.getNameAsString()))
					continue;
				out.append(anImport.toString(defaultFormatter));
				imports.put(anImport.getName().getIdentifier(), anImport.getNameAsString());
				addNewline = true;
			}
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String pluginPackage = plugin.getPackageDeclaration().map(PackageDeclaration::getNameAsString).
				orElse("");

			for (MethodDeclaration method : pluginClass.getMethods())
			{
				if (!method.getType().isClassOrInterfaceType())
				{
					// No imports are necessary for void and primitive types
					continue;
				}
				String returnType = method.getType().asClassOrInterfaceType().getName().asString();
				if (!namesImported.add(returnType))
					continue;
				out.append("import " + fullyQualifiedType(returnType, imports, pluginPackage) + ";\n");
				addNewline = true;
			}

			// e.g. DefaultJavaRequirements, DefaultGuavaRequirements
			String defaultImplementationOfPlugin = "Default" + pluginClass.getNameAsString();
			if (namesImported.add(defaultImplementationOfPlugin))
			{
				out.append("import " + pluginPackage + "." + defaultImplementationOfPlugin + ";\n");
				addNewline = true;
			}

			for (ClassOrInterfaceType extendedType : pluginClass.getExtendedTypes())
			{
				// The type that this class "extends"
				String type = extendedType.getNameAsString();
				if (!namesImported.add(type))
					continue;
				out.append("import " + fullyQualifiedType(type, imports, pluginPackage) + ";\n");
				addNewline = true;
			}
		}
	}

	/**
	 * Resolves the fully-qualified name of a type.
	 *
	 * @param type          the type
	 * @param imports       maps simple names to fully-qualified names
	 * @param pluginPackage the package of the plugin being processed
	 * @return empty string is no import is required
	 */
	private String fullyQualifiedType(String type, Map<String, String> imports, String pluginPackage)
	{
		if (type.contains("."))
			return type;
		String fullyQualifiedName = imports.get(type);
		if (fullyQualifiedName != null)
			return fullyQualifiedName;
		try
		{
			classLoader.loadClass(pluginPackage + "." + type);
			return pluginPackage + "." + type;
		}
		catch (ClassNotFoundException e)
		{
			return "java.lang." + type;
		}
	}
}
