package com.github.cowwoc.requirements10.test.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.github.cowwoc.requirements10.java.DefaultJavaValidators.requireThat;
import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

public final class JsonNodeTest
{
	ObjectMapper om = JsonMapper.builder().build();

	@Test
	public void nodeIsByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(Byte.MAX_VALUE);
			int value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().isIntegralNumber().
				getValue().intValue();
			requireThat(value, "value").isEqualTo(Byte.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(Short.MAX_VALUE);
			short value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().isIntegralNumber().
				getValue().shortValue();
			requireThat(value, "value").isEqualTo(Short.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(Integer.MAX_VALUE);
			int value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().isIntegralNumber().
				getValue().intValue();
			requireThat(value, "value").isEqualTo(Integer.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(Long.MAX_VALUE);
			long value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().isIntegralNumber().
				getValue().longValue();
			requireThat(value, "value").isEqualTo(Long.MAX_VALUE);
		}
	}

	@Test
	public void nodeIsFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(5.5f);
			float value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().
				isFloatingPointNumber().getValue().floatValue();
			requireThat(value, "value").isEqualTo(5.5f);
		}
	}

	@Test
	public void nodeIsDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(5.5);
			double value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().
				isFloatingPointNumber().getValue().floatValue();
			requireThat(value, "value").isEqualTo(5.5);
		}
	}

	@Test
	public void nodeIsBigInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigInteger input = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
			JsonNode node = om.createObjectNode().numberNode(input);
			BigInteger value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().isBigInteger().
				getValue().bigIntegerValue();
			requireThat(value, "value").isEqualTo(input);
		}
	}

	@Test
	public void nodeIsBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal input = BigDecimal.valueOf(Double.MAX_VALUE).add(BigDecimal.ONE);
			JsonNode node = om.createObjectNode().numberNode(input);
			BigDecimal value = new TestValidatorsImpl(scope).requireThat(node, "node").isNumber().isBigDecimal().
				getValue().decimalValue();
			requireThat(value, "value").isEqualTo(input);
		}
	}

	@Test
	public void nodeIsBinary() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().put("value", "dGVzdA==").get("value");
			byte[] value = new TestValidatorsImpl(scope).requireThat(node, "node").isValue().getValue().
				binaryValue();
			String output = new String(value, StandardCharsets.UTF_8);
			requireThat(output, "output").isEqualTo("test");
		}
	}

	@Test(expectedExceptions = IOException.class)
	public void nodeIsBinary_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().put("value", "123456789").get("value");
			new TestValidatorsImpl(scope).requireThat(node, "node").isValue().getValue().binaryValue();
		}
	}

	@Test
	public void nodeIsBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().put("value", true).get("value");
			boolean value = new TestValidatorsImpl(scope).requireThat(node, "node").isValue().getValue().
				booleanValue();
			requireThat(value, "value").isTrue();
		}
	}

	@Test
	public void nodeIsMissing()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().missingNode();
			new TestValidatorsImpl(scope).requireThat(node, "node").isMissing();
		}
	}

	@Test
	public void nodeIsString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().textNode("test");
			String value = new TestValidatorsImpl(scope).requireThat(node, "node").isValue().getValue().
				textValue();
			requireThat(value, "value").isEqualTo("test");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nodeIsString_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(5);
			new TestValidatorsImpl(scope).requireThat(node, "node").isString();
		}
	}

	@Test
	public void nodeIsArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			ArrayNode node = om.createArrayNode();
			node.add(1).add(2).add(3);
			ArrayNode value = new TestValidatorsImpl(scope).requireThat(node, "node").isArray().getValue();
			List<Integer> list = new ArrayList<>();
			for (JsonNode element : value)
				list.add(element.intValue());
			requireThat(list, "list").containsExactly(List.of(1, 2, 3));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nodeIsArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(5);
			new TestValidatorsImpl(scope).requireThat(node, "node").isArray();
		}
	}

	@Test
	public void nodeIsContainer()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			ObjectNode node = om.createObjectNode();
			node.put("name", "John Smith");
			node.put("age", 52);
			new TestValidatorsImpl(scope).requireThat(node, "node").isContainer();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nodeIsContainer_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			JsonNode node = om.createObjectNode().numberNode(5);
			new TestValidatorsImpl(scope).requireThat(node, "node").isContainer();
		}
	}
}