package br.ufrj.dcc.convexhull.model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is a convex hull calculator.
 * 
 * @author jonatascb
 *
 */
public class ConvexHull
{
	/**
	 * Basic idea: Given a set of points P, test each line segment 
	 * to see if it makes up an edge of the convex hull.
	 * 
	 * Complexity: O(n3)
	 * 
	 * @param points a given set of points P
	 * @return the convex hull i.e a set of points 
	 */
	public static Set<Point> bruteForce(Set<Point> points)
	{
		Set<LineSegment> lines = getAllPossibleLines(points);
		
		return null;
	}
	
	public static Set<Point> divideAndConquer(Set<Point> points)
	{
		return null;
	}

	public boolean isConvexHullEdge(LineSegment line, Set<Point> points)
	{
		return false;
	}
	
	/**
	 *  Given a set of points P, returns all possible line segments.
	 *  
	 * @see https://en.wikipedia.org/wiki/Complete_graph
	 * @param points a given set of points P
	 * @return a set of line segments
	 */
	protected static Set<LineSegment> getAllPossibleLines(Set<Point> points)
	{
		Set<LineSegment> lines = new HashSet<LineSegment>();
		
		for (Point point : points)
		{
			for (Point point2 : points)
			{
				if(point2.equals(point))
				{
					continue;
				}
				
				LineSegment line = new LineSegment(point, point2);
				lines.add(line);
			}
		}
		
		return lines;
	}
}
