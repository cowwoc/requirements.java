package com.github.cowwoc.requirements.java.internal.diff;

import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.PatchFailedException;

import java.util.List;

/**
 * Describes the add-delta between original and revised texts.
 *
 * @param <T> The type of the compared elements in the 'lines'.
 * @see <a href="https://github.com/java-diff-utils/java-diff-utils/issues/42">Github issue</a>
 */
public final class EqualDelta<T> extends AbstractDelta<T>
{
	/**
	 * Creates an insert delta with the two given chunks.
	 *
	 * @param original The original chunk. Must not be {@code null}.
	 * @param revised  The original chunk. Must not be {@code null}.
	 */
	public EqualDelta(Chunk<T> original, Chunk<T> revised)
	{
		super(DeltaType.EQUAL, original, revised);
		assert (original.getLines().equals(revised.getLines())) :
			"original: " + original + ", revised: " + revised;
	}

	@Override
	public void applyTo(List<T> target) throws PatchFailedException
	{
		verifyChunk(target);
	}

	@Override
	public void restore(List<T> target)
	{
	}

	@Override
	public String toString()
	{
		return "[EqualDelta, source.position: " + getSource().getPosition() + ", target.position: " +
			getTarget().getPosition() + ", lines: " + getSource().getLines() + "]";
	}
}
