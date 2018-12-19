package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance() {
        Point p1 = new Point(3, 1);
        Point p2 = new Point(3, 2);

        Assert.assertEquals(p1.distance(p2), 1.0);
    }

    @Test
    public void testDistanceNegativeCoordinates() {
        Point p1 = new Point(-12, -5);
        Point p2 = new Point(5, 4);

        Assert.assertEquals(p1.distance(p2), 19.235384061671343);
    }

    @Test
    public void testDistanceBigNumbers() {
        Point p1 = new Point(-565655, -5123);
        Point p2 = new Point(4543554, 454545);

        Assert.assertEquals(p1.distance(p2), 5129845.151259929);
    }

    @Test
    public void testDistanceFloats() {
        Point p1 = new Point(-4.3, -5.2);
        Point p2 = new Point(4.5, 1.3);

        Assert.assertEquals(p1.distance(p2), 10.940292500660117);
    }
}