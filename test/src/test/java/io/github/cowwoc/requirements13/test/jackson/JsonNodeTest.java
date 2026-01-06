/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test.jackson;

import io.github.cowwoc.requirements13.jackson.validator.JsonNodeValidator;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.test.TestValidators;
import io.github.cowwoc.requirements13.test.scope.TestApplicationScope;
import org.testng.annotations.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.exc.JsonNodeException;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;

public final class JsonNodeTest
{
	JsonMapper om = JsonMapper.builder().build();

	@Test
	public void nodeIsByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(Byte.MAX_VALUE);
			int value = validators.requireThat(node, "node").isNumber().isIntegralNumber().getValue().intValue();
			validators.requireThat(value, "value").isEqualTo(Byte.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(Short.MAX_VALUE);
			short value = validators.requireThat(node, "node").isNumber().isIntegralNumber().getValue().
				shortValue();
			validators.requireThat(value, "value").isEqualTo(Short.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(Integer.MAX_VALUE);
			int value = validators.requireThat(node, "node").isNumber().isIntegralNumber().getValue().intValue();
			validators.requireThat(value, "value").isEqualTo(Integer.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(Long.MAX_VALUE);
			long value = validators.requireThat(node, "node").isNumber().isIntegralNumber().getValue().longValue();
			validators.requireThat(value, "value").isEqualTo(Long.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(5.5f);
			float value = validators.requireThat(node, "node").isNumber().isFloatingPointNumber().getValue().
				floatValue();
			validators.requireThat(value, "value").isEqualTo(5.5f);
		}
	}

	@Test
	public void nodeIsDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(5.5);
			double value = validators.requireThat(node, "node").isNumber().isFloatingPointNumber().getValue().
				floatValue();
			validators.requireThat(value, "value").isEqualTo(5.5);
		}
	}

	@Test
	public void nodeIsBigInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			BigInteger input = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
			JsonNode node = om.createObjectNode().numberNode(input);
			BigInteger value = validators.requireThat(node, "node").isNumber().isBigInteger().getValue().
				bigIntegerValue();
			validators.requireThat(value, "value").isEqualTo(input);
		}
	}

	@Test
	public void nodeIsBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			BigDecimal input = BigDecimal.valueOf(Double.MAX_VALUE).add(BigDecimal.ONE);
			JsonNode node = om.createObjectNode().numberNode(input);
			BigDecimal value = validators.requireThat(node, "node").isNumber().isBigDecimal().getValue().
				decimalValue();
			validators.requireThat(value, "value").isEqualTo(input);
		}
	}

	@Test
	public void nodeIsBinary()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().put("value", "dGVzdA==").get("value");
			byte[] value = validators.requireThat(node, "node").isValue().getValue().binaryValue();
			String output = new String(value, StandardCharsets.UTF_8);
			validators.requireThat(output, "output").isEqualTo("test");
		}
	}

	@Test(expectedExceptions = JsonNodeException.class)
	public void nodeIsBinary_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().put("value", "123456789").get("value");
			validators.requireThat(node, "node").isValue().getValue().binaryValue();
		}
	}

	@Test
	public void nodeIsBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().put("value", true).get("value");
			boolean value = validators.requireThat(node, "node").isBoolean().getValue().booleanValue();
			validators.requireThat(value, "value").isTrue();
		}
	}

	@Test
	public void nodeIsValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().
				put("base64", "SGVsbG8gV29ybGQ=").
				put("boolean", true).
				putNull("null").
				put("number", 5).
				put("string", "Hello World");
			JsonNodeValidator<JsonNode> validator = validators.requireThat(node, "node");

			byte[] binaryValue = validator.property("base64").isValue().getValue().binaryValue();
			validators.requireThat(binaryValue, "binaryValue").
				isEqualTo("Hello World".getBytes(StandardCharsets.UTF_8));

			boolean booleanValue = validator.property("boolean").isValue().getValue().booleanValue();
			validators.requireThat(booleanValue, "booleanValue").isTrue();

			boolean isNull = validator.property("null").isValue().getValue().isNull();
			validators.requireThat(isNull, "nullValue").isTrue();

			Number numberValue = validator.property("number").isValue().getValue().numberValue();
			validators.requireThat(numberValue, "numberValue").isEqualTo(5);

			String stringValue = validator.property("string").isValue().getValue().stringValue();
			validators.requireThat(stringValue, "stringValue").isEqualTo("Hello World");
		}
	}

	@Test
	public void nodeIsMissing()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().missingNode();
			validators.requireThat(node, "node").isMissing();
		}
	}

	@Test
	public void nodeIsString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);
			JsonNode node = om.createObjectNode().stringNode("test");
			String value = validators.requireThat(node, "node").isValue().getValue().stringValue();
			validators.requireThat(value, "value").isEqualTo("test");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nodeIsString_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(5);
			validators.requireThat(node, "node").isString();
		}
	}

	@Test
	public void nodeIsArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			ArrayNode node = om.createArrayNode();
			node.add(1).add(2).add(3);
			ArrayNode value = validators.requireThat(node, "node").isArray().getValue();
			List<Integer> list = new ArrayList<>();
			for (JsonNode element : value)
				list.add(element.intValue());
			validators.requireThat(list, "list").containsExactly(List.of(1, 2, 3));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nodeIsArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(5);
			validators.requireThat(node, "node").isArray();
		}
	}

	@Test
	public void nodeIsContainer()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			ObjectNode node = om.createObjectNode();
			node.put("name", "John Smith");
			node.put("age", 52);
			validators.requireThat(node, "node").isContainer();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nodeIsContainer_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			JsonNode node = om.createObjectNode().numberNode(5);
			validators.requireThat(node, "node").isContainer();
		}
	}
}