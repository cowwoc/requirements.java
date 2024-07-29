package com.github.cowwoc.requirements10.test;

import com.github.cowwoc.requirements10.guava.GuavaAssumeThat;
import com.github.cowwoc.requirements10.guava.GuavaCheckIf;
import com.github.cowwoc.requirements10.guava.GuavaRequireThat;
import com.github.cowwoc.requirements10.jackson.JacksonAssumeThat;
import com.github.cowwoc.requirements10.jackson.JacksonCheckIf;
import com.github.cowwoc.requirements10.jackson.JacksonRequireThat;
import com.github.cowwoc.requirements10.java.JavaAssumeThat;
import com.github.cowwoc.requirements10.java.JavaCheckIf;
import com.github.cowwoc.requirements10.java.JavaRequireThat;
import com.github.cowwoc.requirements10.java.Validators;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

/**
 * Validators for automated tests.
 * <p>
 * A validation method may throw exceptions in three scenarios:
 * <ol>
 * <li>The method arguments are invalid, e.g. {@code isGreaterThan(value, null)}.</li>
 * <li>The method encounters a predictable but unavoidable failure that can be recovered from, e.g. an I/O
 * error.</li>
 * <li>The value fails the validation check, e.g. {@code isGreaterThan(5)} on a value of 0.</li>
 * </ol>
 * {@code requireThat()} throws an exception in all scenarios. {@code checkIf()} only throws exceptions in
 * scenarios 1 and 2. For scenario 3, the exception is available via
 * {@link ValidatorComponent#elseGetException() validator.elseGetException()}}.
 */
public interface TestValidators
	extends Validators<TestValidators>,
	JavaRequireThat, JavaAssumeThat, JavaCheckIf,
	GuavaRequireThat, GuavaAssumeThat, GuavaCheckIf,
	JacksonRequireThat, JacksonAssumeThat, JacksonCheckIf
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