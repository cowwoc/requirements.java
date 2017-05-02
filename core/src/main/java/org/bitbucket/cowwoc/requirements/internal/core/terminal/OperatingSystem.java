/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.terminal;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import static org.bitbucket.cowwoc.requirements.internal.core.terminal.OperatingSystem.Type.WINDOWS;
import org.bitbucket.cowwoc.requirements.internal.core.util.Strings;

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
	private static final Pattern VERSION_PATTERN = Pattern.compile(
		"(\\d+)(?:\\.(\\d+)(?:\\.(\\d+))?)?");

	private static final Reference<OperatingSystem> DETECTED = ConcurrentLazyReference.create(() ->
	{
		Type type = Type.detected();
		Version version = Version.detected();
		Architecture architecture = Architecture.detected();
		return new OperatingSystem(type, version, architecture);
	});

	/**
	 * @return the detected operating system
	 * @throws AssertionError if the operating system is unsupported
	 */
	public static OperatingSystem detected()
	{
		return DETECTED.getValue();
	}

	/**
	 * The version number of an operating system.
	 */
	public static final class Version implements Comparable<Version>
	{
		private static final Reference<Version> DETECTED = ConcurrentLazyReference.create(() ->
		{
			Type type = Type.detected();
			switch (type)
			{
				case WINDOWS:
					return getWindowsVersion();
				default:
					return getVersionUsingJava(System.getProperty("os.version"));
			}
		});

		/**
		 * @return the version of the detected operating system
		 */
		public static Version detected()
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
		 * @param osVersion the operating system version
		 * @return the version of Linux
		 * @throws AssertionError if the version is not supported
		 */
		static Version getVersionUsingJava(String osVersion)
		{
			Matcher matcher = VERSION_PATTERN.matcher(osVersion);
			if (!matcher.find())
				throw new AssertionError("Unsupported version: " + osVersion);
			int major;
			int minor;
			int build;
			try
			{
				major = Integer.parseInt(matcher.group(1));
				if (matcher.group(2) == null)
					return new Version(major);
				minor = Integer.parseInt(matcher.group(2));
				if (matcher.group(3) == null)
					return new Version(major, minor);
				build = Integer.parseInt(matcher.group(3));
				return new Version(major, minor, build);
			}
			catch (NumberFormatException e)
			{
				throw new AssertionError("Failed to parse version: " + osVersion, e);
			}
		}
		public final int major;
		public final Integer minor;
		/**
		 * The minor number, or zero if the component is absent.
		 */
		public final int minorOrZero;
		public final Integer build;
		/**
		 * The build number, or zero if the component is absent.
		 */
		public final int buildOrZero;

		/**
		 * @param major the major component of the version number
		 * @param minor the minor component of the version number
		 * @param build the build component of the version number
		 * @throws AssertionError if any of the components are negative
		 */
		public Version(int major, int minor, int build)
		{
			assert (major >= 0): "major: " + major;
			assert (minor >= 0): "minor: " + minor;
			assert (build >= 0): "build: " + build;
			this.major = major;
			this.minor = minor;
			this.minorOrZero = minor;
			this.build = build;
			this.buildOrZero = build;
		}

		/**
		 * @param major the major component of the version number
		 * @param minor the minor component of the version number
		 * @throws AssertionError if any of the components are negative
		 */
		public Version(int major, int minor)
		{
			assert (major >= 0): "major: " + major;
			assert (minor >= 0): "minor: " + minor;
			this.major = major;
			this.minor = minor;
			this.minorOrZero = minor;
			this.build = null;
			this.buildOrZero = 0;
		}

		/**
		 * @param major the major component of the version number
		 * @throws AssertionError if {@code major} is negative
		 */
		public Version(int major)
		{
			assert (major >= 0): "major: " + major;
			this.major = major;
			this.minor = null;
			this.minorOrZero = 0;
			this.build = null;
			this.buildOrZero = 0;
		}

		@Override
		public int compareTo(Version other)
		{
			int result = major - other.major;
			if (result != 0)
				return result;
			result = minorOrZero - other.minorOrZero;
			if (result != 0)
				return result;
			return buildOrZero - other.buildOrZero;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof Version))
				return false;
			Version other = (Version) obj;
			return major == other.major && minorOrZero == other.minorOrZero &&
				buildOrZero == other.buildOrZero;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(major, minor, build);
		}

		@Override
		public String toString()
		{
			StringBuilder result = new StringBuilder(10);
			result.append(major);
			if (minor != null)
				result.append(".").append(minor);
			if (build != null)
				result.append(".").append(build);
			return result.toString();
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
		public static Architecture detected()
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
		public static Type detected()
		{
			return DETECTED.getValue();
		}
	}
}
