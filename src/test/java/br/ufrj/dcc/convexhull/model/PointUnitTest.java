package br.ufrj.dcc.convexhull.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PointUnitTest
{
	@Test
	public void equals_SamePoint() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(1.0, 1.0);
		
		assertTrue(a.equals(b));
	}
	
	@Test
	public void hashCode_SamePoint() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(1.0, 1.0);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void equals_DifferentPoint() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		
		assertFalse(a.equals(b));
	}
	
	@Test
	public void hashCode_DifferentPoint() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		
		assertNotEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void getOrientationSignalGreaterThanZero() throws Exception
	{
		Point a = new Point(0.0, 0.0);
		Point b = new Point(0.0, 5.0);
		Point c = new Point(5.0, 0.0);
		Point d = new Point(-5.0, 0.0);
		LineSegment line = new LineSegment(a, b);
		
		Boolean isPointCOnTheLeftOfLineSegment = c.isPointOnTheLeftOfLineSegment(line);
		Boolean isPointDOnTheLeftOfLineSegment = d.isPointOnTheLeftOfLineSegment(line);
		
		assertFalse(isPointCOnTheLeftOfLineSegment);
		assertTrue(isPointDOnTheLeftOfLineSegment);
	}
}
