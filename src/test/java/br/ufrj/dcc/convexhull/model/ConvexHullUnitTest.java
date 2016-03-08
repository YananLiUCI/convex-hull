package br.ufrj.dcc.convexhull.model;

import static br.ufrj.dcc.convexhull.model.ConvexHull.IS_FIRST_HALF;
import static br.ufrj.dcc.convexhull.model.ConvexHull.NOT_IS_FIRST_HALF;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ConvexHullUnitTest
{
	@Test
	public void getAllPossibleLines() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(1.0, 2.0);
		Point c = new Point(2.0, 2.0);
		Point d = new Point(5.0, 3.0);
		Point e = new Point(3.0, 4.0);
		Point f = new Point(1.0, 4.0);
		Point g = new Point(2.0, 5.0);
		
		Set<Point> points = new HashSet<Point>();
		points.addAll(asList(a, b, c, d, e, f, g));
		
		Set<LineSegment> result = ConvexHull.getAllPossibleLineSegment(points);
		
		assertEquals(21, result.size());
	}
	
	@Test
	public void splitSet() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		Point c = new Point(3.0, 3.0);
		Point d = new Point(4.0, 4.0);
		
		Set<Point> points = new HashSet<Point>();
		points.addAll(asList(a, b, c, d));
		
		Set<Point> firstHalf = ConvexHull.splitSet(points, IS_FIRST_HALF);
		Set<Point> secondHalf = ConvexHull.splitSet(points, NOT_IS_FIRST_HALF);
		
		assertEquals(firstHalf.size(), 2);
		assertEquals(secondHalf.size(), 2);
		assertFalse(secondHalf.containsAll(firstHalf));
	}
	
	@Test
	public void splitSet_oddQuantityOfPoints() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		Point c = new Point(3.0, 3.0);
		
		Set<Point> points = new HashSet<Point>();
		points.addAll(asList(a, b, c));
		
		Set<Point> firstHalf = ConvexHull.splitSet(points, IS_FIRST_HALF);
		Set<Point> secondHalf = ConvexHull.splitSet(points, NOT_IS_FIRST_HALF);
		
		assertEquals(firstHalf.size(), 1);
		assertEquals(secondHalf.size(), 2);
		assertFalse(secondHalf.containsAll(firstHalf));
	}
}
