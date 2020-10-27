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
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, true);
			out.append("\tprivate static final " + topLevel.getNameAsString() + " " + delegateName +
				" = new Default" + topLevel.getNameAsString() + "();\n ");
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
		addDelegateMethods(plugins, true, out);
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
			String path = "/delegates/" + plugin + ".java";
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
	 * Delegates calls from a static method to an instance method.
	 *
	 * @param plugins  the list of enabled plugins
	 * @param isStatic true if the delegates are static
	 * @param out      the buffer to write into
	 */
	private void addDelegateMethods(List<CompilationUnit> plugins, boolean isStatic, StringBuilder out)
	{
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, isStatic);

			for (MethodDeclaration method : topLevel.getMethods())
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
					String text = method.getJavadoc().get().toComment().toString(defaultFormatter);
					// Increase indentation
					text = "\t" + text.replace("\n", "\n\t");
					out.append(text);
				});
				// Modifiers
				method.addModifier(Keyword.PUBLIC);
				if (isStatic)
					method.addModifier(Keyword.STATIC);
				StringJoiner joiner = new StringJoiner(" ", "", " ");
				for (Modifier modifier : method.getModifiers())
					joiner.add(modifier.getKeyword().asString());
				out.append("\t" + joiner.toString());

				// Type parameters
				joiner = new StringJoiner(", ", "<", "> ");
				joiner.setEmptyValue("");
				for (TypeParameter parameter : method.getTypeParameters())
					joiner.add(parameter.asString());

				// Return type and method name
				out.append(joiner.toString()).append(method.getType() + " " + method.getNameAsString());

				// Arguments
				joiner = new StringJoiner(", ", "(", ")");
				for (Parameter parameter : method.getParameters())
					joiner.add(parameter.getTypeAsString() + " " + parameter.getNameAsString());
				out.append(joiner.toString() + "\n");

				out.append("\t{\n");
				out.append("\t\treturn " + delegateName + "." + method.getNameAsString() + "(actual, name);\n");
				out.append("\t}\n");
			}
		}
	}

	/**
	 * @param delegate the type of the delegate
	 * @param isStatic true if the delegate is static
	 * @return the name of the variable to delegate to
	 */
	private String getDelegateName(ClassOrInterfaceDeclaration delegate, boolean isStatic)
	{
		String topLevelName = delegate.getNameAsString();
		if (!isStatic)
			return Character.toLowerCase(topLevelName.charAt(0)) + topLevelName.substring(1);
		int requirementsIndex = topLevelName.indexOf("Requirements");
		String delegateName = topLevelName.substring(0, requirementsIndex);
		return delegateName.toUpperCase(Locale.US) + "_REQUIREMENTS";
	}

	/**
	 * @param delegate the type of the delegate
	 * @return a function that returns a new instance of the delegate
	 */
	private String createDelegate(ClassOrInterfaceDeclaration delegate)
	{
		String topLevelName = delegate.getNameAsString();
		int requirementsIndex = topLevelName.indexOf("Requirements");
		String delegateType = topLevelName.substring(0, requirementsIndex);
		return "this." + getDelegateName(delegate, false) + " = " + delegateType + "Secrets.INSTANCE" +
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
			"public final class Requirements implements Configuration\n" +
			"{\n");
		addDelegates(plugins, out);
		out.append("\n");
		addDefaultConstructor(plugins, out);
		out.append("\n");
		addDelegateConstructor(plugins, out);
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
				ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
				out.append(createDelegate(topLevel));
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
			"\tpublic Requirements withAssertionsEnabled()\n" +
			"\t{\n");
		updateConfig(plugins, "withAssertionsEnabled()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic boolean isCleanStackTrace()\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.isCleanStackTrace();\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Requirements withCleanStackTrace()\n" +
			"\t{\n");
		updateConfig(plugins, "withCleanStackTrace()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Requirements withoutCleanStackTrace()\n" +
			"\t{\n");
		updateConfig(plugins, "withoutCleanStackTrace()", out);
		out.append("\t}\n" +
			"\n" +
			"\tpublic Requirements withAssertionsDisabled()\n" +
			"\t{\n");
		updateConfig(plugins, "withAssertionsDisabled()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic boolean isDiffEnabled()\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.isDiffEnabled();\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Requirements withDiff()\n" +
			"\t{\n");
		updateConfig(plugins, "withDiff()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Requirements withoutDiff()\n" +
			"\t{\n");
		updateConfig(plugins, "withoutDiff()", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Map<String, Object> getContext()\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.getContext();\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Requirements putContext(String name, Object value)\n" +
			"\t{\n");
		updateConfig(plugins, "putContext(name, value)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Requirements removeContext(String name)\n" +
			"\t{\n");
		updateConfig(plugins, "removeContext(name)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic String toString(Object value)\n" +
			"\t{\n" +
			"\t\treturn javaRequirements.toString(value);\n" +
			"\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic <T> Requirements withStringConverter(Class<T> type, Function<T, String> converter)\n" +
			"\t{\n");
		updateConfig(plugins, "withStringConverter(type, converter)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic <T> Requirements withoutStringConverter(Class<T> type)\n" +
			"\t{\n");
		updateConfig(plugins, "withoutStringConverter(type)", out);
		out.append("\t}\n" +
			"\n" +
			"\t@Override\n" +
			"\tpublic Requirements withConfiguration(Configuration newConfig)\n" +
			"\t{\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, false);
			String newDelegateName = "new" + Character.toUpperCase(delegateName.charAt(0)) +
				delegateName.substring(1);
			out.append("\t\t" + topLevel.getNameAsString() + " " + newDelegateName + " = " + delegateName +
				".withConfiguration(newConfig);\n");
		}
		out.append("\t\treturn new Requirements");
		StringJoiner joiner = new StringJoiner(", ", "(", ");\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			joiner.add("new" + topLevel.getNameAsString());
		}
		out.append(joiner.toString() +
			"\t}\n");
		addDelegateMethods(plugins, false, out);
		out.append("}\n");
		return Generators.writeIfChanged(path, out.toString());
	}

	/**
	 * Declares the delegate class fields.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void addDelegates(List<CompilationUnit> plugins, StringBuilder out)
	{
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, false);
			out.append("\tprivate final " + topLevel.getNameAsString() + " " + delegateName + ";\n ");
		}
	}

	/**
	 * Adds the default constructor.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void addDefaultConstructor(List<CompilationUnit> plugins, StringBuilder out)
	{
		out.append("\tpublic Requirements()\n" +
			"\t{\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, false);
			out.append("\t\tthis." + delegateName + " = new Default" + topLevel.getNameAsString() + "();\n");
		}
		out.append("\t}\n");
	}

	/**
	 * Adds a constructor that accepts delegates to delegate to.
	 *
	 * @param plugins the list of enabled plugins
	 * @param out     the buffer to write into
	 */
	private void addDelegateConstructor(List<CompilationUnit> plugins, StringBuilder out)
	{
		out.append("\t/**\n");
		documentDelegates(plugins, out);
		out.append("\t * @throws AssertionError if any of the arguments are null\n" +
			"\t */\n" +
			"\tprivate Requirements");
		StringJoiner joiner = new StringJoiner(", ", "(", ")");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, false);
			joiner.add(topLevel.getNameAsString() + " " + delegateName);
		}
		out.append(joiner.toString() + "\n" +
			"\t{\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, false);
			out.append("\t\tassert (" + delegateName + " != null) : \"" + delegateName + " may not be null\";\n");
		}
		out.append("\n");
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, false);
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
	private void documentDelegates(List<CompilationUnit> plugins, StringBuilder out)
	{
		for (CompilationUnit plugin : plugins)
		{
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String delegateName = getDelegateName(topLevel, false);
			out.append("\t * @param " + delegateName + "  the " + topLevel.getNameAsString() + " instance to " +
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
	private void updateConfig(List<CompilationUnit> plugins, String change, StringBuilder out)
	{
		CompilationUnit firstPlugin = plugins.get(0);
		ClassOrInterfaceDeclaration topLevel = firstPlugin.getType(0).asClassOrInterfaceDeclaration();
		String delegateName = getDelegateName(topLevel, false);
		String newDelegateName = "new" + Character.toUpperCase(delegateName.charAt(0)) +
			delegateName.substring(1);
		out.append("\t\t" + topLevel.getNameAsString() + " " + newDelegateName + " = " + delegateName +
			"." + change + ";\n" +
			"\t\tif (" + newDelegateName + ".equals(" + delegateName + "))\n" +
			"\t\t\treturn this;\n");
		for (CompilationUnit plugin : plugins.subList(1, plugins.size()))
		{
			topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			delegateName = getDelegateName(topLevel, false);
			newDelegateName = "new" + Character.toUpperCase(delegateName.charAt(0)) + delegateName.substring(1);
			out.append("\t\t" + topLevel.getNameAsString() + " " + newDelegateName + " = " + delegateName +
				"." + change + ";\n");
		}
		out.append("\t\treturn new Requirements");
		StringJoiner joiner = new StringJoiner(", ", "(", ");\n");
		for (CompilationUnit plugin : plugins)
		{
			topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			joiner.add("new" + topLevel.getNameAsString());
		}
		out.append(joiner.toString());
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
			ClassOrInterfaceDeclaration topLevel = plugin.getType(0).asClassOrInterfaceDeclaration();
			String packageName = plugin.getPackageDeclaration().map(PackageDeclaration::getNameAsString).
				orElse("");

			for (MethodDeclaration method : topLevel.getMethods())
			{
				String type = method.getType().asClassOrInterfaceType().getName().asString();
				if (!namesImported.add(type))
					continue;
				out.append("import " + fullyQualifiedType(type, packageName) + ";\n");
				addNewline = true;
			}

			// e.g. DefaultJavaRequirements, DefaultGuavaRequirements
			String defaultImplementationOfDelegate = "Default" + topLevel.getNameAsString();
			if (namesImported.add(defaultImplementationOfDelegate))
			{
				out.append("import " + packageName + "." + defaultImplementationOfDelegate + ";\n");
				addNewline = true;
			}

			for (ClassOrInterfaceType extendedType : topLevel.getExtendedTypes())
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
