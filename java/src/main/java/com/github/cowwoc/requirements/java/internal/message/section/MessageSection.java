package com.github.cowwoc.requirements.java.internal.message.section;

/**
 * A section of text that contains contextual information related to a validation failure.
 */
public sealed interface MessageSection permits ContextSection, StringSection
{
}