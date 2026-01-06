/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.util;

/**
 * An object and its size: If the object represents a collection, the size refers to the number of elements it
 * contains. If the object represents a string, the size corresponds to its length.
 *
 * @param object the object
 * @param size   the object's size
 */
public record ObjectAndSize(Object object, int size)
{
}