package br.ufrj.dcc.convexhull.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
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
		File input = new File("/home/jonatascb/projetos/ConvexHull/input/input.txt");
		
		List<Point> inputPoints = InputReader.readFromFile(input);
		Set<Point> convexPoints = new HashSet<Point>(ConvexHull.divideAndConquer(inputPoints));
		
		System.out.println(convexPoints);
	}
}
