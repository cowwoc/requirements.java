/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.natives.internal.util;

/**
 * Outputs all 8-bit color combinations.
 */
public final class ColorTest
{
	/**
	 * The main entry point.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args)
	{
		System.out.println("\u001B[0mmode\tbackground\tforeground");
		for (int mode = -1; mode < 2; ++mode)
		{
			for (int background = 40; background < 108; ++background)
			{
				if (background == 48)
					background = 100;
				System.out.print("\u001B[0m");
				if (mode >= 0)
					System.out.print(mode);
				System.out.print("\t" + background + "\t\t\u001B[" + background);
				if (mode >= 0)
					System.out.print(";" + mode);
				System.out.print("m");
				for (int foreground = 30; foreground < 98; ++foreground)
				{
					if (foreground == 38)
						foreground = 90;
					System.out.print("\u001B[" + background + ";" + foreground + "m" + foreground + " ");
				}
				System.out.println("\u001B[0m");
			}
		}
	}
}