/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.bitbucket.cowwoc.requirements.diff.string.OperatingSystem.Type.UNKNOWN;
import static org.bitbucket.cowwoc.requirements.diff.string.OperatingSystem.Type.WINDOWS;
import org.bitbucket.cowwoc.requirements.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An operating system.
 *
 * @author Gili Tzabari
 */
public final class OperatingSystem
{
	/**
	 * Extracts the Windows version number from the output of the "ver" command.
	 */
	private static final Pattern WINDOWS_VERSION = Pattern.compile(
		"Microsoft Windows \\[Version (\\d+)\\.(\\d+)\\.(\\d+)\\]");

	/**
	 * @return the current operating system
	 */
	public static OperatingSystem current()
	{
		Logger log = LoggerFactory.getLogger(OperatingSystem.class);
		Type type = Type.current();
		switch (type)
		{
			case WINDOWS:
			{
				String versionAsString;
				try
				{
					versionAsString = Processes.run("cmd.exe", "/c", "ver");
				}
				catch (IOException e)
				{
					log.error("Failed to get Windows version", e);
					return new OperatingSystem(type, null);
				}
				Matcher matcher = WINDOWS_VERSION.matcher(versionAsString);
				if (!matcher.find())
				{
					log.error("Unexpected Windows version: {}", versionAsString);
					return new OperatingSystem(type, null);
				}
				int major;
				int minor;
				int build;
				try
				{
					major = Integer.parseInt(matcher.group(1));
					minor = Integer.parseInt(matcher.group(2));
					build = Integer.parseInt(matcher.group(3));
				}
				catch (NumberFormatException e)
				{
					log.error("Failed to parse Windows version: " + versionAsString, e);
					return new OperatingSystem(type, null);
				}
				Version version = new Version(major, minor, build);
				return new OperatingSystem(type, version);
			}
			default:
				return new OperatingSystem(UNKNOWN, null);
		}
	}

	/**
	 * The version number of an operating system.
	 */
	public static final class Version
	{
		public final int major;
		public final int minor;
		public final int build;

		/**
		 * @param major the major component of the version number
		 * @param minor the minor component of the version number
		 * @param build the build component of the version number
		 * @throws AssertionError if any of the components is negative
		 */
		public Version(int major, int minor, int build)
		{
			assert (major >= 0): "major: " + major;
			assert (minor >= 0): "minor: " + minor;
			assert (build >= 0): "build: " + build;
			this.major = major;
			this.minor = minor;
			this.build = build;
		}

		@Override
		public String toString()
		{
			return major + "." + minor + "." + build;
		}
	}
	public final Type type;
	public final Version version;

	/**
	 * @param type    the type of the operating system
	 * @param version the version of the operating system; null if unknown
	 * @throws AssertionError if {@code type} is null
	 */
	OperatingSystem(Type type, Version version)
	{
		assert (type != null): "type may not be null";
		this.type = type;
		this.version = version;
	}

	/**
	 * Operating system types.
	 */
	public enum Type
	{
		WINDOWS,
		LINUX,
		MAC,
		UNKNOWN;

		/**
		 * @return the type of the current operating system
		 */
		public static Type current()
		{
			String osName = System.getProperty("os.name");
			if (Strings.startsWith(osName, "windows", true))
				return WINDOWS;
			if (Strings.startsWith(osName, "linux", true))
				return LINUX;
			if (Strings.startsWith(osName, "mac", true))
				return MAC;
			return UNKNOWN;
		}
	}

	@Override
	public String toString()
	{
		return type + " " + version;
	}
}
