package br.ufrj.dcc.convexhull.model;

/**
 * This class represents a 2-D point.
 * 
 * @author jonatascb
 *
 */
public class Point
{
	private Double x;
	private Double y;
	
	public Point(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Check if a point is on the left or on the right of a given line segment.
	 * 
	 * @param line a given line segment
	 * @param point a given point P
	 * @return <b>true</b> if the point is on the left or <b>false</b> if the point is on the right
	 * @see https://www.cs.cmu.edu/~quake/robust.html
	 */
	public boolean isPointOnTheLeftOfLineSegment(LineSegment line)
	{
		if(((line.a().x() - x) * (line.b().y() - y)) - ((line.a().y() - y) * (line.b().x() - x)) > 0.0)
		{
			return true;
		}
		
		return false;
	}
	
	public Double x()
	{
		return x;
	}
	
	public Double y()
	{
		return y;
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Object other)
	{
		if(other == null) return false;
		if(!(other instanceof Point)) return false;
		
		Point otherPoint = (Point) other;
		
		return otherPoint.x().equals(this.x()) && otherPoint.y().equals(this.y());
	}
	
	public int hashCode()
	{
		return (int) x.intValue() * y.intValue();
	}
}
