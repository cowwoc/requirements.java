 /*
  * Copyright (c) 2019 Gili Tzabari
  * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
  */
 package com.github.cowwoc.requirements11.java.internal.validator;

 import com.github.cowwoc.requirements11.java.ValidationFailure;
 import com.github.cowwoc.requirements11.java.internal.Configuration;
 import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
 import com.github.cowwoc.requirements11.java.internal.util.ValidationTarget;
 import com.github.cowwoc.requirements11.java.validator.DoubleValidator;

 import java.util.List;
 import java.util.Map;
 import java.util.Optional;

 public final class DoubleValidatorImpl extends AbstractObjectValidator<DoubleValidator, Double>
	 implements DoubleValidator
 {
	 private final Doubles<DoubleValidator> doubles = new Doubles<>(this);

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
	 public DoubleValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		 ValidationTarget<Double> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	 {
		 super(scope, configuration, name, value, context, failures);
	 }

	 @Override
	 public DoubleValidator isNegative()
	 {
		 return doubles.isNegative();
	 }

	 @Override
	 public DoubleValidator isNotNegative()
	 {
		 return doubles.isNotNegative();
	 }

	 @Override
	 public DoubleValidator isZero()
	 {
		 return doubles.isZero();
	 }

	 @Override
	 public DoubleValidator isNotZero()
	 {
		 return doubles.isNotZero();
	 }

	 @Override
	 public DoubleValidator isPositive()
	 {
		 return doubles.isPositive();
	 }

	 @Override
	 public DoubleValidator isNotPositive()
	 {
		 return doubles.isNotPositive();
	 }

	 @Override
	 public DoubleValidator isLessThan(double maximumExclusive)
	 {
		 return doubles.isLessThan(maximumExclusive);
	 }

	 @Override
	 public DoubleValidator isLessThan(double maximumExclusive, String name)
	 {
		 return doubles.isLessThan(maximumExclusive, name);
	 }

	 @Override
	 public DoubleValidator isLessThanOrEqualTo(double maximumInclusive)
	 {
		 return doubles.isLessThanOrEqualTo(maximumInclusive);
	 }

	 @Override
	 public DoubleValidator isLessThanOrEqualTo(double maximumInclusive, String name)
	 {
		 return doubles.isLessThanOrEqualTo(maximumInclusive, name);
	 }

	 @Override
	 public DoubleValidator isGreaterThanOrEqualTo(double minimumInclusive)
	 {
		 return doubles.isGreaterThanOrEqualTo(minimumInclusive);
	 }

	 @Override
	 public DoubleValidator isGreaterThanOrEqualTo(double minimumInclusive, String name)
	 {
		 return doubles.isGreaterThanOrEqualTo(minimumInclusive, name);
	 }

	 @Override
	 public DoubleValidator isGreaterThan(double minimumExclusive)
	 {
		 return doubles.isGreaterThan(minimumExclusive);
	 }

	 @Override
	 public DoubleValidator isGreaterThan(double minimumExclusive, String name)
	 {
		 return doubles.isGreaterThan(minimumExclusive, name);
	 }

	 @Override
	 public DoubleValidator isBetween(double minimumInclusive, double maximumExclusive)
	 {
		 return doubles.isBetween(minimumInclusive, maximumExclusive);
	 }

	 @Override
	 public DoubleValidator isBetween(double minimum, boolean minimumIsInclusive, double maximum,
		 boolean maximumIsInclusive)
	 {
		 return doubles.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	 }

	 @Override
	 public DoubleValidator isMultipleOf(double factor)
	 {
		 return doubles.isMultipleOf(factor);
	 }

	 @Override
	 public DoubleValidator isMultipleOf(double factor, String name)
	 {
		 return doubles.isMultipleOf(factor, name);
	 }

	 @Override
	 public DoubleValidator isNotMultipleOf(double factor)
	 {
		 return doubles.isNotMultipleOf(factor);
	 }

	 @Override
	 public DoubleValidator isNotMultipleOf(double factor, String name)
	 {
		 return doubles.isNotMultipleOf(factor, name);
	 }

	 @Override
	 public DoubleValidator isLessThan(Double maximumExclusive)
	 {
		 return doubles.isLessThan(maximumExclusive);
	 }

	 @Override
	 public DoubleValidator isLessThan(Double maximumExclusive, String name)
	 {
		 return doubles.isLessThan(maximumExclusive, name);
	 }

	 @Override
	 public DoubleValidator isLessThanOrEqualTo(Double maximumInclusive)
	 {
		 return doubles.isLessThanOrEqualTo(maximumInclusive);
	 }

	 @Override
	 public DoubleValidator isLessThanOrEqualTo(Double maximumInclusive, String name)
	 {
		 return doubles.isLessThanOrEqualTo(maximumInclusive, name);
	 }

	 @Override
	 public DoubleValidator isGreaterThanOrEqualTo(Double minimumInclusive)
	 {
		 return doubles.isGreaterThanOrEqualTo(minimumInclusive);
	 }

	 @Override
	 public DoubleValidator isGreaterThanOrEqualTo(Double minimumInclusive, String name)
	 {
		 return doubles.isGreaterThanOrEqualTo(minimumInclusive, name);
	 }

	 @Override
	 public DoubleValidator isGreaterThan(Double minimumExclusive)
	 {
		 return doubles.isGreaterThan(minimumExclusive);
	 }

	 @Override
	 public DoubleValidator isGreaterThan(Double minimumExclusive, String name)
	 {
		 return doubles.isGreaterThan(minimumExclusive, name);
	 }

	 @Override
	 public DoubleValidator isBetween(Double minimumInclusive, Double maximumExclusive)
	 {
		 return doubles.isBetween(minimumInclusive, maximumExclusive);
	 }

	 @Override
	 public DoubleValidator isBetween(Double minimum, boolean minimumIsInclusive, Double maximum,
		 boolean maximumIsInclusive)
	 {
		 return doubles.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	 }

	 @Override
	 public DoubleValidator isNumber()
	 {
		 return doubles.isNumber();
	 }

	 @Override
	 public DoubleValidator isNotNumber()
	 {
		 return doubles.isNotNumber();
	 }

	 @Override
	 public DoubleValidator isFinite()
	 {
		 return doubles.isFinite();
	 }

	 @Override
	 public DoubleValidator isInfinite()
	 {
		 return doubles.isInfinite();
	 }

	 @Override
	 public DoubleValidator isWholeNumber()
	 {
		 return doubles.isWholeNumber();
	 }

	 @Override
	 public DoubleValidator isNotWholeNumber()
	 {
		 return doubles.isNotWholeNumber();
	 }
 }