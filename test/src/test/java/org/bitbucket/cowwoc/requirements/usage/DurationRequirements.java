package org.bitbucket.cowwoc.requirements.usage;

import java.time.Duration;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.ComparableVerifierSpi;

public interface DurationRequirements
	extends ComparableVerifierSpi<DurationRequirements, Duration>,
	Isolatable<DurationRequirements>
{
	/**
	 * Ensures that the actual value is positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not positive
	 */
	DurationRequirements isPositive();
}
