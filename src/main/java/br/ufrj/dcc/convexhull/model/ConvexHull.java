package br.ufrj.dcc.convexhull.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class is a convex hull calculator.
 * 
 * @author jonatascb
 *
 */
public class ConvexHull
{
	protected static final boolean IS_FIRST_HALF = true;
	protected static final boolean NOT_IS_FIRST_HALF = false;

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
	
	/**
	 * Basic idea: Finding the convex hull of small sets is easier than finding
	 * the hull of large ones.
	 * 
	 * Complexity: O(nlogn)
	 * 
	 * @param points a given set of points P
	 * @return
	 */
	public static Set<Point> divideAndConquer(Set<Point> points)
	{
		Set<Point> firstSmallSet = splitSet(points, IS_FIRST_HALF);
		Set<Point> secondSmallSet = splitSet(points, NOT_IS_FIRST_HALF);
		
		Set<Point> firstSetPointsOnConvexHull = bruteForce(firstSmallSet);
		Set<Point> secondSetPointsOnConvexHull = bruteForce(secondSmallSet);
		
		Set<Point> pointsOnConvexHull = mergeHulls(firstSetPointsOnConvexHull, secondSetPointsOnConvexHull);
		
		return pointsOnConvexHull;
	}

	/**
	 * Given a original set, split it by two, returning the first or second half.
	 * 
	 * @param points a given set of points
	 * @param isFirstHalf a flag to check if it should return the first half of the subset
	 * @return
	 */
	protected static Set<Point> splitSet(Set<Point> points, boolean isFirstHalf)
	{
		if(isFirstHalf)
		{
			int setSize = points.size() / 2;
			
			Set<Point> firstHalf = new HashSet<Point>();
			Iterator<Point> iterator = points.iterator();
	
			for (int i = 0; i < setSize; i++)
			{
				Point nextPoint = iterator.next();
				
				firstHalf.add(nextPoint);
			}

			points.removeAll(firstHalf);
			
			return firstHalf;
		}
		
		return points;
	}
	
	private static Set<Point> mergeHulls(Set<Point> firstSetPointsOnConvexHull, Set<Point> secondSetPointsOnConvexHull)
	{
		// TODO Auto-generated method stub
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
			
			lastOrientation = orientation;
		}
		
		return true;
	}
}
