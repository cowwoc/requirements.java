 /*
  * Copyright (c) 2019 Gili Tzabari
  * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
  */
 package com.github.cowwoc.requirements11.java.internal.validator;

 import com.github.cowwoc.requirements11.java.ValidationFailure;
 import com.github.cowwoc.requirements11.java.internal.Configuration;
 import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
 import com.github.cowwoc.requirements11.java.internal.util.ValidationTarget;
 import com.github.cowwoc.requirements11.java.validator.ByteValidator;

 import java.util.List;
 import java.util.Map;
 import java.util.Optional;

 public final class ByteValidatorImpl extends AbstractObjectValidator<ByteValidator, Byte>
	 implements ByteValidator
 {
	 private final Bytes<ByteValidator> bytes = new Bytes<>(this);

	 /**
	  * @param scope         the application configuration
	  * @param configuration the validator configuration
	  * @param name          the name of the value
	  * @param value         the value
	  * @param context       the contextual information set by a parent validator or the user
	  * @param failures      the list of validation failures
	  * @throws NullPointerException     if {@code name} is null
	  * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	  * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	  *                                  or {@code failures} are null
	  */
	 public ByteValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		 ValidationTarget<Byte> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	 {
		 super(scope, configuration, name, value, context, failures);
	 }

	 @Override
	 public ByteValidator isNegative()
	 {
		 return bytes.isNegative();
	 }

	 @Override
	 public ByteValidator isNotNegative()
	 {
		 return bytes.isNotNegative();
	 }

	 @Override
	 public ByteValidator isZero()
	 {
		 return bytes.isZero();
	 }

	 @Override
	 public ByteValidator isNotZero()
	 {
		 return bytes.isNotZero();
	 }

	 @Override
	 public ByteValidator isPositive()
	 {
		 return bytes.isPositive();
	 }

	 @Override
	 public ByteValidator isNotPositive()
	 {
		 return bytes.isNotPositive();
	 }

	 @Override
	 public ByteValidator isLessThan(byte maximumExclusive)
	 {
		 return bytes.isLessThan(maximumExclusive);
	 }

	 @Override
	 public ByteValidator isLessThan(byte maximumExclusive, String name)
	 {
		 return bytes.isLessThan(maximumExclusive, name);
	 }

	 @Override
	 public ByteValidator isLessThanOrEqualTo(byte maximumInclusive)
	 {
		 return bytes.isLessThanOrEqualTo(maximumInclusive);
	 }

	 @Override
	 public ByteValidator isLessThanOrEqualTo(byte maximumInclusive, String name)
	 {
		 return bytes.isLessThanOrEqualTo(maximumInclusive, name);
	 }

	 @Override
	 public ByteValidator isGreaterThanOrEqualTo(byte minimumInclusive)
	 {
		 return bytes.isGreaterThanOrEqualTo(minimumInclusive);
	 }

	 @Override
	 public ByteValidator isGreaterThanOrEqualTo(byte minimumInclusive, String name)
	 {
		 return bytes.isGreaterThanOrEqualTo(minimumInclusive, name);
	 }

	 @Override
	 public ByteValidator isGreaterThan(byte minimumExclusive)
	 {
		 return bytes.isGreaterThan(minimumExclusive);
	 }

	 @Override
	 public ByteValidator isGreaterThan(byte minimumExclusive, String name)
	 {
		 return bytes.isGreaterThan(minimumExclusive, name);
	 }

	 @Override
	 public ByteValidator isBetween(byte minimumInclusive, byte maximumExclusive)
	 {
		 return bytes.isBetween(minimumInclusive, maximumExclusive);
	 }

	 @Override
	 public ByteValidator isBetween(byte minimum, boolean minimumIsInclusive, byte maximum,
		 boolean maximumIsInclusive)
	 {
		 return bytes.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	 }

	 @Override
	 public ByteValidator isMultipleOf(byte factor)
	 {
		 return bytes.isMultipleOf(factor);
	 }

	 @Override
	 public ByteValidator isMultipleOf(byte factor, String name)
	 {
		 return bytes.isMultipleOf(factor, name);
	 }

	 @Override
	 public ByteValidator isNotMultipleOf(byte factor)
	 {
		 return bytes.isNotMultipleOf(factor);
	 }

	 @Override
	 public ByteValidator isNotMultipleOf(byte factor, String name)
	 {
		 return bytes.isNotMultipleOf(factor, name);
	 }

	 @Override
	 public ByteValidator isLessThan(Byte maximumExclusive)
	 {
		 return bytes.isLessThan(maximumExclusive);
	 }

	 @Override
	 public ByteValidator isLessThan(Byte maximumExclusive, String name)
	 {
		 return bytes.isLessThan(maximumExclusive, name);
	 }

	 @Override
	 public ByteValidator isLessThanOrEqualTo(Byte maximumInclusive)
	 {
		 return bytes.isLessThanOrEqualTo(maximumInclusive);
	 }

	 @Override
	 public ByteValidator isLessThanOrEqualTo(Byte maximumInclusive, String name)
	 {
		 return bytes.isLessThanOrEqualTo(maximumInclusive, name);
	 }

	 @Override
	 public ByteValidator isGreaterThanOrEqualTo(Byte minimumInclusive)
	 {
		 return bytes.isGreaterThanOrEqualTo(minimumInclusive);
	 }

	 @Override
	 public ByteValidator isGreaterThanOrEqualTo(Byte minimumInclusive, String name)
	 {
		 return bytes.isGreaterThanOrEqualTo(minimumInclusive, name);
	 }

	 @Override
	 public ByteValidator isGreaterThan(Byte minimumExclusive)
	 {
		 return bytes.isGreaterThan(minimumExclusive);
	 }

	 @Override
	 public ByteValidator isGreaterThan(Byte minimumExclusive, String name)
	 {
		 return bytes.isGreaterThan(minimumExclusive, name);
	 }

	 @Override
	 public ByteValidator isBetween(Byte minimumInclusive, Byte maximumExclusive)
	 {
		 return bytes.isBetween(minimumInclusive, maximumExclusive);
	 }

	 @Override
	 public ByteValidator isBetween(Byte minimum, boolean minimumIsInclusive, Byte maximum,
		 boolean maximumIsInclusive)
	 {
		 return bytes.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	 }
 }