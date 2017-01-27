/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.terminal;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import static org.bitbucket.cowwoc.requirements.core.terminal.OperatingSystem.Type.UNKNOWN;
import static org.bitbucket.cowwoc.requirements.core.terminal.OperatingSystem.Type.WINDOWS;
import org.bitbucket.cowwoc.requirements.core.util.Strings;
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

	private static final Reference<OperatingSystem> DETECTED = ConcurrentLazyReference.create(() ->
	{
		Logger log = LoggerFactory.getLogger(OperatingSystem.class);
		Type type = Type.detect();
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
				return new OperatingSystem(type, null);
		}
	});

	/**
	 * @return the detected operating system
	 */
	public static OperatingSystem detect()
	{
		return DETECTED.getValue();
	}

	/**
	 * The version number of an operating system.
	 */
	public static final class Version implements Comparable<Version>
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
		public int compareTo(Version other)
		{
			int result = major - other.major;
			if (result != 0)
				return result;
			result = minor - other.minor;
			if (result != 0)
				return result;
			return build - other.build;
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

	@Override
	public String toString()
	{
		return type + " " + version;
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

		private static final Reference<Type> DETECTED = ConcurrentLazyReference.create(() ->
		{
			String osName = System.getProperty("os.name");
			if (Strings.startsWith(osName, "windows", true))
				return WINDOWS;
			if (Strings.startsWith(osName, "linux", true))
				return LINUX;
			if (Strings.startsWith(osName, "mac", true))
				return MAC;

			Logger log = LoggerFactory.getLogger(OperatingSystem.class);
			log.warn("Unknown operating system: {}, os.name: {}", OperatingSystem.detect().type.name(),
				System.getProperty("os.name"));
			return UNKNOWN;
		});

		/**
		 * @return the type of the detected operating system
		 */
		public static Type detect()
		{
			return DETECTED.getValue();
		}
	}
}
