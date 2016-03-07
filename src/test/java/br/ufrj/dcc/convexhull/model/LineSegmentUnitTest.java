package br.ufrj.dcc.convexhull.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class LineSegmentUnitTest
{
	@Test
	public void equals_SameLineSegment() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		
		LineSegment c = new LineSegment(a, b);
		LineSegment d = new LineSegment(b, a);
		
		assertTrue(c.equals(d));
		assertTrue(d.equals(c));
	}
	
	@Test
	public void hashCode_SameLineSegment() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		
		LineSegment c = new LineSegment(a, b);
		LineSegment d = new LineSegment(b, a);
		
		assertEquals(c.hashCode(), d.hashCode());
	}
	
	@Test
	public void equals_DifferentLineSegment() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		Point c = new Point(3.0, 3.0);
		
		LineSegment d = new LineSegment(a, b);
		LineSegment e = new LineSegment(b, c);
		
		assertFalse(d.equals(e));
	}
	
	@Test
	public void hashCode_DifferentLineSegment() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		Point c = new Point(3.0, 3.0);
		
		LineSegment d = new LineSegment(a, b);
		LineSegment e = new LineSegment(b, c);
		
		assertNotEquals(d.hashCode(), e.hashCode());
	}
	
	@Test
	public void contains_SameLineSegment() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		
		LineSegment c = new LineSegment(a, b);
		LineSegment d = new LineSegment(b, a);
		
		Set<LineSegment> lines = new HashSet<LineSegment>();
		lines.add(c);
		
		assertTrue(lines.contains(d));
	}
	
	@Test
	public void isPointOnLineSegment() throws Exception
	{
		Point a = new Point(1.0, 1.0);
		Point b = new Point(2.0, 2.0);
		Point c = new Point(3.0, 3.0);
		
		LineSegment d = new LineSegment(a, b);

		assertTrue(d.isPointOnLineSegment(a));
		assertTrue(d.isPointOnLineSegment(b));
		assertFalse(d.isPointOnLineSegment(c));
	}
}
