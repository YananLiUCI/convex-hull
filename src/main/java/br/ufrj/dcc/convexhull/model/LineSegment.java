package br.ufrj.dcc.convexhull.model;

/**
 * This class represents a line segment.
 * 
 * @author jonatascb
 *
 */
public class LineSegment
{
	private Point a;
	private Point b;
	
	public LineSegment(Point a, Point b)
	{
		this.a = a;
		this.b = b;
	}
	
	public Point a()
	{
		return a;
	}
	
	public Point b()
	{
		return b;
	}
	
	public String toString()
	{
		return "[(" + a.x() + ", " + a.y() + ")" + ", " + "(" + b.x() + ", " + b.y() + ")]";
	}
	
	public boolean equals(Object other)
	{
		if(other == null) return false;
		if(!(other instanceof LineSegment)) return false;
		
		LineSegment otherLineSegment = (LineSegment) other;
		
		return otherLineSegment.a().equals(this.a()) && otherLineSegment.b().equals(this.b()) ||
			   otherLineSegment.a().equals(this.b()) && otherLineSegment.b().equals(this.a());
	}
	
	public int hashCode()
	{
		return (int) a.hashCode() * b.hashCode();
	}
}
