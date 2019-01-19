/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Optimizes the exceptions thrown by the library, such as evaluating stack traces are lazily. See
 * {@code org.bitbucket.cowwoc.requirements.java.GlobalConfiguration.isLibraryRemovedFromStackTrace()}.
 */
public final class ExceptionOptimizer
{
	/**
	 * @param args the command-line arguments
	 * @throws IOException if an I/O error occurs while writing the files
	 */
	public static void main(String[] args) throws IOException
	{
		if (args.length != 2)
		{
			System.err.println("Optimizes the exceptions thrown by the library.");
			System.err.println();
			System.err.println("Usage: ExceptionOptimizer <root> <exception>");
			System.err.println();
			System.err.println("Where:");
			System.err.println();
			System.err.println("<root> is the directory corresponding to the Java root package");
			System.err.println("<exception> is the name of the exception to wrap");
			System.exit(1);
		}

		Path directory = Paths.get(args[0]);
		String exceptionName = args[1];
		Class<?> exception;
		try
		{
			exception = Class.forName(exceptionName);
		}
		catch (ClassNotFoundException e)
		{
			System.err.println("Cannot find " + exceptionName);
			System.exit(2);
			exception = null;
		}
		ExceptionOptimizer optimizer = new ExceptionOptimizer();

		Logger log = LoggerFactory.getLogger(ExceptionOptimizer.class);
		Path wrapperPath = getWrapperPath(directory, exceptionName);
		if (optimizer.writeWrapper(wrapperPath, exception))
			log.info("{} was up-to-date", wrapperPath);
		else
			log.info("Generated {}", wrapperPath);
	}

	/**
	 * @param exceptionName the name of an exception
	 * @return the name of the wrapper corresponding to this exception
	 */
	private static String exceptionToWrapperName(String exceptionName)
	{
		return "org.bitbucket.cowwoc.requirements.exception." + exceptionName + "Wrapper";
	}

	/**
	 * @param rootPackage   the path of the root package
	 * @param exceptionName the exception to wrap
	 * @return the path of the exception wrapper
	 */
	public static Path getWrapperPath(Path rootPackage, String exceptionName)
	{
		return rootPackage.resolve(exceptionToWrapperName(exceptionName).replace('.', '/') + ".java");
	}

	/**
	 * @param className the name of a class
	 * @return the simple name of the class
	 */
	private static String getSimpleName(String className)
	{
		int index = className.lastIndexOf('.');
		assert (index != -1) : className;
		return className.substring(index + 1);
	}

	/**
	 * Writes the exception wrapper.
	 *
	 * @param path      the path of the file
	 * @param exception the exception to wrap
	 * @return true if the file was updated
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	public boolean writeWrapper(Path path, Class<?> exception) throws IOException
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

		String exceptionSimpleName = exception.getSimpleName();
		String packageName = exception.getPackage().getName();

		String wrapperName = exceptionToWrapperName(exception.getName());
		String wrapperSimpleName = getSimpleName(wrapperName);
		String wrapperPackageName = wrapperName.substring(0,
			wrapperName.length() - (1 + wrapperSimpleName.length()));

		try (StringWriter sw = new StringWriter();
		     BufferedWriter writer = new BufferedWriter(sw))
		{
			writer.write("/*\n" +
				" * Copyright 2018 Gili Tzabari.\n" +
				" * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0\n" +
				" */\n" +
				"package " + wrapperPackageName + ";\n" +
				"\n");
			writer.write("import org.bitbucket.cowwoc.requirements.java.GlobalConfiguration;\n" +
				"import org.bitbucket.cowwoc.requirements.java.annotations.OptimizedException;\n" +
				"import org.bitbucket.cowwoc.requirements.java.internal.util.Exceptions;\n" +
				"\n" +
				"import java.io.PrintStream;\n");
			if (!packageName.equals("java.lang"))
			{
				// No need to import classes in "java.lang"
				writer.write("import " + exception.getName() + ";\n");
			}
			writer.write("\n" +
				"/**\n" +
				" * Generates exceptions that strip this library from their stack traces lazily.\n" +
				" *\n" +
				" * @see GlobalConfiguration#isLibraryRemovedFromStackTrace()\n" +
				" */\n" +
				"@OptimizedException\n" +
				"public final class " + wrapperSimpleName + " extends " + exceptionSimpleName + "\n" +
				"{\n" +
				"\tprivate final Exceptions exceptions;\n" +
				"\tprivate boolean filtered;\n" +
				"\n" +
				"\t/**\n" +
				"\t * @param exceptions an instance of {@link Exceptions}\n" +
				"\t * @param message    the detail message. The detail message is saved for later retrieval by " +
				"the {@link #getMessage()} method\n" +
				"\t */\n" +
				"\tpublic " + wrapperSimpleName + "(Exceptions exceptions, String message)\n" +
				"\t{\n" +
				"\t\tsuper(message);\n" +
				"\t\tif (exceptions == null)\n" +
				"\t\t\tthrow new NullPointerException(\"exceptions may not be null\");\n" +
				"\t\tthis.exceptions = exceptions;\n" +
				"\t}\n" +
				"\n");
			Constructor<?> constructor;
			try
			{
				constructor = exception.getConstructor(String.class, Throwable.class);
			}
			catch (NoSuchMethodException e)
			{
				constructor = null;
			}
			if (constructor != null)
			{
				writer.write("\t/**\n" +
					"\t * @param exceptions an instance of {@link Exceptions}\n" +
					"\t * @param message    the detail message. The detail message is saved for later retrieval by " +
					"the {@link #getMessage()} method\n" +
					"\t * @param cause      the cause (which is saved for later retrieval by the {@link #getCause()} " +
					"method).  (A {@code null}\n" +
					"\t *                   value is permitted, and indicates that the cause is nonexistent or " +
					"unknown.)\n" +
					"\t */\n" +
					"\tpublic " + wrapperSimpleName + "(Exceptions exceptions, String message, Throwable cause)\n" +
					"\t{\n" +
					"\t\tsuper(message, cause);\n" +
					"\t\tif (exceptions == null)\n" +
					"\t\t\tthrow new NullPointerException(\"exceptions may not be null\");\n" +
					"\t\tthis.exceptions = exceptions;\n" +
					"\t}\n" +
					"\n");
			}
			writer.write("\t@Override\n" +
				"\tpublic void printStackTrace(PrintStream s)\n" +
				"\t{\n" +
				"\t\tfilterStackTrace();\n" +
				"\t\tsuper.printStackTrace(s);\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic StackTraceElement[] getStackTrace()\n" +
				"\t{\n" +
				"\t\tfilterStackTrace();\n" +
				"\t\treturn super.getStackTrace();\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * Removes references to this library from the exception stack trace.\n" +
				"\t */\n" +
				"\tprivate synchronized void filterStackTrace()\n" +
				"\t{\n" +
				"\t\tif (filtered)\n" +
				"\t\t\treturn;\n" +
				"\t\tStackTraceElement[] stackTrace = super.getStackTrace();\n" +
				"\t\tStackTraceElement[] newStackTrace = exceptions.removeLibraryFromStackTrace(stackTrace);\n" +
				"\t\tif (newStackTrace != stackTrace)\n" +
				"\t\t\tsetStackTrace(newStackTrace);\n" +
				"\t\tfiltered = true;\n" +
				"\t}\n" +
				"}\n");
			// There is no easy way to override writeObject(ObjectOutputStream) so we don't try to
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
