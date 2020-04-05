package com.github.cowwoc.requirements.java.internal.diff;

import com.github.difflib.patch.AbstractDelta;

import java.util.List;

/**
 * A word with the list of associated deltas.
 */
public final class WordWithDeltas
{
	public final String word;
	public final List<AbstractDelta<Integer>> deltas;

	/**
	 * @param word   a word
	 * @param deltas the deltas associated with the word
	 * @throws AssertionError if {@code deltas} is null
	 */
	public WordWithDeltas(String word, List<AbstractDelta<Integer>> deltas)
	{
		assert (deltas != null) : "deltas may not be null";
		this.word = word;
		this.deltas = List.copyOf(deltas);
	}

	@Override
	public String toString()
	{
		return "WordWithDeltas (word: " + word + ", deltas: " + deltas + ")";
	}
}
