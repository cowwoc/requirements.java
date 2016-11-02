#############
# FUNCTIONS #
#############

# Returns all header files in the current directory, recursing into sub-directories.
FUNCTION (get_headers DIRECTORY RESULT)
	FILE (GLOB_RECURSE HEADER_FILES_TMP "${DIRECTORY}/*.h" "${DIRECTORY}/*.hpp" "${DIRECTORY}/*.inl" "${DIRECTORY}/*.pch" "${DIRECTORY}/*.tsd" "${DIRECTORY}/*.tsp")
	SET (${RESULT} ${HEADER_FILES_TMP} PARENT_SCOPE)
ENDFUNCTION ()

# Returns all source files in the current directory, recursing into sub-directories
FUNCTION (get_sources DIRECTORY RESULT)
	FILE (GLOB_RECURSE SOURCE_FILES_TMP "${DIRECTORY}/*.c" "${DIRECTORY}/*.cpp")
	SET (${RESULT} ${SOURCE_FILES_TMP} PARENT_SCOPE)
ENDFUNCTION ()

################
# Build script #
################

CMAKE_MINIMUM_REQUIRED (VERSION 3.6.2)
PROJECT (requirements)

SET (requirements_VERSION_MAJOR 1)
SET (requirements_VERSION_MINOR 0)
ADD_DEFINITIONS (-DREQUIREMENTS_EXPORTS)

SET (CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} /MP")
IF (CMAKE_COMPILER_IS_GNUCXX)
	SET (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Werror")
ENDIF (CMAKE_COMPILER_IS_GNUCXX)

IF ("${CMAKE_SYSTEM_NAME}" STREQUAL "Windows")
	# ANSI constants only available in Windows 10 and up
	SET (CMAKE_SYSTEM_VERSION 10.0)

	IF ("${CMAKE_GENERATOR_PLATFORM}" STREQUAL "x64")
		set(CMAKE_SHARED_LINKER_FLAGS "/machine:x64")
	ELSEIF (NOT "${CMAKE_GENERATOR_PLATFORM}")
		# CMake interprets the lack of a build architecture ("-A <platform>") as x86. There doesn't
		# seem to be any other way to specify it.
		set(CMAKE_SHARED_LINKER_FLAGS "/machine:x86")
	ELSE ()
		MESSAGE (FATAL_ERROR "cmake -A <platform-name> must be one of [x86, x64], was ${CMAKE_GENERATOR_PLATFORM}")
	ENDIF ()
	FILE (TO_CMAKE_PATH "$ENV{JAVA_HOME}/include/win32" java_include_path)
ELSEIF ("${CMAKE_SYSTEM_NAME}" STREQUAL "Linux")
	IF ("${CMAKE_GENERATOR_PLATFORM}" STREQUAL "i386")
		SET (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -m32")
		SET (CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -m32")
		FILE (TO_CMAKE_PATH "$ENV{JAVA_HOME}/jre/lib/i386/client" java_library_path)
	ELSEIF ("${CMAKE_GENERATOR_PLATFORM}" STREQUAL "amd64")
		SET (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -m64")
		SET (CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -m64")
		FILE (TO_CMAKE_PATH "$ENV{JAVA_HOME}/jre/lib/amd64/server" java_library_path)
	ELSE ()
		MESSAGE (FATAL_ERROR "cmake -A <platform-name> must be one of [i386, amd64], was ${CMAKE_GENERATOR_PLATFORM}")
	ENDIF ()
	FILE (TO_CMAKE_PATH "$ENV{JAVA_HOME}/include/linux" java_include_path)
ELSEIF (${CMAKE_SYSTEM_NAME} STREQUAL "Darwin")
	FILE (TO_CMAKE_PATH "$ENV{JAVA_HOME}/include/darwin" java_include_path)
ELSE ()
	MESSAGE (FATAL_ERROR "CMAKE_SYSTEM_NAME must be one of [Windows, Linux, Darwin], was ${CMAKE_SYSTEM_NAME}")
ENDIF ()

INCLUDE_DIRECTORIES (include $ENV{JAVA_HOME}/include ${java_include_path} "${PROJECT_BINARY_DIR}/../include")

get_headers ("${CMAKE_CURRENT_SOURCE_DIR}/include" HEADER_FILES)
get_sources ("${CMAKE_CURRENT_SOURCE_DIR}/source" SOURCE_FILES)

ADD_LIBRARY (requirements SHARED ${SOURCE_FILES} ${HEADER_FILES})

TARGET_LINK_LIBRARIES (requirements ${CMAKE_DL_LIBS})