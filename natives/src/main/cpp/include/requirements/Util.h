#ifndef requirements_Util
#define requirements_Util

#include <jni.h>

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

#ifdef __cplusplus
extern "C" {
#endif
	class Exceptions
	{
	private:
#ifdef _WIN32
			const DWORD DEFAULT_LANGUAGE = MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT);
			/**
			* Returns the String representation of the error. The caller must the LocalFree() function to
			* free the buffer when it is no longer needed.
			*
			* @param errorCode the error code returned by GetLastError()
			* @return 0 on failure
			*/
			char* getFormatMessage(DWORD errorCode);
#endif // _WIN32
		JNIEnv* env;
		/**
		 * Throws an exception.
		 * @param type the exception type
		 * @param message the exception message
		 */
		void throwException(const char* type, const char* message);

	public:
		/**
		 * @param env the JNI interface pointer
		 * @param lastError the error code returned by GetLastError()
		 */
		Exceptions(JNIEnv* env);
		/**
		 * Throws an IOException.
		 *
		 * @param message the exception message
		 */
		void throwIOException(const char* message);
#ifdef _WIN32
			/**
			 * Throws an IOException.
			 *
			 * @param lastError the error code returned by GetLastError()
			 * @param message the exception message
			 */
			void throwIOException(const char* message, DWORD lastError);
#endif // _WIN32
		/**
		 * Throws an UnsupportedOperationException.
		 *
		 * @param message the exception message
		 */
		void throwUnsupportedOperationException(const char* message);
	};

#ifdef __cplusplus
}
#endif // __cplusplus
#endif // requirements_Util