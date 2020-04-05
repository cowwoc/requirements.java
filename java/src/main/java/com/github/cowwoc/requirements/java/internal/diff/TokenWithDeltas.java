package com.github.cowwoc.requirements.java.internal.diff;

import com.github.difflib.patch.AbstractDelta;

import java.util.List;

/**
 * A token with the list of associated deltas.
 *
 * @param <T> the type of the token
 * @param <S> the type of the subset of the token
 */
public final class TokenWithDeltas<T, S>
{
	public final T token;
	public final List<AbstractDelta<S>> deltas;

	/**
	 * @param token  a token
	 * @param deltas the deltas associated with the token
	 * @throws AssertionError if {@code deltas} is null
	 */
	public TokenWithDeltas(T token, List<AbstractDelta<S>> deltas)
	{
		assert (deltas != null) : "deltas may not be null";
		this.token = token;
		this.deltas = List.copyOf(deltas);
	}

	@Override
	public String toString()
	{
		return "TokenWithDeltas (token: " + token + ", deltas: " + deltas + ")";
	}
}
