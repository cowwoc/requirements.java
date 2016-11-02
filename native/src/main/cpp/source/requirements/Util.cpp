#include <assert.h>
#include <stdio.h>
#include <wchar.h>

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

#include "requirements/Util.h"

Exceptions::Exceptions(JNIEnv* _env) : env(_env)
{}

void Exceptions::throwException(char* type, char* message)
{
	assert(type != 0);
	assert(message != 0);
	jclass exception = env->FindClass(type);
	if (exception == 0)
		return;
	env->ThrowNew(exception, message);
}

void Exceptions::throwIOException(char* message)
{
	assert(message != 0);
	char* context = getFormatMessage(GetLastError());
	if (!context)
		throwException("java/lang/AssertionError", "FormatMessage failed");

	// printf("Attach debugger...\n");
	// fgetc(stdin);

	size_t len = strlen(message) + strlen(": ") + strlen(context) + 1;
	char* joinedMessage = new char[len];
	joinedMessage[0] = '\0';
	errno_t rc = strcat_s(joinedMessage, len, message);
	assert(rc == 0);
	rc = strcat_s(joinedMessage, len, ": ");
	assert(rc == 0);
	rc = strcat_s(joinedMessage, len, context);
	assert(rc == 0);

	throwException("java/io/IOException", joinedMessage);

	HLOCAL address = LocalFree(context);
	assert(address == 0);
}

/**
* Returns the String representation of the error. The caller must the LocalFree() function to free the buffer when it is no longer needed.
*
* @param errorCode an error code returned by GetLastError().
* @return 0 on failure
*/
char* Exceptions::getFormatMessage(DWORD errorCode)
{
	char* result;

	if (FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER |
		FORMAT_MESSAGE_FROM_SYSTEM |
		FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		errorCode,
		DEFAULT_LANGUAGE,
		(LPTSTR)&result,
		0,
		NULL))
	{
		size_t len = strlen(result);
		if (result[len - 1] == '\n')
			result[len - 1] = 0;
		return result;
	}
	return 0;
}
