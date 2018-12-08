/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.secrets;

import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffWriter;
import org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding;

/**
 * @see SharedSecrets
 */
public interface SecretTerminalEncoding
{
	DiffWriter diff(TerminalEncoding encoding, String actual, String expected);
}
