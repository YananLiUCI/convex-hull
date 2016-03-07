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
	 * @see http://www.cs.jhu.edu/~misha/Fall05/09.13.05.pdf 
	 */
	public static Set<Point> bruteForce(Set<Point> points)
	{
		Set<LineSegment> lines = getAllPossibleLineSegment(points);
		Set<Point> pointsOnConvexHull = checkIfEachLineSegmentMakesAnEdgeOfConvexHull(points, lines);
		
		return pointsOnConvexHull;
	}
	
	public static Set<Point> divideAndConquer(Set<Point> points)
	{
		return null;
	}

	/**
	 * Given a set of points P and a set of line segments,
	 * iterate over all line segments to check if it makes an edge of
	 * the convex hull.
	 * 
	 * @param points a given set of points P
	 * @param lines a set of line segments
	 * @return a set of points that makes an edge of the convex hull
	 */
	private static Set<Point> checkIfEachLineSegmentMakesAnEdgeOfConvexHull(Set<Point> points, Set<LineSegment> lines)
	{
		Set<Point> pointsOnConvexHull = new HashSet<Point>();
		
		for (LineSegment line : lines)
		{
			if(isConvexHullEdge(line, points))
			{
				pointsOnConvexHull.add(line.a());
				pointsOnConvexHull.add(line.b());
			}
		}
		
		return pointsOnConvexHull;
	}
	
	/**
	 *  Given a set of points P, returns all possible line segments.
	 *  
	 * @see https://en.wikipedia.org/wiki/Complete_graph
	 * @param points a given set of points P
	 * @return a set of line segments
	 */
	protected static Set<LineSegment> getAllPossibleLineSegment(Set<Point> points)
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
	
	/**
	 * Given a line segment and a set of points, check if every point is on the same side of the line segment.
	 * @param line a given line
	 * @param points a given set of points
	 * @return <b>true</b> if every point is on the same side <b>false</b> if not
	 */
	private static boolean isConvexHullEdge(LineSegment line, Set<Point> points)
	{
		Boolean lastOrientation = null;
		Boolean orientation = null;
		
		for (Point point : points)
		{
			if(line.isPointOnLineSegment(point))
			{
				continue;
			}
			
			if(lastOrientation == null)
			{
				lastOrientation = point.isPointOnTheLeftOfLineSegment(line);
			}
			
			orientation = point.isPointOnTheLeftOfLineSegment(line);
			
			if(!lastOrientation.equals(orientation))
			{
				return false;
			}
		}
		
		return true;
	}
}
