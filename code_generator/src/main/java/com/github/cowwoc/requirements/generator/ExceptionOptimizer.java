/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.generator;

import com.github.cowwoc.requirements.generator.internal.util.Generators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Optimizes the exceptions thrown by the library.
 * <p>
 * See {@code com.github.cowwoc.requirements.java.GlobalRequirements.isCleanStackTrace()}.
 */
public final class ExceptionOptimizer
{
	/**
	 * The command-line entry point for this class.
	 *
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
		optimizer.writeWrapper(directory, exception);
	}

	private final Logger log = LoggerFactory.getLogger(ExceptionOptimizer.class);

	/**
	 * Creates a new ExceptionOptimizer.
	 */
	public ExceptionOptimizer()
	{
	}

	/**
	 * Writes an exception wrapper and logs the result.
	 *
	 * @param directory the directory to generate files into
	 * @param exception the exception to wrap
	 * @throws NullPointerException if any of the arguments are null
	 * @throws IOException          if an I/O error occurs
	 */
	public void apply(Path directory, Class<?> exception) throws IOException
	{
		if (directory == null)
			throw new NullPointerException("directory may not be null");
		Path wrapperPath = getWrapperPath(directory, exception.getName());
		if (writeWrapper(wrapperPath, exception))
			log.info("Generated {}", wrapperPath);
		else
			log.info("Skipped {} because it was up-to-date", wrapperPath);
	}

	/**
	 * @param exceptionName the name of an exception
	 * @return the name of the wrapper corresponding to this exception
	 */
	private static String exceptionToWrapperName(String exceptionName)
	{
		return "com.github.cowwoc.requirements.exception." + exceptionName;
	}

	/**
	 * Returns the path of the exception wrapper.
	 *
	 * @param rootPackage   the path of the root package
	 * @param exceptionName the exception to wrap
	 * @return the path of the exception wrapper
	 */
	private static Path getWrapperPath(Path rootPackage, String exceptionName)
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
	private boolean writeWrapper(Path path, Class<?> exception) throws IOException
	{
		// NOTE: Any exceptions thrown by the generated code must use fully-qualified names to avoid conflict
		// with other optimized exceptions that have the same simple name.
		String wrapperName = exceptionToWrapperName(exception.getName());
		String wrapperSimpleName = getSimpleName(wrapperName);
		String wrapperPackageName = wrapperName.substring(0,
			wrapperName.length() - (1 + wrapperSimpleName.length()));

		StringWriter sw = new StringWriter();
		try (BufferedWriter writer = new BufferedWriter(sw))
		{
			writer.write("/*\n" +
				" * Copyright 2018 Gili Tzabari.\n" +
				" * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0\n" +
				" */\n" +
				"package " + wrapperPackageName + ";\n" +
				"\n");
			writer.write("""
				import com.github.cowwoc.requirements.annotation.OptimizedException;
				import com.github.cowwoc.requirements.java.GlobalRequirements;
				import com.github.cowwoc.requirements.java.internal.util.Exceptions;

				import java.io.PrintStream;
				import java.io.PrintWriter;
				""");
			writer.write("\n" +
				"/**\n" +
				" * Optimizes exceptions thrown by the library.\n" +
				" *\n" +
				" * @see GlobalRequirements#isCleanStackTrace()\n" +
				" */\n" +
				"@OptimizedException\n" +
				"public final class " + wrapperSimpleName + " extends " + exception.getName() + "\n" +
				"{\n" +
				"\tprivate static final long serialVersionUID = 0L;\n" +
				"\t/**\n" +
				"\t * An instance of {@code Exceptions}.\n" +
				"\t */\n" +
				"\tprivate final Exceptions exceptions;\n" +
				"\t/**\n" +
				"\t * Indicates if stack trace references to this library have already been removed.\n" +
				"\t */\n" +
				"\tprivate boolean cleanedStackTrace;\n" +
				"\n" +
				"\t/**\n" +
				"\t * @param exceptions an instance of {@link Exceptions}\n" +
				"\t * @param message    the detail message. The detail message is saved for later retrieval by " +
				"the\n" +
				"\t *                   {@link #getMessage()} method\n" +
				"\t */\n" +
				"\tpublic " + wrapperSimpleName + "(Exceptions exceptions, String message)\n" +
				"\t{\n" +
				"\t\tsuper(message);\n" +
				"\t\tif (exceptions == null)\n" +
				"\t\t\tthrow new java.lang.NullPointerException(\"exceptions may not be null\");\n" +
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
					"the\n" +
					"\t *                   {@link #getMessage()} method\n" +
					"\t * @param cause      the cause (which is saved for later retrieval by the {@link #getCause()} " +
					"method).\n" +
					"\t *                   (A {@code null} value is permitted, and indicates that the cause is " +
					"nonexistent or\n" +
					"\t *                   unknown.)\n" +
					"\t */\n" +
					"\tpublic " + wrapperSimpleName + "(Exceptions exceptions, String message, Throwable cause)\n" +
					"\t{\n" +
					"\t\tsuper(message, cause);\n" +
					"\t\tif (exceptions == null)\n" +
					"\t\t\tthrow new java.lang.NullPointerException(\"exceptions may not be null\");\n" +
					"\t\tthis.exceptions = exceptions;\n" +
					"\t}\n" +
					"\n");
			}
			writer.write("""
				\t@Override
				\tpublic void printStackTrace(PrintStream s)
				\t{
				\t\tcleanStackTrace();
				\t\tsuper.printStackTrace(s);
				\t}

				\t@Override
				\tpublic void printStackTrace(PrintWriter s)
				\t{
				\t\tcleanStackTrace();
				\t\tsuper.printStackTrace(s);
				\t}

				\t@Override
				\tpublic StackTraceElement[] getStackTrace()
				\t{
				\t\tcleanStackTrace();
				\t\treturn super.getStackTrace();
				\t}

				\t/**
				\t * Removes stack trace references to this library.
				\t */
				\tprivate synchronized void cleanStackTrace()
				\t{
				\t\tif (cleanedStackTrace)
				\t\t\treturn;
				\t\tStackTraceElement[] stackTrace = super.getStackTrace();
				\t\tStackTraceElement[] newStackTrace = exceptions.removeLibraryFromStackTrace(stackTrace);
				\t\tif (newStackTrace != stackTrace)
				\t\t\tsetStackTrace(newStackTrace);
				\t\tcleanedStackTrace = true;
				\t}
				}
				""");
			// There is no easy way to override writeObject(ObjectOutputStream) so we don't try to
		}
		return Generators.writeIfChanged(path, sw.toString());
	}
}
