/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.terminal;

import io.github.cowwoc.pouch.core.ConcurrentLazyReference;
import io.github.cowwoc.pouch.core.Reference;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An operating system.
 */
public final class OperatingSystem
{
	/**
	 * Extracts the Windows version number from the output of the "ver" command.
	 */
	private static final Pattern WINDOWS_VERSION = Pattern.compile("Microsoft Windows \\[Version ([^]]+)]");
	/**
	 * Version format used by Linux and Mac.
	 */
	private static final Pattern VERSION_PATTERN =
		Pattern.compile("(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:\\.(\\d+))?)?)?");

	private static final Reference<OperatingSystem> DETECTED = ConcurrentLazyReference.create(() ->
	{
		Type type = Type.detected();
		Version version = Version.detected();
		Architecture architecture = Architecture.detected();
		return new OperatingSystem(type, version, architecture);
	});
	/**
	 * The type of the operating system.
	 */
	public final Type type;
	/**
	 * The version of the operating system.
	 */
	public final Version version;
	/**
	 * The architecture of the operating system.
	 */
	public final Architecture architecture;

	/**
	 * @param type         the type of the operating system
	 * @param version      the version of the operating system
	 * @param architecture the architecture of the operating system
	 * @throws NullPointerException if any of the arguments are null
	 */
	private OperatingSystem(Type type, Version version, Architecture architecture)
	{
		assert type != null : "type may not be null";
		assert version != null : "version may not be null";
		assert architecture != null : "architecture may not be null";
		this.type = type;
		this.version = version;
		this.architecture = architecture;
	}

	/**
	 * @return the detected operating system
	 * @throws IllegalStateException if the operating system is unsupported
	 */
	public static OperatingSystem detected()
	{
		return DETECTED.getValue();
	}

	@Override
	public String toString()
	{
		return type + " " + version + " " + architecture;
	}

	/**
	 * The architecture of an operating system.
	 * <p>
	 * Naming convention based on <a href="https://github.com/trustin/os-maven-plugin">os-maven-plugin</a>.
	 */
	public enum Architecture
	{
		/**
		 * x86 32-bit.
		 */
		X86_32,
		/**
		 * x86 64-bit.
		 */
		X86_64,
		/**
		 * ARM 64-bit
		 */
		AARCH_64;

		private static final Reference<Architecture> DETECTED = ConcurrentLazyReference.create(() ->
		{
			String osArch = System.getProperty("os.arch");
			osArch = osArch.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
			return switch (osArch)
			{
				case "x8632", "x86", "i386", "i486", "i586", "i686", "ia32", "x32" -> X86_32;
				case "x8664", "amd64", "ia32e", "em64t", "x64" -> X86_64;
				case "aarch64" -> AARCH_64;
				default -> throw new IllegalStateException("Unsupported architecture: " + osArch + "\n" +
					"properties: " + System.getProperties());
			};
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
		/**
		 * Windows.
		 */
		WINDOWS,
		/**
		 * Linux.
		 */
		LINUX,
		/**
		 * macOS.
		 */
		MAC;

		private static final Reference<Type> DETECTED = ConcurrentLazyReference.create(() ->
		{
			String osName = System.getProperty("os.name");
			if (startsWith(osName, "windows", true))
				return WINDOWS;
			if (startsWith(osName, "linux", true))
				return LINUX;
			if (startsWith(osName, "mac", true))
				return MAC;
			throw new IllegalStateException("Unsupported operating system: " + osName + "\n" +
				"properties: " + System.getProperties());
		});

		/**
		 * @return the type of the detected operating system
		 */
		public static Type detected()
		{
			return DETECTED.getValue();
		}

		/**
		 * Indicates if {@code text} starts with {@code prefix}, disregarding case sensitivity.
		 *
		 * @param text       a string
		 * @param prefix     a prefix
		 * @param ignoreCase {@code true} if case should be ignored when comparing characters
		 * @return true if {@code text} starts with {@code prefix}, disregarding case sensitivity
		 * @throws NullPointerException if any of the arguments are null
		 */
		public static boolean startsWith(String text, String prefix, boolean ignoreCase)
		{
			return text.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
		}
	}

	/**
	 * The version number of an operating system.
	 */
	public static final class Version implements Comparable<Version>
	{
		private static final Reference<Version> DETECTED = ConcurrentLazyReference.create(() ->
		{
			if (Type.detected() == Type.WINDOWS)
				return getWindowsVersion();
			return parseVersion(System.getProperty("os.version"));
		});
		/**
		 * The value of absent version components.
		 */
		private static final int ABSENT = -1;
		/**
		 * The major version number.
		 */
		private final int major;
		/**
		 * The minor version number.
		 */
		private final int minor;
		/**
		 * The build version number.
		 */
		private final int build;
		/**
		 * The hotfix version number. See Stackoverflow for
		 * <a href="https://softwareengineering.stackexchange.com/a/171381/42177">more details</a>.
		 */
		private final int revision;

		/**
		 * @param major the major component of the version number
		 * @throws IllegalArgumentException if {@code major} is negative
		 */
		public Version(int major)
		{
			this(major, null, null, null);
		}

		/**
		 * @param major the major component of the version number
		 * @param minor the minor component of the version number
		 * @throws IllegalArgumentException if any of the components are negative
		 */
		public Version(int major, int minor)
		{
			this(major, minor, null, null);
		}

		/**
		 * @param major the major component of the version number
		 * @param minor the minor component of the version number
		 * @param build the build component of the version number
		 * @throws IllegalArgumentException if any of the components are negative
		 */
		public Version(int major, int minor, int build)
		{
			this(major, minor, build, null);
		}

		/**
		 * @param major    the major component of the version number
		 * @param minor    the minor component of the version number
		 * @param build    the build component of the version number
		 * @param revision the revision component of the version number
		 * @throws IllegalArgumentException if any of the components are negative
		 */
		public Version(int major, Integer minor, Integer build, Integer revision)
		{
			assert major >= 0 : "major: " + major;
			this.major = major;

			if (minor == null)
				this.minor = ABSENT;
			else
			{
				assert minor >= 0 : "minor: " + minor;
				this.minor = minor;
			}

			if (build == null)
				this.build = ABSENT;
			else
			{
				assert build >= 0 : "build : " + build;
				this.build = build;
			}

			if (revision == null)
				this.revision = ABSENT;
			else
			{
				assert revision >= 0 : "revision: " + revision;
				this.revision = revision;
			}
		}

		/**
		 * @return the version of the detected operating system
		 */
		public static Version detected()
		{
			return DETECTED.getValue();
		}

		/**
		 * @return the version of Windows
		 * @throws IllegalStateException if the version is not supported
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
				throw new IllegalStateException("Failed to get Windows version", e);
			}
			Matcher matcher = WINDOWS_VERSION.matcher(versionAsString);
			if (!matcher.find())
				throw new IllegalStateException("Unsupported Windows version: " + versionAsString);
			return parseVersion(matcher.group(1));
		}

		/**
		 * @param versionAsString the String representation of the version number
		 * @return the version of the operating system
		 * @throws IllegalStateException if the version is not supported
		 */
		public static Version parseVersion(String versionAsString)
		{
			Matcher matcher = VERSION_PATTERN.matcher(versionAsString);
			if (!matcher.find())
				throw new IllegalStateException("Unsupported version: " + versionAsString);
			try
			{
				int major = Integer.parseInt(matcher.group(1));
				if (matcher.group(2) == null)
					return new Version(major);
				int minor = Integer.parseInt(matcher.group(2));
				if (matcher.group(3) == null)
					return new Version(major, minor);
				int build = Integer.parseInt(matcher.group(3));
				if (matcher.group(4) == null)
					return new Version(major, minor, build);
				int revision = Integer.parseInt(matcher.group(4));
				return new Version(major, minor, build, revision);
			}
			catch (NumberFormatException e)
			{
				throw new IllegalStateException("Failed to parse version: " + versionAsString, e);
			}
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
			result = build - other.build;
			if (result != 0)
				return result;
			return revision - other.revision;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof Version other))
				return false;
			return major == other.major && minor == other.minor &&
				build == other.build && revision == other.revision;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(major, minor, build, revision);
		}

		@Override
		public String toString()
		{
			StringBuilder result = new StringBuilder(10);
			result.append(major);
			if (minor != ABSENT)
				result.append('.').append(minor);
			if (build != ABSENT)
				result.append('.').append(build);
			if (revision != ABSENT)
				result.append('.').append(revision);
			return result.toString();
		}
	}
}