/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.generator.internal.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * Code generator helper functions.
 */
public final class Generators
{
	/**
	 * Prevent construction.
	 */
	private Generators()
	{
	}

	/**
	 * Updates the contents of a file if it has changed.
	 *
	 * @param path        the path of the file
	 * @param newContents the new contents of the file
	 * @return true if the file was changed
	 * @throws NullPointerException if any of the arguments are null
	 * @throws IOException          if an I/O error occurs
	 */
	public static boolean writeIfChanged(Path path, String newContents) throws IOException
	{
		if (path == null)
			throw new NullPointerException("path may not be null");
		if (newContents == null)
			throw new NullPointerException("contents may not be null");
		Files.createDirectories(path.getParent());
		newContents = toNativeNewline(newContents);

		String existingContents;
		try
		{
			existingContents = Files.readString(path);
		}
		catch (NoSuchFileException unused)
		{
			existingContents = "";
		}

		if (existingContents.equals(newContents))
			return false;
		try (FileWriter fw = new FileWriter(path.toFile()))
		{
			fw.write(newContents);
		}
		return true;
	}

	/**
	 * @param text some text
	 * @return {@code text} using system-native line separator
	 */
	public static String toNativeNewline(String text)
	{
		String nativeNewline = System.getProperty("line.separator");
		if (nativeNewline.equals("\n"))
			return text;
		return text.replace("\n", nativeNewline);
	}

	/**
	 * @param text some text
	 * @return {@code text} using {@code '\n'} line separator
	 */
	public static String toUnixNewline(String text)
	{
		String nativeNewline = System.getProperty("line.separator");
		if (nativeNewline.equals("\n"))
			return text;
		return text.replace(nativeNewline, "\n");
	}
}

