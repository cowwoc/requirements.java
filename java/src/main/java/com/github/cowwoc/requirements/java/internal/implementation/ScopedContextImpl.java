package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.ScopedContext;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ScopedContextImpl implements ScopedContext
{
	private final ApplicationScope scope;
	private final Set<String> updatedEntries = new HashSet<>();
	private final Map<String, Object> nameToOldValue = new HashMap<>();
	private final Map<String, Object> threadContext;

	public ScopedContextImpl(ApplicationScope scope)
	{
		assert scope != null;
		this.scope = scope;
		this.threadContext = scope.getThreadContext().get();
	}

	@Override
	public Object get(String name)
	{
		return threadContext.get(name);
	}

	@Override
	public boolean containsName(String name)
	{
		return threadContext.containsKey(name);
	}

	@Override
	public ScopedContext put(Object value, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (threadContext.containsKey(name))
		{
			Object oldValue = threadContext.put(name, value);
			if (updatedEntries.add(name))
				nameToOldValue.put(name, oldValue);
		}
		else
			updatedEntries.add(name);
		threadContext.put(name, value);
		return this;
	}

	@Override
	public ScopedContext remove(String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (threadContext.containsKey(name))
		{
			Object oldValue = threadContext.remove(name);
			if (updatedEntries.add(name))
				nameToOldValue.put(name, oldValue);
		}
		return this;
	}

	public void close()
	{
		for (String name : updatedEntries)
		{
			if (nameToOldValue.containsKey(name))
			{
				Object oldValue = nameToOldValue.get(name);
				threadContext.put(name, oldValue);
			}
			else
				threadContext.remove(name);
		}
		updatedEntries.clear();
		nameToOldValue.clear();
	}
}