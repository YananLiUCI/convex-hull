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

		sortPointsClockwiseOrder(firstSetPointsOnConvexHull);
		sortPointsCounterClockwiseOrder(secondSetPointsOnConvexHull);
		
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
		LineSegment upperTangent = findUpperTangent(firstConvexHull, secondConvexHull);
		
		List<Point> convexHull = getConvexHull(lowerTangent, upperTangent, firstConvexHull, secondConvexHull);
		
		return convexHull;
	}
	
	/**
	 * Given the two convex hulls, with the first one containing the lowest x coordinates 
	 * and the second contains the highest coordinates, given also the lower and upper tangent,
	 * returns a convex hull set of points by discarding the points between those two tangents.
	 * 
	 * @param lowerTangent the lower tangent of the two convex hulls
	 * @param upperTangent the upper tangent of the two convex hulls
	 * @param firstConvexHull the first convex hull containing the lowest x coordinates points
	 * @param secondConvexHull the second convex hull containing the highest x coordinates points
	 * @return the convex hull merged
	 */
	private static List<Point> getConvexHull(LineSegment lowerTangent, LineSegment upperTangent, List<Point> firstConvexHull, List<Point> secondConvexHull)
	{
		List<Point> convexHull = new ArrayList<Point>();
		
		for (Point point : firstConvexHull)
		{
			if(point.x() > upperTangent.a().x() || point.x() > lowerTangent.a().x())
			{
				if(point.y() < upperTangent.a().y() || point.y() > lowerTangent.a().y())
				{
					continue;
				}
			}
			
			convexHull.add(point);
		}
		
		for (Point point : secondConvexHull)
		{
			if(point.x() < upperTangent.b().x() || point.x() < lowerTangent.b().x())
			{
				if(point.y() < upperTangent.b().y() && point.y() > lowerTangent.b().y())
				{
					continue;
				}
			}
			
			convexHull.add(point);
		}
		
		return convexHull;
	}

	/**
	 * This method find the upper tangent. This method is symmetric to the lower tangent method.
	 * 
	 * @param firstConvexHull the first convex hull containing the leftmost points
	 * @param secondConvexHull the second convex hull containing the rightmost points
	 * @return upperTangent the upperTangent between those two convex hulls
	 */
	protected static LineSegment findUpperTangent(List<Point> firstConvexHull, List<Point> secondConvexHull)
	{
		Point rightmostPointFirstConvexHull = getRightMostPoint(firstConvexHull);
		Point leftmostPointSecondConvexHull = getLeftMostPoint(secondConvexHull); 
		
		LineSegment upperTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);

		Boolean isEveryPointOnTheSameLine = checkIfEveryPointIsOnTheSameSide(upperTangent, firstConvexHull, secondConvexHull);
		
		while(!isEveryPointOnTheSameLine)
		{
			int leftmostPointSecondConvexHullIndex = secondConvexHull.indexOf(leftmostPointSecondConvexHull) ;
			leftmostPointSecondConvexHullIndex = (leftmostPointSecondConvexHullIndex - 1) % secondConvexHull.size();
			
			if(leftmostPointSecondConvexHullIndex < 0)
			{
				leftmostPointSecondConvexHullIndex+= secondConvexHull.size();
			}
			
			leftmostPointSecondConvexHull = secondConvexHull.get(leftmostPointSecondConvexHullIndex); 
			
			upperTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);
			
			isEveryPointOnTheSameLine = checkIfEveryPointIsOnTheSameSide(upperTangent, firstConvexHull, secondConvexHull);
			
			if(isEveryPointOnTheSameLine)
			{
				return upperTangent;
			}

			int rightmostPointFirstConvexHullIndex = firstConvexHull.indexOf(rightmostPointFirstConvexHull);
			rightmostPointFirstConvexHullIndex = (rightmostPointFirstConvexHullIndex - 1) % firstConvexHull.size();
			
			if(rightmostPointFirstConvexHullIndex < 0)
			{
				rightmostPointFirstConvexHullIndex+= firstConvexHull.size();
			}
			
			rightmostPointFirstConvexHull = firstConvexHull.get(rightmostPointFirstConvexHullIndex);
			
			upperTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);
			
			isEveryPointOnTheSameLine = checkIfEveryPointIsOnTheSameSide(upperTangent, firstConvexHull, secondConvexHull);
		}
		
		return upperTangent;
	}

	/**
	 * This method find the lower tangent using a simple walking procedure.
	 * 
	 * @param firstConvexHull the first convex hull containing the leftmost points
	 * @param secondConvexHull the second convex hull containing the rightmost points
	 * @return lowerTangent the lowerTangent between those two convex hulls
	 */
	protected static LineSegment findLowerTangent(List<Point> firstConvexHull, List<Point> secondConvexHull)
	{
		Point rightmostPointFirstConvexHull = getRightMostPoint(firstConvexHull);
		Point leftmostPointSecondConvexHull = getLeftMostPoint(secondConvexHull); 
		
		LineSegment lowerTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);

		Boolean isEveryPointOnTheSameLine = checkIfEveryPointIsOnTheSameSide(lowerTangent, firstConvexHull, secondConvexHull);
		
		while(!isEveryPointOnTheSameLine)
		{
			int rightmostPointFirstConvexHullIndex = firstConvexHull.indexOf(rightmostPointFirstConvexHull);
			rightmostPointFirstConvexHullIndex = (rightmostPointFirstConvexHullIndex + 1) % firstConvexHull.size();
			rightmostPointFirstConvexHull = firstConvexHull.get(rightmostPointFirstConvexHullIndex);
			
			lowerTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);

			isEveryPointOnTheSameLine = checkIfEveryPointIsOnTheSameSide(lowerTangent, firstConvexHull, secondConvexHull);
			
			if(isEveryPointOnTheSameLine)
			{
				return lowerTangent;
			}

			int leftmostPointSecondConvexHullIndex = secondConvexHull.indexOf(leftmostPointSecondConvexHull);
			leftmostPointSecondConvexHullIndex = (leftmostPointSecondConvexHullIndex + 1) % secondConvexHull.size();
			leftmostPointSecondConvexHull = secondConvexHull.get(leftmostPointSecondConvexHullIndex); 
			
			lowerTangent = new LineSegment(rightmostPointFirstConvexHull, leftmostPointSecondConvexHull);

			isEveryPointOnTheSameLine = checkIfEveryPointIsOnTheSameSide(lowerTangent, firstConvexHull, secondConvexHull);
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
				Point a = line.a();
				Point b = line.b();
				
				if(!pointsOnConvexHull.contains(a))
				{
					pointsOnConvexHull.add(a);
				}
				
				if(!pointsOnConvexHull.contains(b))
				{
					pointsOnConvexHull.add(b);
				}
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
	
	/**
	 * Given a line segment and two set of points, check if every point (considering both sets) is on the same side of the line segment.
	 * 
	 * @param line a given line segment
	 * @param firstConvexHull a set of points
	 * @param secondConvexHull another set of points
	 * @return true if every point is on the same side or false otherwise
	 */
	private static boolean checkIfEveryPointIsOnTheSameSide(LineSegment line, List<Point> firstConvexHull, List<Point> secondConvexHull)
	{
		Boolean lastOrientation = null;
		Boolean orientation = null;
		
		List<Point> points = new ArrayList<Point>(firstConvexHull);
		points.addAll(secondConvexHull);
		
		for (Point point : points)
		{
			if(line.isPointOnLineSegment(point))
			{
				continue;
			}

			if(point.isPointAlignedWithLineSegment(line))
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
	
	/**
	 * Sort a given set of points in clockwise order.
	 * 
	 * @param points a given set of points
	 * @see http://stackoverflow.com/questions/6989100/sort-points-in-clockwise-order
	 */
	protected static void sortPointsClockwiseOrder(List<Point> points)
	{
		Point centerPoint = getCenterPoint(points);
		Collections.sort(points, new PointClockwiseComparator(centerPoint));
	}
	
	/**
	 * Sort a given set of points in counterclockwise order.
	 * 
	 * @param points a given set of points
	 * @see http://stackoverflow.com/questions/6989100/sort-points-in-clockwise-order
	 */
	protected static void sortPointsCounterClockwiseOrder(List<Point> points)
	{
		Point centerPoint = getCenterPoint(points);
		Collections.sort(points, new PointCounterClockwiseComparator(centerPoint));
	}

	/**
	 * Computes the center of a given set of points.
	 * 
	 * @param points a given set of points
	 * @return center a point which is the center of this point set.
	 * @see http://stackoverflow.com/questions/6989100/sort-points-in-clockwise-order
	 */
	protected static Point getCenterPoint(List<Point> points)
	{
		double sumOfXCoordinates = 0.0;
		double sumOfYCoordinates = 0.0;
		
		for (Point point : points)
		{
			sumOfXCoordinates+= point.x();
			sumOfYCoordinates+= point.y();
		}
		
		sumOfXCoordinates = sumOfXCoordinates / points.size();
		sumOfYCoordinates = sumOfYCoordinates / points.size();
		
		Point center = new Point(sumOfXCoordinates, sumOfYCoordinates);
		
		return center;
	}

	/**
	 * Return the leftmost point in a list.
	 * @param points
	 * @return leftMost the leftMost point
	 */
	private static Point getLeftMostPoint(List<Point> points)
	{
		Point leftMost = null;
		
		for (Point point : points)
		{
			if(leftMost == null)
			{
				leftMost = point;
			}
			
			if(point.x() < leftMost.x())
			{
				leftMost = point;
			}
		}
		
		return leftMost;
	}

	
	
	/**
	 * Return the rightMost point of a given list.
	 * @param points
	 * @return rightMost the rightmost point of a list
	 */
	private static Point getRightMostPoint(List<Point> points)
	{
		Point rightMost = null;
		
		for (Point point : points)
		{
			if(rightMost == null)
			{
				rightMost = point;
			}
			
			if(point.x() > rightMost.x())
			{
				rightMost = point;
			}
		}
		
		return rightMost;
	}
}
