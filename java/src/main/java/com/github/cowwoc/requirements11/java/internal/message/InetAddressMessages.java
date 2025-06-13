package com.github.cowwoc.requirements11.java.internal.message;

import com.github.cowwoc.requirements11.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements11.java.internal.validator.AbstractValidator;

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
	 * @param validator the validator
	 * @param type      the type of IP address (e.g. "IP v4" or "IP v6")
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isIpAddressFailed(AbstractValidator<?, InetAddress> validator, String type)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			MessageBuilder.quoteName(name) + " must be an " + type + " address.");
		InetAddress value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}
}