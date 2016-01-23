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
