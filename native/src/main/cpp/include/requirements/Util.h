#ifndef requirements_Util
#define requirements_Util

#ifdef _WIN32

#include <jni.h>

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

#ifdef __cplusplus
extern "C" {
#endif
	class Exceptions
	{
	private:
		const DWORD DEFAULT_LANGUAGE = MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT);
		JNIEnv* env;
		/**
		* Returns the String representation of the error. The caller must the LocalFree() function to free the buffer when it is no longer needed.
		*
		* @param errorCode an error code returned by GetLastError()
		* @return 0 on failure
		*/
		char* getFormatMessage(DWORD errorCode);
		/**
		 * Throws an exception.
		 * @param type the exception type
		 * @param message the exception message
		 */
		void throwException(char* type, char* message);

	public:
		/**
		 * @param env the JNI interface pointer
		 */
		Exceptions(JNIEnv* env);
		void throwIOException(char* message);
	};

#ifdef __cplusplus
}
#endif // __cplusplus
#endif // _WIN32
#endif // requirements_Util