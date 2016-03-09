package br.ufrj.dcc.convexhull.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.ufrj.dcc.convexhull.model.Point;

public class InputReaderUnitTest
{
	@Test
	public void readFromFile_SingleTestCase() throws FileNotFoundException
	{
		URL url = InputReaderUnitTest.class.getResource("dataset/singleTestCase");
		File input = new File(url.getFile());
		
		List<Point> actualPoints = InputReader.readFromFile(input);
		
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(1.0, 2.0));
		expectedPoints.add(new Point(3.0, 4.0));
		expectedPoints.add(new Point(5.0, 6.0));
		
		assertTrue(expectedPoints.equals(actualPoints));
	}
}
