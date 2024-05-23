 /*
  * Copyright (c) 2019 Gili Tzabari
  * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
  */
 package com.github.cowwoc.requirements.java.internal.implementation;

 import com.github.cowwoc.requirements.java.Configuration;
 import com.github.cowwoc.requirements.java.ValidationFailure;
 import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
 import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
 import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
 import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
 import com.github.cowwoc.requirements.java.type.DoubleValidator;

 import java.util.List;
 import java.util.Map;

 public final class DoubleValidatorImpl extends AbstractObjectValidator<DoubleValidator, Double>
	 implements DoubleValidator
 {
	 /**
	  * @param scope         the application configuration
	  * @param configuration the validator configuration
	  * @param name          the name of the value
	  * @param value         (optional) the value
	  * @param context       the contextual information set by the user
	  * @param failures      the list of validation failures
	  * @throws NullPointerException     if {@code name} is null
	  * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	  * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	  *                                  leading or trailing whitespace, or is empty.
	  */
	 public DoubleValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		 Double value, Map<String, Object> context, List<ValidationFailure> failures)
	 {
		 super(scope, configuration, name, value, context, failures);
	 }

	 /**
	  * @param value a value
	  * @return true if {@code value} is a whole number
	  */
	 private static boolean isWholeNumber(double value)
	 {
		 // Based on https://stackoverflow.com/a/9909417/14731
		 return (value % 1) == 0;
	 }

	 /**
	  * @param number the number being divided
	  * @param factor the number dividing {@code number}
	  * @return true if {@code number} is a multiple of {@code factor}
	  */
	 private static boolean isMultipleOf(double number, double factor)
	 {
		 return factor != 0.0 && (number == 0.0 || (number % factor == 0));
	 }

	 @Override
	 public DoubleValidator isNegative()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNegative(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (Double.isNaN(value) || value >= 0.0)
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNegative(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isNotNegative()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotNegative(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!Double.isNaN(value) && value < 0.0)
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isZero()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isZero(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (Double.isNaN(value) || value != 0.0)
		 {
			 addIllegalArgumentException(
				 NumberMessages.isZero(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isNotZero()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotZero(scope, this, this.name).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!Double.isNaN(value) && value == 0.0)
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotZero(scope, this, this.name).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isPositive()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isPositive(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (Double.isNaN(value) || Double.compare(value, 0.0) < 0)
		 {
			 addIllegalArgumentException(
				 NumberMessages.isPositive(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isNotPositive()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotPositive(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!Double.isNaN(value) && value > 0.0)
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isNumber()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNumber(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (Double.isNaN(value))
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNumber(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isNotNumber()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotNumber(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!Double.isNaN(value))
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotNumber(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isFinite()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isFinite(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!Double.isFinite(value))
		 {
			 addIllegalArgumentException(
				 NumberMessages.isFinite(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isInfinite()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isInfinite(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!Double.isInfinite(value))
		 {
			 addIllegalArgumentException(
				 NumberMessages.isInfinite(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isWholeNumber()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isWholeNumber(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!isWholeNumber(value))
		 {
			 addIllegalArgumentException(
				 NumberMessages.isWholeNumber(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isNotWholeNumber()
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotWholeNumber(scope, this, this.name, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (isWholeNumber(value))
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotWholeNumber(scope, this, this.name, value).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isMultipleOf(double factor)
	 {
		 return isMultipleOfImpl(factor, null);
	 }

	 @Override
	 public DoubleValidator isMultipleOf(double factor, String name)
	 {
		 requireThatNameIsUnique(name);
		 return isMultipleOfImpl(factor, name);
	 }

	 private DoubleValidator isMultipleOfImpl(double factor, String name)
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isMultipleOf(scope, this, this.name, null, name, factor).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (!isMultipleOf(value, factor))
		 {
			 addIllegalArgumentException(
				 NumberMessages.isMultipleOf(scope, this, this.name, value, name, factor).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isNotMultipleOf(double factor)
	 {
		 return isNotMultipleOfImpl(factor, null);
	 }

	 @Override
	 public DoubleValidator isNotMultipleOf(double factor, String name)
	 {
		 requireThatNameIsUnique(name);
		 return isNotMultipleOfImpl(factor, name);
	 }

	 private DoubleValidator isNotMultipleOfImpl(double factor, String name)
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 NumberMessages.isNotMultipleOf(scope, this, this.name, null, name, factor).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (isMultipleOf(value, factor))
		 {
			 addIllegalArgumentException(NumberMessages.isNotMultipleOf(scope, this,
				 this.name, value, name, factor).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isLessThan(double maximumExclusive)
	 {
		 return isLessThanImpl(maximumExclusive, null);
	 }

	 @Override
	 public DoubleValidator isLessThan(double maximumExclusive, String name)
	 {
		 requireThatNameIsUnique(name);
		 return isLessThanImpl(maximumExclusive, name);
	 }

	 private DoubleValidator isLessThanImpl(double maximumExclusive, String name)
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isLessThan(scope, this, this.name, null, name, maximumExclusive).
					 toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (value.compareTo(maximumExclusive) >= 0)
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isLessThan(scope, this, this.name, value, name, maximumExclusive).
					 toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isLessThanOrEqualTo(double maximumInclusive)
	 {
		 return isLessThanOrEqualToImpl(maximumInclusive, null);
	 }

	 @Override
	 public DoubleValidator isLessThanOrEqualTo(double maximumInclusive, String name)
	 {
		 requireThatNameIsUnique(name);
		 return isLessThanOrEqualToImpl(maximumInclusive, name);
	 }

	 private DoubleValidator isLessThanOrEqualToImpl(double maximumInclusive, String name)
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, null, name,
					 maximumInclusive).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (value.compareTo(maximumInclusive) > 0)
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, value, name,
					 maximumInclusive).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isGreaterThanOrEqualTo(double minimumInclusive)
	 {
		 return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	 }

	 @Override
	 public DoubleValidator isGreaterThanOrEqualTo(double minimumInclusive, String name)
	 {
		 requireThatNameIsUnique(name);
		 return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	 }

	 private DoubleValidator isGreaterThanOrEqualToImpl(double minimumInclusive, String name)
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, null, name,
					 minimumInclusive).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (value.compareTo(minimumInclusive) < 0)
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, value, name,
					 minimumInclusive).toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isGreaterThan(double minimumExclusive)
	 {
		 return isGreaterThanImpl(minimumExclusive, null);
	 }

	 @Override
	 public DoubleValidator isGreaterThan(double minimumExclusive, String name)
	 {
		 requireThatNameIsUnique(name);
		 return isGreaterThanImpl(minimumExclusive, name);
	 }

	 private DoubleValidator isGreaterThanImpl(double minimumExclusive, String name)
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isGreaterThan(scope, this, this.name, null, name, minimumExclusive).
					 toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (value.compareTo(minimumExclusive) <= 0)
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isGreaterThan(scope, this, this.name, value, name, minimumExclusive).
					 toString());
		 }
		 return this;
	 }

	 @Override
	 public DoubleValidator isBetween(double minimumInclusive, double maximumExclusive)
	 {
		 return isBetween(minimumInclusive, true, maximumExclusive, false);
	 }

	 @Override
	 public DoubleValidator isBetween(double minimum, boolean minimumInclusive, double maximum,
		 boolean maximumInclusive)
	 {
		 if (hasFailed())
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isBetween(scope, this, this.name, null, minimum, minimumInclusive,
					 maximum, maximumInclusive, null).toString());
		 }
		 else if (value == null)
		 {
			 addIllegalArgumentException(
				 ObjectMessages.isNotNull(scope, this, this.name).toString());
		 }
		 else if (minimumFailed(minimum, minimumInclusive) || maximumFailed(maximum, maximumInclusive))
		 {
			 addIllegalArgumentException(
				 ComparableMessages.isBetween(scope, this, this.name, value, minimum, minimumInclusive,
					 maximum, maximumInclusive, null).toString());
		 }
		 return this;
	 }

	 private boolean minimumFailed(double minimum, boolean minimumInclusive)
	 {
		 if (minimumInclusive)
			 return value.compareTo(minimum) < 0;
		 return value.compareTo(minimum) <= 0;
	 }

	 private boolean maximumFailed(double maximum, boolean maximumInclusive)
	 {
		 if (maximumInclusive)
			 return value.compareTo(maximum) > 0;
		 return value.compareTo(maximum) >= 0;
	 }
 }