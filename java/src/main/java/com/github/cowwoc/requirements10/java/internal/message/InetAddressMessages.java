package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

import java.net.InetAddress;

/**
 * Generates failure messages for InetAddress.
 */
public final class InetAddressMessages
{
	private InetAddressMessages()
	{
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param type      the type of IP address (e.g. "IP v4" or "IP v6")
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isIpAddress(ApplicationScope scope,
		AbstractValidator<?, InetAddress> validator, String type)
	{
		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(actualName) + " must be an " + type + " address.");
		validator.ifDefined(value -> messageBuilder.withContext(value, actualName));
		return messageBuilder;
	}
}