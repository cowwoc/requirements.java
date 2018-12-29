/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal */

#ifndef _Included_org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal
#define _Included_org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal
 * Method:    connect
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal_connect
  (JNIEnv *, jobject);

/*
 * Class:     org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal
 * Method:    isConnectedToStdout
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal_isConnectedToStdout
  (JNIEnv *, jobject);

/*
 * Class:     org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal
 * Method:    setEncoding
 * Signature: (Lorg/bitbucket/cowwoc/requirements/java/terminal/TerminalEncoding;)V
 */
JNIEXPORT void JNICALL Java_org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal_setEncoding
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal
 * Method:    disconnect
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_bitbucket_cowwoc_requirements_java_internal_terminal_NativeTerminal_disconnect
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif