#include <assert.h>
#include <string>
#include <deque>

#include "requirements/Util.h"
#include "requirements/NativeTerminal.h"

jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
{
	return JNI_VERSION_1_6;
}

#ifdef _WIN32
	#ifndef VC_EXTRALEAN
	#define VC_EXTRALEAN		// Exclude rarely-used stuff from Windows headers
	#endif

	// Modify the following defines if you have to target a platform prior to the ones specified below.
	// Refer to MSDN for the latest info on corresponding values for different platforms.
	#ifndef WINVER
	#define WINVER 0x0A00
	#endif

	#ifndef _WIN32_WINNT
	#define _WIN32_WINNT 0x0A00
	#endif

	#include <windows.h>
	#include <wincon.h>
	#include <VersionHelpers.h>

	static struct State
	{
		/**
		 * True after connected() is called.
		 */
		bool connected;
		/**
		 * True if stdout is connected to the terminal.
		 */
		bool connectedToStdout;
		/**
		 * The stdout handle.
		 */
		HANDLE stdoutHandle;
		/**
		* The stdout mode when open() was invoked (prior to any modification).
		*/
		DWORD stdoutMode;
		/**
		* The current mode of stdout.
		*/
		DWORD currentMode;
	} state;
#endif // _WIN32

/**
 * @param env the JVM environment
 * @param name the name of the terminal encoding
 * @return the TerminalEncoding enum value corresponding to the name
 */
jobject terminalEncoding(JNIEnv* env, const char* name)
{
	const char* enumName = "org/bitbucket/cowwoc/requirements/core/terminal/TerminalEncoding";
	jclass enumClass = env->FindClass(enumName);
	if (enumClass == 0)
	{
		assert(env->ExceptionCheck());
		return 0;
	}

	std::string enumFieldType("L");
	enumFieldType += enumName;
	enumFieldType += ";";
	jfieldID enumField = env->GetStaticFieldID(enumClass, name, enumFieldType.c_str());
	if (enumField == 0)
	{
		assert(env->ExceptionCheck());
		return 0;
	}
	return env->GetStaticObjectField(enumClass, enumField);
}

/**
* @param env the JNI environment
* @param o an object
* @return the String representation of the object
*/
std::string toString(JNIEnv* env, jobject o)
{
	jclass object = env->FindClass("java/lang/Object");
	if (object == 0)
	{
		assert(env->ExceptionCheck());
		return 0;
	}
	jmethodID toString = env->GetMethodID(object, "toString", "()Ljava/lang/String;");
	jstring result = (jstring) env->CallObjectMethod(o, toString);
	const char* charArray = env->GetStringUTFChars(result, 0);
	return std::string(charArray);
}

#ifdef _WIN32
	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.connect()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	void JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_connect
	(JNIEnv* env, jobject jthis)
	{
		state.connected = true;

		Exceptions exceptions(env);
		// See http://stackoverflow.com/a/3650507/14731
		CONSOLE_SCREEN_BUFFER_INFO sbi;
		HANDLE stdoutHandle = GetStdHandle(STD_OUTPUT_HANDLE);
		BOOL rc = GetConsoleScreenBufferInfo(stdoutHandle, &sbi);
		DWORD lastError = GetLastError();
		state.connectedToStdout = rc && lastError != ERROR_INVALID_HANDLE;
		if (state.connectedToStdout)
		{
			state.stdoutHandle = GetStdHandle(STD_OUTPUT_HANDLE);
			if (state.stdoutHandle == INVALID_HANDLE_VALUE)
			{
				exceptions.throwIOException("Failed to get stdout handle");
				return;
			}

			if (!GetConsoleMode(state.stdoutHandle, &state.stdoutMode))
				exceptions.throwIOException("Failed to get stdout mode", GetLastError());
			state.currentMode = state.stdoutMode;
		}
	}

	typedef NTSTATUS(WINAPI* RtlGetVersionPtr)(PRTL_OSVERSIONINFOW);

	/**
	 * @param majorVersion a major version number
	 * @param minorVersion a minor version number
	 * @param buildVersion a build version number
	 * @return true if the Windows version is greater than or equal to the specified number
	 */
	bool isWindowsVersionOrGreater(WORD majorVersion, WORD minorVersion, WORD buildVersion)
	{
		// See http://stackoverflow.com/a/36545162/14731 and https://github.com/DarthTon/Blackbone/blob/master/contrib/VersionHelpers.h#L78
		RTL_OSVERSIONINFOW verInfo = { 0 };
		verInfo.dwOSVersionInfoSize = sizeof(verInfo);

		static auto RtlGetVersion = (RtlGetVersionPtr) GetProcAddress(GetModuleHandleW(L"ntdll.dll"), "RtlGetVersion");

		if (RtlGetVersion != 0 && RtlGetVersion(&verInfo) == 0)
		{
			if (verInfo.dwMajorVersion > majorVersion)
				return true;
			else if (verInfo.dwMajorVersion < majorVersion)
				return false;

			if (verInfo.dwMinorVersion > minorVersion)
				return true;
			else if (verInfo.dwMinorVersion < minorVersion)
				return false;

			if (verInfo.dwBuildNumber >= buildVersion)
				return true;
		}
		return false;
	}

	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.isConnectedToStdout()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	jboolean JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_isConnectedToStdout
	(JNIEnv* env, jobject jthis)
	{
		return state.connectedToStdout;
	}

	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.setEncoding()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	void JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_setEncoding
	(JNIEnv* env, jobject jthis, jobject encoding)
	{
		Exceptions exceptions(env);
		jobject NONE = terminalEncoding(env, "NONE");
		DWORD newStdoutMode;
		if (env->IsSameObject(encoding, NONE))
			newStdoutMode = state.stdoutMode & !ENABLE_VIRTUAL_TERMINAL_PROCESSING;
		else
		{
			std::deque<char*> supportedEncodings;
			// build 10586 added 16-bit color support: http://www.nivot.org/blog/post/2016/02/04/Windows-10-TH2-%28v1511%29-Console-Host-Enhancements
			assert(IsWindowsVersionOrGreater(10, 0, 10586));
			supportedEncodings.push_back("XTERM_8COLOR");
			supportedEncodings.push_back("XTERM_16COLOR");

			if (IsWindowsVersionOrGreater(10, 0, 14931))
			{
				// build 14931 added 24-bit color support: https://blogs.msdn.microsoft.com/commandline/2016/09/22/24-bit-color-in-the-windows-console/
				supportedEncodings.push_back("RGB_888COLOR");
			}

			bool matchFound;
			for (std::deque<char*>::iterator i = supportedEncodings.begin(); i != supportedEncodings.end(); ++i)
			{
				jobject expectedEnum = terminalEncoding(env, *i);
				if (env->IsSameObject(encoding, expectedEnum))
				{
					matchFound = true;
					break;
				}
			}
			if (!matchFound)
			{
				supportedEncodings.push_front("NONE");
				std::string message("Expected encoding to be one of [");
				for (std::deque<char*>::iterator i = supportedEncodings.begin(); i != supportedEncodings.end(); ++i)
				{
					message += *i;
					if (i != supportedEncodings.end() - 1)
						message += ", ";
				}
				message += "].\n";
				message += "Actual: ";
				message += toString(env, encoding);
				exceptions.throwIOException(message.c_str());
				return;
			}
			newStdoutMode = state.stdoutMode | ENABLE_VIRTUAL_TERMINAL_PROCESSING;
		}
		if (state.currentMode != newStdoutMode)
		{
			if (!SetConsoleMode(state.stdoutHandle, newStdoutMode))
			{
				exceptions.throwIOException("Failed to set stdout mode", GetLastError());
				return;
			}
			state.currentMode = newStdoutMode;
		}
	}

	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.disconnect()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	void JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_disconnect
	(JNIEnv* env, jobject jthis)
	{
		if (!state.connected)
			return;
		if (state.currentMode != state.stdoutMode && !SetConsoleMode(state.stdoutHandle, state.stdoutMode))
		{
			DWORD lastError = GetLastError();
			Exceptions exceptions(env);
			exceptions.throwIOException("Failed to set stdout mode", lastError);
		}
		state.connected = false;
	}
#elif defined (__linux__) || defined (__APPLE__)
	#include <unistd.h>

	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.connect()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	void JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_connect
	(JNIEnv* env, jobject jthis)
	{
	}

	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.isConnectedToStdout()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	jboolean JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_isConnectedToStdout
	(JNIEnv* env, jobject jthis)
	{
		return isatty(STDOUT_FILENO);
	}

	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.setEncoding()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	void JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_setEncoding
	(JNIEnv* env, jobject jthis, jobject encoding)
	{
		Exceptions exceptions(env);
		jobject NONE = terminalEncoding(env, "NONE");
		if (env->IsSameObject(encoding, NONE))
			return;
		std::string message("Unexpected encoding: ");
		message += toString(env, encoding);
		exceptions.throwIOException(message.c_str());
	}

	/**
	 * org.bitbucket.cowwoc.requirements.internal.core.terminal.NativeTerminal.disconnect()
	 *
	 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
	 */
	void JNICALL Java_org_bitbucket_cowwoc_requirements_internal_core_terminal_NativeTerminal_disconnect
	(JNIEnv* env, jobject jthis)
	{
	}
#endif // _WIN32