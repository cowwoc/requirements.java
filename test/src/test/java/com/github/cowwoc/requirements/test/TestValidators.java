package com.github.cowwoc.requirements.test;

import com.github.cowwoc.requirements.guava.GuavaAssumeThat;
import com.github.cowwoc.requirements.guava.GuavaCheckIf;
import com.github.cowwoc.requirements.guava.GuavaRequireThat;
import com.github.cowwoc.requirements.java.JavaAssumeThat;
import com.github.cowwoc.requirements.java.JavaCheckIf;
import com.github.cowwoc.requirements.java.JavaRequireThat;
import com.github.cowwoc.requirements.java.Validators;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.part.Validator;

/**
 * Validators for automated tests.
 * <p>
 * A validation method may throw exceptions in three scenarios:
 * <ol>
 * <li>The method arguments are invalid, e.g. {@code isGreaterThan(null, value)}.</li>
 * <li>The method encounters a predictable but unavoidable failure that can be recovered from, e.g. an I/O
 * error.</li>
 * <li>The value fails the validation check, e.g. {@code isGreaterThan(5)} on a value of 0.</li>
 * </ol>
 * {@code requireThat()} throws an exception in all scenarios. {@code checkIf()} only throws exceptions in
 * scenarios 1 and 2. For scenario 3, the exception is available via
 * {@link Validator#elseGetException() validator.elseGetException()}}.
 */
public interface TestValidators extends Validators, JavaRequireThat, JavaAssumeThat, JavaCheckIf,
	GuavaRequireThat, GuavaAssumeThat, GuavaCheckIf
{
	/**
	 * Creates a validator factory with a custom configuration.
	 *
	 * @param scope the application configuration
	 * @return an instance of this interface
	 * @throws NullPointerException if any of the arguments are null
	 */
	static TestValidators of(ApplicationScope scope)
	{
		return new TestValidatorsImpl(scope);
	}
}