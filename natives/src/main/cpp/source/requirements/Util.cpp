#include <assert.h>
#include <stdio.h>
#include <wchar.h>
#include <string>

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
#endif // _WIN32

#include "requirements/Util.h"


#ifdef _WIN32
	char* Exceptions::getFormatMessage(DWORD errorCode)
	{
		char* result;

		if (FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER |
			FORMAT_MESSAGE_FROM_SYSTEM |
			FORMAT_MESSAGE_IGNORE_INSERTS,
			NULL,
			errorCode,
			DEFAULT_LANGUAGE,
			(LPTSTR) &result,
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

	void Exceptions::throwIOException(const char* message, DWORD lastError)
	{
		assert(message != 0);
		char* context;
		if (lastError != 0)
		{
			context = getFormatMessage(lastError);
			if (!context)
				throwException("java/lang/AssertionError", "FormatMessage failed");
		}

		std::string joinedMessage(message);
		if (lastError != 0)
		{
			joinedMessage += ": ";
			joinedMessage += context;
		}

		throwException("java/io/IOException", joinedMessage.c_str());

		if (lastError != 0)
		{
			HLOCAL address = LocalFree(context);
			assert(address == 0);
		}
	}
#endif // _WIN32

Exceptions::Exceptions(JNIEnv* _env) : env(_env)
{}

void Exceptions::throwException(const char* type, const char* message)
{
	assert(type != 0);
	assert(message != 0);
	jclass exception = env->FindClass(type);
	if (exception == 0)
		return;
	env->ThrowNew(exception, message);
}

void Exceptions::throwIOException(const char* message)
{
	assert(message != 0);
	throwException("java/io/IOException", message);
}

void Exceptions::throwUnsupportedOperationException(const char* message)
{
	assert(message != 0);
	throwException("java/lang/UnsupportedOperationException", message);
}