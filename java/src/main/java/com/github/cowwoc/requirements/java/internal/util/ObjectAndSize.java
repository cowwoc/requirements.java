package com.github.cowwoc.requirements.java.internal.util;

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