package br.ufrj.dcc.convexhull.model;

import java.util.Comparator;

public class PointClockwiseComparator implements Comparator<Point>
{
	private Point center;
	
	public PointClockwiseComparator(Point center)
	{
		this.center = center;
	}

	@Override
	public int compare(Point a, Point b)
	{
		final int BEFORE = -1;
	    final int AFTER = 1;
		
	    if(a.x() >= 0 && b.x() < 0) return BEFORE;
	    else if(a.x() == 0 && b.x() == 0)
	    {
	    	if (a.y() > b.y())
    		{
	    		return BEFORE;
    		}

	    	return AFTER;
	    }
	    
	    double det = (a.x() - center.x()) * (b.y() - center.y()) - (b.x() - center.x()) * (a.y() - center.y());
		if (det < 0) return BEFORE;
		else if (det > 0) return AFTER;
	    
	    double d1 = (a.x() - center.x()) * (a.x() - center.x()) + (a.y() - center.y()) * (a.y() - center.y());
	    double d2 = (b.x() - center.x()) * (b.x() - center.x()) + (b.y() - center.y()) * (b.y() - center.y());
	    
	    if(d1 > d2)
	    {
	    	return BEFORE;
	    }
	    
	    return AFTER;
	}
}
