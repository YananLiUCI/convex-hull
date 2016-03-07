package br.ufrj.dcc.convexhull.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import br.ufrj.dcc.convexhull.model.ConvexHull;
import br.ufrj.dcc.convexhull.model.Point;
import br.ufrj.dcc.convexhull.utils.InputReader;

/**
 * This is the main class.
 * 
 * @author jonatascb
 *
 */
public class Main
{
	public static void main(String[] args) throws FileNotFoundException
	{
		File input = new File("/home/jonatascb/projetos/ConvexHull/input/input2.txt");
		
		Set<Point> inputPoints = InputReader.readFromFile(input);
		Set<Point> convexPoints = ConvexHull.bruteForce(inputPoints);
		
		System.out.println(convexPoints);
	}
}
