package com.github.cowwoc.requirements10.java.internal.message.section;

import com.github.cowwoc.requirements10.java.StringMappers;
import com.github.cowwoc.requirements10.java.internal.message.diff.ContextGenerator;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SequencedMap;
import java.util.StringJoiner;

/**
 * Builds an exception message.
 */
public final class MessageBuilder
{
	public static final String DIFF_LEGEND = """

		Legend
		------
		+           : Add this character to the value
		-           : Remove this character from the value
		[index]     : Refers to the index of a collection element
		@line-number: Refers to the line number of a multiline string
		""";
	private final AbstractValidator<?, ?> validator;
	private final String message;
	private final Map<String, Object> failureContext = LinkedHashMap.newLinkedHashMap(2);
	/**
	 * A string that describes the difference between the expected and actual values.
	 */
	private final List<MessageSection> diff = new ArrayList<>();

	/**
	 * @param validator the validator
	 * @param message   (optional) the exception message (empty string when absent)
	 * @throws AssertionError if:
	 *                        <ul>
	 *                          <li>any of the arguments are null</li>
	 *                          <li>{@code message} is blank or does not end with a dot</li>
	 *                        </ul>
	 */
	public MessageBuilder(AbstractValidator<?, ?> validator, String message)
	{
		assert (validator != null);
		assert (message != null);
		assert (message.isEmpty() || message.endsWith(".")) : "Message must end with a dot: " + message;
		assert (!message.isBlank()) : "message may not be blank";
		this.validator = validator;
		this.message = message;
	}

	/**
	 * Appends context to the exception message. If the context previously contained a mapping for the name, the
	 * old value is replaced.
	 *
	 * @param context the exception context
	 * @return this
	 * @throws AssertionError if:
	 *                        <ul>
	 *                          <li>{@code context} is null</li>
	 *                          <li>one of {@code context}'s keys are empty</li>
	 *                          <li>one of {@code context}'s keys contains whitespace or a colon</li>
	 *                        </ul>
	 */
	public MessageBuilder withContext(Map<String, Object> context)
	{
		for (String name : context.keySet())
		{
			assert (name != null) : context;
			assert (!name.isEmpty()) : context;
		}
		failureContext.putAll(context);
		return this;
	}

	/**
	 * Appends context to the exception message. If the context previously contained a mapping for the name, the
	 * old value is replaced.
	 *
	 * @param value the value of the context
	 * @param name  (optional) the name of the context (empty string if absent)
	 * @return this
	 * @throws AssertionError if {@code name}:
	 *                        <ul>
	 *                          <li>is null</li>
	 *                          <li>is empty</li>
	 *                          <li>contains whitespace or a colon</li>
	 *                        </ul>
	 */
	public MessageBuilder withContext(Object value, String name)
	{
		assert (name != null);
		assert (!name.isEmpty());
		failureContext.put(name, value);
		return this;
	}

	/**
	 * Adds a DIFF to the context that compares the value to an expected value
	 *
	 * @param actualName    the name of the value
	 * @param actualValue   the object representation of the value
	 * @param expectedName  the name of the expected value
	 * @param expectedValue the object representation of the expected value
	 * @return this
	 */
	public MessageBuilder addDiff(String actualName, Object actualValue, String expectedName,
		Object expectedValue)
	{
		ContextGenerator contextGenerator = new ContextGenerator(validator.getScope(), validator.configuration(),
			actualName, expectedName).
			actualValue(actualValue).
			expectedValue(expectedValue);
		diff.addAll(contextGenerator.build());
		diff.add(new StringSection(DIFF_LEGEND));
		return this;
	}

	/**
	 * @return the contextual information associated with a validation failure
	 */
	private ContextSection getValidatorContext()
	{
		Map<String, Object> mergedContext = new LinkedHashMap<>(failureContext);
		for (Entry<String, Object> entry : validator.getContext().entrySet())
			mergedContext.putIfAbsent(entry.getKey(), entry.getValue());

		StringMappers stringMappers = validator.configuration().stringMappers();
		SequencedMap<String, String> contextAsString = new LinkedHashMap<>();
		for (Entry<String, Object> entry : mergedContext.entrySet())
			contextAsString.put(entry.getKey(), stringMappers.toString(entry.getValue()));
		return new ContextSection(contextAsString);
	}

	/**
	 * Quotes the name of a parameter, unless it references a method call.
	 *
	 * @param name the name of a parameter
	 * @return the updated name
	 */
	public static String quoteName(String name)
	{
		if (name.contains("."))
			return name;
		return "\"" + name + "\"";
	}

	@Override
	public String toString()
	{
		List<MessageSection> context = new ArrayList<>();
		addValidatorContextToContext(context);
		addDiffToContext(context);
		addExceptionMessageToContext(context);
		return toString(context);
	}

	private void addDiffToContext(List<MessageSection> context)
	{
		if (diff.isEmpty())
			return;
		if (!context.isEmpty() || !message.isEmpty())
		{
			// Add an extra newline in front of the diff
			context.add(new StringSection(""));
		}
		context.addAll(diff);
	}

	private void addValidatorContextToContext(List<MessageSection> context)
	{
		ContextSection validatorContext = getValidatorContext();
		if (!validatorContext.value().isEmpty())
			context.add(validatorContext);
	}

	private String toString(List<MessageSection> context)
	{
		int maxKeyLength = getMaxKeyLength(context);
		StringJoiner result = new StringJoiner("\n");
		for (MessageSection section : context)
		{
			switch (section)
			{
				case ContextSection contextSection ->
				{
					// Align the colons vertically
					for (Entry<String, String> entry : contextSection.value().entrySet())
						result.add(alignLeft(entry.getKey(), maxKeyLength) + ": " + entry.getValue());
				}
				case StringSection stringSection -> result.add(stringSection.value());
			}
		}
		return result.toString();
	}

	/**
	 * @param text      the {@code String} to align
	 * @param minLength the minimum length of {@code text}
	 * @return {@code text} padded on the right with spaces until its length is greater than or equal to
	 * {@code minLength}
	 */
	private static String alignLeft(String text, int minLength)
	{
		int actualLength = text.length();
		if (actualLength > minLength)
			return text;
		return text + " ".repeat(minLength - actualLength);
	}

	private static int getMaxKeyLength(List<MessageSection> context)
	{
		int maxKeyLength = 0;
		for (MessageSection section : context)
		{
			if (!(section instanceof ContextSection contextSection))
				continue;
			for (String key : contextSection.value().keySet())
				maxKeyLength = Math.max(maxKeyLength, key.length());
		}
		return maxKeyLength;
	}

	private void addExceptionMessageToContext(List<MessageSection> context)
	{
		if (message == null)
			return;
		String updatedMessage;
		if (context.isEmpty() && !message.contains("\n"))
		{
			assert (!message.isEmpty());
			assert (message.endsWith(".")) : message;

			// Strip the period from the end of single-line messages, unless it contains a comma.
			if (message.contains(","))
				updatedMessage = message;
			else
				updatedMessage = message.substring(0, message.length() - 1);
		}
		else
			updatedMessage = message;
		context.addFirst(new StringSection(updatedMessage));
	}
}