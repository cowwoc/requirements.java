/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.generator;

import com.github.cowwoc.requirements.generator.internal.secrets.SharedSecrets;
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
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Generates {@code Requirements} and {@code DefaultRequirements} endpoints. The contents of these classes
 * depend on which plugins are enabled.
 */
public final class ApiGenerator
{
	private final PrettyPrinterConfiguration defaultFormatter =
		new PrettyPrinterConfiguration().setEndOfLineCharacter("\n");

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
		ApiGenerator generator = new ApiGenerator();
		generator.writeTo(directory);
	}

	private boolean guavaEnabled;
	private boolean exportScope;
	private final Logger log = LoggerFactory.getLogger(ApiGenerator.class);

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
		out.append("\n" +
			"/**\n" +
			" * Verifies API requirements using the default {@link Configuration configuration}.\n" +
			" * <p>\n" +
			" * The assertion status of the {@link Configuration} class determines whether\n" +
			" * {@code assertThat()} carries out a verification or does nothing.\n" +
			" *\n" +
			" * @see Requirements\n" +
			" * @see JavaRequirements\n");
		if (guavaEnabled)
			out.append(" * @see GuavaRequirements\n");
		out.append(" */\n" +
			"public final class DefaultRequirements\n" +
			"{\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, true);
			out.append("\tprivate static final " + pluginClass.getNameAsString() + " " + delegateName +
				" = new Default" + pluginClass.getNameAsString() + "();\n ");
		}
		out.append("\n" +
			"\t/**\n" +
			"\t * Returns true if assertions are enabled for this class.\n" +
			"\t *\n" +
			"\t * @return true if assertions are enabled for this class\n" +
			"\t */\n" +
			"\tpublic static boolean assertionsAreEnabled()\n" +
			"\t{\n" +
			"\t\treturn JAVA_REQUIREMENTS.assertionsAreEnabled();\n" +
			"\t}\n");
		appendPluginMethods(plugins, true, out);
		out.append("\n" +
			"\t/**\n" +
			"\t * Prevent construction.\n" +
			"\t */\n" +
			"\tprivate DefaultRequirements()\n" +
			"\t{\n" +
			"\t}\n" +
			"}\n");
		return Generators.writeIfChanged(path, out.toString());
	}

	/**
	 * @return the AST of enabled plugins
	 * @throws IOException if an I/O error occurs
	 */
	private List<CompilationUnit> getPlugins() throws IOException
	{
		List<String> plugins = new ArrayList<>(2);
		plugins.add("JavaRequirements");
		if (guavaEnabled)
			plugins.add("GuavaRequirements");
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
			String path = "/com/github/cowwoc/requirements/plugins/" + plugin + ".java";
			try (InputStream in = getClass().getResourceAsStream(path))
			{
				if (in == null)
					throw new IOException("Could not find " + path + " on classpath");
				result.add(StaticJavaParser.parse(in));
			}
			catch (ParseProblemException e)
			{
				throw new IOException(e);
			}
		}
		return result;
	}

	/**
	 * Delegates calls to a plugin method.
	 *
	 * @param plugins  the list of enabled plugins
	 * @param isStatic true if the delegate field is static
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
					case "assertThat":
					case "validateThat":
						break;
					default:
						continue;
				}

				out.append("\n");
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
				// Annotations
				if (!isStatic)
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
		int requirementsIndex = pluginName.indexOf("Requirements");
		String delegateName = pluginName.substring(0, requirementsIndex);
		return delegateName.toUpperCase(Locale.US) + "_REQUIREMENTS";
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
			out.append("import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;\n" +
				"import com.github.cowwoc.requirements.java.internal.secrets.JavaSecrets;\n" +
				"import com.github.cowwoc.requirements.guava.internal.secrets.GuavaSecrets;\n" +
				"\n");
		}
		out.append("import java.math.BigDecimal;\n" +
			"import java.net.InetAddress;\n" +
			"import java.net.URI;\n" +
			"import java.nio.file.Path;\n" +
			"import java.util.Collection;\n" +
			"import java.util.Map;\n" +
			"import java.util.Optional;\n" +
			"import java.util.function.Function;\n" +
			"\n");
		out.append("/**\n" +
			" * Verifies API requirements using a custom {@link Configuration configuration}." +
			" " +
			"for verifying API requirements.\n" +
			" * <p>\n" +
			" * This class holds its own configuration whereas {@link DefaultRequirements} always uses the\n" +
			" * default {@link Configuration configuration}.\n" +
			" * <p>\n" +
			" * The assertion status of the {@link Configuration} class determines whether\n" +
			" * {@code assertThat()} carries out a verification or does nothing.\n" +
			" * <p>\n" +
			" * This class is thread-safe.\n" +
			" *\n" +
			" * @see DefaultRequirements\n" +
			" * @see JavaRequirements\n");
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
			out.append("\n" +
				"\t/**\n" +
				"\t * This constructor is meant to be used by automated tests, not by users.\n" +
				"\t *\n" +
				"\t * @param scope the application configuration\n" +
				"\t * @throws AssertionError if any of the arguments are null\n" +
				"\t */\n" +
				"\tpublic Requirements(ApplicationScope scope)\n" +
				"\t{\n" +
				"\t\tassert (scope != null) : \"scope may not be null\";\n");
			for (CompilationUnit plugin : plugins)
			{
				ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
				out.append(createDelegate(pluginClass));
			}
			out.append("\t}\n");
		}
		out.append("\n" +
			"\t/**\n" +
			"\t * Returns true if assertions are enabled for this class.\n" +
			"\t *\n" +
			"\t * @return true if assertions are enabled for this class\n" +
			"\t */\n" +
			"\tpublic boolean assertionsAreEnabled()\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.assertionsAreEnabled();\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements withAssertionsEnabled()\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withAssertionsEnabled()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic boolean isCleanStackTrace()\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.isCleanStackTrace();\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements withCleanStackTrace()\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withCleanStackTrace()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements withoutCleanStackTrace()\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withoutCleanStackTrace()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements withAssertionsDisabled()\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withAssertionsDisabled()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic boolean isDiffEnabled()\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.isDiffEnabled();\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements withDiff()\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withDiff()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements withoutDiff()\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withoutDiff()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Map<String, Object> getContext()\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.getContext();\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements putContext(String name, Object value)\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "putContext(name, value)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements removeContext(String name)\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "removeContext(name)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic String toString(Object value)\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.toString(value);\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic <T> Requirements withStringConverter(Class<T> type, Function<T, String> converter)\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withStringConverter(type, converter)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic <T> Requirements withoutStringConverter(Class<T> type)\n" +
			"\t{\n");
		appendConfigurationUpdate(plugins, "withoutStringConverter(type)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\t@CheckReturnValue\n" +
			"\tpublic Requirements withConfiguration(Configuration newConfig)\n" +
			"\t{\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(pluginClass, false);
			String newPluginInstance = "new" + Character.toUpperCase(delegateName.charAt(0)) +
				delegateName.substring(1);
			out.append("\t\t" + pluginClass.getNameAsString() + " " + newPluginInstance + " = " + delegateName +
				".withConfiguration(newConfig);\n");
		}
		out.append("\t\treturn new Requirements");
		StringJoiner joiner = new StringJoiner(", ", "(", ");\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			joiner.add("new" + pluginClass.getNameAsString());
		}
		out.append(joiner +
			"\t}\n");
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
		out.append("\t/**\n" +
			"\t * Creates a new instance of Requirements.\n" +
			"\t */\n" +
			"\tpublic Requirements()\n" +
			"\t{\n");
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
		out.append("\t * @throws AssertionError if any of the arguments are null\n" +
			"\t */\n" +
			"\tprivate Requirements");
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
		String newPluginInstance = "new" + Character.toUpperCase(delegateName.charAt(0)) +
			delegateName.substring(1);
		out.append("\t\t" + pluginClass.getNameAsString() + " " + newPluginInstance + " = " + delegateName +
			"." + change + ";\n" +
			"\t\tif (" + newPluginInstance + ".equals(" + delegateName + "))\n" +
			"\t\t\treturn this;\n");
		for (CompilationUnit plugin : plugins.subList(1, plugins.size()))
		{
			pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			delegateName = getDelegateName(pluginClass, false);
			newPluginInstance = "new" + Character.toUpperCase(delegateName.charAt(0)) + delegateName.substring(1);
			out.append("\t\t" + pluginClass.getNameAsString() + " " + newPluginInstance + " = " + delegateName +
				"." + change + ";\n");
		}
		out.append("\t\treturn new Requirements");
		StringJoiner joiner = new StringJoiner(", ", "(", ");\n");
		for (CompilationUnit plugin : plugins)
		{
			pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			joiner.add("new" + pluginClass.getNameAsString());
		}
		out.append(joiner);
	}

	/**
	 * Appends copyright information.
	 *
	 * @param out the buffer to write into
	 */
	private void addCopyright(StringBuilder out)
	{
		out.append("/*\n" +
			" * Copyright 2013 Gili Tzabari.\n" +
			" * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0\n" +
			" */\n");
	}

	/**
	 * Appends the class package.
	 *
	 * @param out the buffer to write into
	 */
	private void addPackage(StringBuilder out)
	{
		out.append("package com.github.cowwoc.requirements;\n" +
			"\n");
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
		out.append("import com.github.cowwoc.requirements.annotations.CheckReturnValue;\n");
		for (CompilationUnit plugin : plugins)
		{
			if (addNewline)
			{
				out.append("\n");
				addNewline = false;
			}
			for (ImportDeclaration anImport : plugin.getImports())
			{
				if (namesImported.add(anImport.getNameAsString()))
				{
					out.append(anImport.toString(defaultFormatter));
					addNewline = true;
				}
			}
			ClassOrInterfaceDeclaration pluginClass = plugin.getType(0).asClassOrInterfaceDeclaration();
			String packageName = plugin.getPackageDeclaration().map(PackageDeclaration::getNameAsString).
				orElse("");

			for (MethodDeclaration method : pluginClass.getMethods())
			{
				String type = method.getType().asClassOrInterfaceType().getName().asString();
				if (!namesImported.add(type))
					continue;
				out.append("import " + fullyQualifiedType(type, packageName) + ";\n");
				addNewline = true;
			}

			// e.g. DefaultJavaRequirements, DefaultGuavaRequirements
			String defaultImplementationOfPlugin = "Default" + pluginClass.getNameAsString();
			if (namesImported.add(defaultImplementationOfPlugin))
			{
				out.append("import " + packageName + "." + defaultImplementationOfPlugin + ";\n");
				addNewline = true;
			}

			for (ClassOrInterfaceType extendedType : pluginClass.getExtendedTypes())
			{
				String type = extendedType.getNameAsString();
				if (namesImported.add(type))
				{
					out.append("import " + fullyQualifiedType(type, packageName) + ";\n");
					namesImported.add(type);
					addNewline = true;
				}
			}
		}
	}

	/**
	 * Resolves a type relative to a package.
	 *
	 * @param type        the type
	 * @param packageName the enclosing package
	 * @return the fully-qualified type
	 */
	private String fullyQualifiedType(String type, String packageName)
	{
		if (type.contains("."))
			return type;
		return packageName + "." + type;
	}
}
