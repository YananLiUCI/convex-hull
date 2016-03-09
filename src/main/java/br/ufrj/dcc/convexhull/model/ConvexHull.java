package br.ufrj.dcc.convexhull.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
	protected static final boolean IS_NOT_FIRST_HALF = false;

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
	public static List<Point> bruteForce(List<Point> points)
	{
		Set<LineSegment> lines = getAllPossibleLineSegment(points);
		List<Point> pointsOnConvexHull = checkIfEachLineSegmentMakesAnEdgeOfConvexHull(points, lines);
		
		return pointsOnConvexHull;
	}
	
	/**
	 * Basic idea: Finding the convex hull of small sets is easier than finding
	 * the hull of large ones.
	 * 
	 * The first small set consists of half the points with the lowest x coordinates
	 * and the second small set consists of half the points with the highest x coordinates.
	 * 
	 * Complexity: O(nlogn)
	 * 
	 * @param points a given set of points P
	 * @return
	 */
	public static List<Point> divideAndConquer(List<Point> points)
	{
		Collections.sort(points);
		
		List<Point> firstSmallSet = splitSet(points, IS_FIRST_HALF);
		List<Point> secondSmallSet = splitSet(points, IS_NOT_FIRST_HALF);
		
		List<Point> firstSetPointsOnConvexHull = bruteForce(firstSmallSet);
		List<Point> secondSetPointsOnConvexHull = bruteForce(secondSmallSet);
		
		List<Point> pointsOnConvexHull = mergeHulls(firstSetPointsOnConvexHull, secondSetPointsOnConvexHull);
		
		return pointsOnConvexHull;
	}

	/**
	 * Given a original set, split it by two, returning the first or second half.
	 * 
	 * @param points a given set of points
	 * @param isFirstHalf a flag to check if it should return the first half of the subset
	 * @return
	 */
	protected static List<Point> splitSet(List<Point> points, boolean isFirstHalf)
	{
		if(isFirstHalf)
		{
			int setSize = points.size() / 2;
			
			List<Point> smallSet = new ArrayList<Point>();
			
			for (int i = 0; i < setSize; i++)
			{
				Point nextPoint = points.get(i);
				
				smallSet.add(nextPoint);
			}

			points.removeAll(smallSet);
			
			return smallSet;
		}
		
		return points;
	}
	
	/**
	 * Merges two convex hulls using the tangent method.
	 * 
	 * @param firstConvexHull a convex hull
	 * @param secondConvexHull another convex hull
	 * @return mergedHull a set of points merging the two convex hulls 
	 * @see http://www.cs.wustl.edu/~pless/506/l3.html
	 */
	private static List<Point> mergeHulls(List<Point> firstConvexHull, List<Point> secondConvexHull)
	{
		LineSegment lowerTangent = findLowerTangent(firstConvexHull, secondConvexHull);
		LineSegment upperTangent = findUpperTangent(lowerTangent);
		
		List<Point> convexHull = getConvexHull(lowerTangent, upperTangent, firstConvexHull, secondConvexHull);
		
		return convexHull;
	}
	
	private static List<Point> getConvexHull(LineSegment lowerTangent, LineSegment upperTangent, List<Point> firstConvexHull, List<Point> secondConvexHull)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static LineSegment findUpperTangent(LineSegment lowerTangent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method find the lower tangent using a simple walking procedure.
	 * 
	 * @param firstConvexHull the first convex hull containing the leftmost points
	 * @param secondConvexHull the second convex hull containing the rightmost points
	 * @return lowerTangent the lowerTangent between those two convex hulls
	 */
	private static LineSegment findLowerTangent(List<Point> firstConvexHull, List<Point> secondConvexHull)
	{
		int rightmostPointFirstConvexHullIndex = firstConvexHull.size()-1;
		int leftmostPointSecondConvexHullIndex = 0;
		
		Point rightmostPointFirstConvexHull = firstConvexHull.get(rightmostPointFirstConvexHullIndex);
		Point leftmostPointSecondConvexHull = secondConvexHull.get(leftmostPointSecondConvexHullIndex); 
		
		LineSegment lowerTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);

		Boolean isEveryPointOnTheSameLineFirstConvexHull = checkIfEveryPointIsOnTheSameSide(lowerTangent, firstConvexHull);
		Boolean isEveryPointOnTheSameLineSecondConvexHull = checkIfEveryPointIsOnTheSameSide(lowerTangent, secondConvexHull);
		
		while(!isEveryPointOnTheSameLineFirstConvexHull && !isEveryPointOnTheSameLineSecondConvexHull)
		{
			rightmostPointFirstConvexHullIndex--;
			leftmostPointSecondConvexHullIndex++;
			
			rightmostPointFirstConvexHull = firstConvexHull.get(rightmostPointFirstConvexHullIndex);
			leftmostPointSecondConvexHull = secondConvexHull.get(leftmostPointSecondConvexHullIndex); 
			
			lowerTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);

			isEveryPointOnTheSameLineFirstConvexHull = checkIfEveryPointIsOnTheSameSide(lowerTangent, firstConvexHull);
			isEveryPointOnTheSameLineSecondConvexHull = checkIfEveryPointIsOnTheSameSide(lowerTangent, secondConvexHull);
		}
		
		return lowerTangent;
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
	private static List<Point> checkIfEachLineSegmentMakesAnEdgeOfConvexHull(List<Point> points, Set<LineSegment> lines)
	{
		List<Point> pointsOnConvexHull = new ArrayList<Point>();
		
		for (LineSegment line : lines)
		{
			if(checkIfEveryPointIsOnTheSameSide(line, points))
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
	protected static Set<LineSegment> getAllPossibleLineSegment(List<Point> points)
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
	private static boolean checkIfEveryPointIsOnTheSameSide(LineSegment line, List<Point> points)
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
