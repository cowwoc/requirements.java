package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.internal.diff.ContextGenerator;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Strings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Builds an exception message.
 */
public final class MessageBuilder
{
	private static final Pattern CONTAINS_NON_ALPHANUMERIC = Pattern.compile("(?U)[^\\p{Alnum}]");
	private final ApplicationScope scope;
	private final AbstractValidator<?> validator;
	private final String message;
	private final Map<String, Object> failureContext = LinkedHashMap.newLinkedHashMap(2);
	private final List<Map<String, Object>> diff = new ArrayList<>();

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param message   (optional) the exception message
	 * @throws AssertionError if any of the mandatory arguments are null, or if {@code message} does not end
	 *                        with a dot
	 */
	public MessageBuilder(ApplicationScope scope, AbstractValidator<?> validator, String message)
	{
		assert (scope != null);
		assert (validator != null);
		assert (message == null || message.endsWith(".")) : "Message must end with a dot: " + message;
		this.scope = scope;
		this.validator = validator;
		this.message = message;
	}

	/**
	 * Appends context to the exception message. If the context previously contained a mapping for the name, the
	 * old value is replaced.
	 *
	 * @param value the value of the context
	 * @param name  (optional) the name of the context (empty string if absent)
	 * @return this
	 * @throws AssertionError if the name is null, empty or contains leading or trailing whitespace, or a colon
	 */
	public MessageBuilder putContext(Object value, String name)
	{
		failureContext.put(name, value);
		return this;
	}

	/**
	 * Adds a DIFF to the context that compares the value to an expected value
	 *
	 * @param nameOfValue       the name of the value
	 * @param value             the object representation of the value
	 * @param nameOfExpected    the name of the expected value
	 * @param expected          the object representation of the expected value
	 * @param expectedInMessage true if the expected value is already mentioned in the failure message
	 * @return this
	 */
	public MessageBuilder addDiff(String nameOfValue, Object value, String nameOfExpected, Object expected,
		boolean expectedInMessage)
	{
		ContextGenerator contextGenerator = new ContextGenerator(validator.configuration(), scope).
			expectedName(nameOfExpected).
			expectedValue(expected).
			actualName(nameOfValue).
			actualValue(value);
		if (expectedInMessage)
			contextGenerator.expectedInMessage();
		diff.addAll(contextGenerator.build());
		return this;
	}

	/**
	 * @return the contextual information associated with a validation failure
	 */
	private String getValidatorContext()
	{
		Map<String, Object> threadContext = scope.getThreadContext().get();
		Map<String, Object> validatorContext = validator.getContext();

		Set<String> existingKeys = new HashSet<>(failureContext.keySet());
		Map<String, Object> section = LinkedHashMap.newLinkedHashMap(failureContext.size() +
		                                                             threadContext.size() +
		                                                             validatorContext.size());
		section.putAll(failureContext);

		for (Entry<String, Object> entry : validatorContext.entrySet())
		{
			if (existingKeys.add(entry.getKey()))
				section.put(entry.getKey(), entry.getValue());
		}

		for (Entry<String, Object> entry : threadContext.entrySet())
			if (existingKeys.add(entry.getKey()))
				section.put(entry.getKey(), entry.getValue());

		StringJoiner joiner = new StringJoiner("\n");
		String sectionAsString = contextToString(List.of(section), true);
		if (!sectionAsString.isEmpty())
			joiner.add(sectionAsString);
		sectionAsString = contextToString(diff, false);
		if (!sectionAsString.isEmpty())
		{
			// Always insert a newline before the DIFF
			joiner.add("");
			joiner.add(sectionAsString);
		}
		return joiner.toString();
	}

	private String contextToString(List<Map<String, Object>> context, boolean convertToString)
	{
		int maxKeyLength = 0;
		for (Map<String, Object> section : context)
			for (Entry<String, Object> entry : section.entrySet())
			{
				String key = entry.getKey();
				maxKeyLength = Math.max(maxKeyLength, key.length());
			}

		StringMappers stringMappers = validator.configuration().stringMappers();
		StringJoiner contextJoiner = new StringJoiner("\n");
		for (Map<String, Object> section : context)
		{
			StringJoiner sectionJoiner = new StringJoiner("\n");
			for (Entry<String, Object> entry : section.entrySet())
			{
				String key = entry.getKey();
				String value;
				if (convertToString)
					value = stringMappers.toString(entry.getValue());
				else
					value = (String) entry.getValue();
				StringBuilder line = new StringBuilder();
				if (!key.isEmpty())
					line.append(Strings.alignLeft(key, maxKeyLength)).append(": ");
				line.append(value);
				sectionJoiner.add(line);
			}
			if (sectionJoiner.length() > 0)
			{
				contextJoiner.add("");
				contextJoiner.add(sectionJoiner.toString());
			}
		}
		return contextJoiner.toString();
	}

	/**
	 * Quotes the name of a parameter if it only contains alphanumeric characters (which makes it difficult to
	 * tell that it is a variable name).
	 *
	 * @param name the name of a parameter
	 * @return the updated name
	 */
	public static String quoteName(String name)
	{
		Matcher matcher = CONTAINS_NON_ALPHANUMERIC.matcher(name);
		if (matcher.find())
			return name;
		return "\"" + name + "\"";
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		String validatorContext = getValidatorContext();

		if (message != null)
		{
			// Strip the period from the end of single-line messages
			int lengthOfMessage = message.length();

			if (validatorContext.isEmpty() && diff.isEmpty() && !message.contains("\n"))
			{
				assert (!message.isEmpty());
				assert (message.endsWith(".")) : message;
				--lengthOfMessage;
			}
			result.append(message, 0, lengthOfMessage);
		}
		result.append(validatorContext);
		return result.toString();
	}
}