#include <stdio.h>
#include <wchar.h>

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
#include "requirements/Util.h"
#include "requirements/Terminal.h"

jboolean JNICALL Java_org_bitbucket_cowwoc_requirements_diff_string_Terminal_stdoutIsTerminal
(JNIEnv* env, jobject jthis)
{
	// See http://stackoverflow.com/a/3650507/14731
	CONSOLE_SCREEN_BUFFER_INFO sbi;
	return GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &sbi) && GetLastError() == ERROR_INVALID_HANDLE;
}

struct State
{
	HANDLE hStdout;
	DWORD stdoutMode;
};

/*
* org.bitbucket.cowwoc.requirements.diff.string.Terminal.start()
*
* @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
*/
jlong JNICALL Java_org_bitbucket_cowwoc_requirements_diff_string_Terminal_start
(JNIEnv* env, jobject jthis)
{
	Exceptions exceptions(env);
	State* state = new State();

	state->hStdout = GetStdHandle(STD_OUTPUT_HANDLE);
	if (state->hStdout == INVALID_HANDLE_VALUE)
	{
		exceptions.throwIOException("Failed to get stdout handle");
		return 0;
	}

	if (!GetConsoleMode(state->hStdout, &state->stdoutMode))
	{
		delete state;
		exceptions.throwIOException("Failed to get stdout mode");
		return 0;
	}

	DWORD newStdoutMode = state->stdoutMode | ENABLE_VIRTUAL_TERMINAL_PROCESSING;
	if (!SetConsoleMode(state->hStdout, newStdoutMode))
	{
		delete state;
		exceptions.throwIOException("Failed to set stdout mode");
		return 0;
	}
	return reinterpret_cast<jlong>(state);
}

/*
* org.bitbucket.cowwoc.requirements.diff.string.Terminal.stop()
*
* @see https://msdn.microsoft.com/en-us/library/windows/desktop/mt638032(v=vs.85).aspx#Output_Sequences
*/
void JNICALL Java_org_bitbucket_cowwoc_requirements_diff_string_Terminal_stop
(JNIEnv* env, jobject jthis, jlong nativeData)
{
	State* state = reinterpret_cast<State*>(nativeData);
	SetConsoleMode(state->hStdout, state->stdoutMode);
	delete state;
}

#elif defined (__linux__)

jboolean JNICALL Java_org_bitbucket_cowwoc_requirements_diff_string_Terminal_stdoutIsTerminal
(JNIEnv* env, jobject jthis)
{
	return isatty(STDOUT_FILENO);
}

#endif