package br.ufrj.dcc.convexhull.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import br.ufrj.dcc.convexhull.model.Point;

/**
 * This class is a tool to read the input.
 * 
 * @author jonatascb
 *
 */
public class InputReader
{
	public static Set<Point> readFromFile(File input) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(input);
		scanner.next();
		
		Set<Point> points = new HashSet<Point>();
		
		while(scanner.hasNext())
		{
			Double x = scanner.nextDouble();
			Double y = scanner.nextDouble();
			
			points.add(new Point(x, y));
		}
		
		scanner.close();
		
		return points;
	}
}
