package br.ufrj.dcc.convexhull.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.ufrj.dcc.convexhull.model.Point;

/**
 * This class is a tool to read the input.
 * 
 * @author jonatascb
 *
 */
public class InputReader
{
	public static List<Point> readFromFile(File input) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(input);
		scanner.next();
		
		List<Point> points = new ArrayList<Point>();
		
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
