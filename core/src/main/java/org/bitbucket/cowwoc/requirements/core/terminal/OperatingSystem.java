/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.terminal;

import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import static org.bitbucket.cowwoc.requirements.core.terminal.OperatingSystem.Type.WINDOWS;
import org.bitbucket.cowwoc.requirements.core.util.Strings;

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
	 * Version format used by Linux and Mac.
	 */
	private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");

	private static final Reference<OperatingSystem> DETECTED = ConcurrentLazyReference.create(
		() ->
	{
		Type type = Type.detect();
		Version version = Version.detect();
		Architecture architecture = Architecture.detect();
		return new OperatingSystem(type, version, architecture);
	});

	/**
	 * @return the detected operating system
	 * @throws AssertionError if the operating system is unsupported
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
		private static final Reference<Version> DETECTED = ConcurrentLazyReference.create(
			() ->
		{
			Type type = Type.detect();
			switch (type)
			{
				case WINDOWS:
					return getWindowsVersion();
				default:
					return getVersionUsingJava();
			}
		});

		/**
		 * @return the version of the detected operating system
		 */
		public static Version detect()
		{
			return DETECTED.getValue();
		}

		/**
		 * @return the version of Windows
		 * @throws AssertionError if the version is not supported
		 */
		private static Version getWindowsVersion()
		{
			String versionAsString;
			try
			{
				versionAsString = Processes.run("cmd.exe", "/c", "ver");
			}
			catch (IOException e)
			{
				throw new AssertionError("Failed to get Windows version", e);
			}
			Matcher matcher = WINDOWS_VERSION.matcher(versionAsString);
			if (!matcher.find())
				throw new AssertionError("Unsupported Windows version: " + versionAsString);
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
				throw new AssertionError("Failed to parse Windows version: " + versionAsString, e);
			}
			return new Version(major, minor, build);
		}

		/**
		 * @return the version of Linux
		 * @throws AssertionError if the version is not supported
		 */
		private static Version getVersionUsingJava()
		{
			String versionAsString = System.getProperty("os.version");
			Matcher matcher = VERSION_PATTERN.matcher(versionAsString);
			if (!matcher.find())
				throw new AssertionError("Unsupported version: " + versionAsString);
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
				throw new AssertionError("Failed to parse version: " + versionAsString, e);
			}
			return new Version(major, minor, build);
		}
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
	public final Architecture architecture;

	/**
	 * @param type         the type of the operating system
	 * @param version      the version of the operating system
	 * @param architecture the architecture of the operating system
	 * @throws AssertionError if any of the arguments are null
	 */
	OperatingSystem(Type type, Version version, Architecture architecture)
	{
		assert (type != null): "type may not be null";
		assert (version != null): "version may not be null";
		assert (architecture != null): "architecture may not be null";
		this.type = type;
		this.version = version;
		this.architecture = architecture;
	}

	@Override
	public String toString()
	{
		return type + " " + version + " " + architecture;
	}

	/**
	 * The architecture of an operating system.
	 * <p>
	 * Naming convention based on https://github.com/trustin/os-maven-plugin.
	 */
	public enum Architecture
	{
		X86_32,
		X86_64;

		private static final Reference<Architecture> DETECTED = ConcurrentLazyReference.create(() ->
		{
			String osArch = System.getProperty("os.arch");
			osArch = osArch.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
			switch (osArch)
			{
				case "x8632":
				case "x86":
				case "i386":
				case "i486":
				case "i586":
				case "i686":
				case "ia32":
				case "x32":
					return X86_32;
				case "x8664":
				case "amd64":
				case "ia32e":
				case "em64t":
				case "x64":
					return X86_64;
				default:
					throw new AssertionError("Unsupported architecture: " + osArch + "\n" +
						"properties: " + System.getProperties());
			}
		});

		/**
		 * @return the architecture of the detected operating system
		 */
		public static Architecture detect()
		{
			return DETECTED.getValue();
		}
	}

	/**
	 * Operating system types.
	 */
	public enum Type
	{
		WINDOWS,
		LINUX,
		MAC;

		private static final Reference<Type> DETECTED = ConcurrentLazyReference.create(() ->
		{
			String osName = System.getProperty("os.name");
			if (Strings.startsWith(osName, "windows", true))
				return WINDOWS;
			if (Strings.startsWith(osName, "linux", true))
				return LINUX;
			if (Strings.startsWith(osName, "mac", true))
				return MAC;
			throw new AssertionError("Unsupported operating system: " + osName + "\n" +
				"properties: " + System.getProperties());
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
