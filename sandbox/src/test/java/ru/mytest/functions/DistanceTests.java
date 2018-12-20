package ru.mytest.functions;

import org.testng.Assert;
import org.testng.annotations.Test;


public class DistanceTests {

    @Test
    public void DistTest1() {
        Point p1 = new Point (5.0, 5.0);
        Point p2 = new Point (2.0, 1.0);
        Assert.assertEquals(p1.distance(p2),5.0);
    }
    @Test
    public void DistTest2() {
        Point p1 = new Point (6.0, 5.0);
        Point p2 = new Point (2.0, 5.0);
        Assert.assertEquals(p1.distance(p2),4.0);
    }
    @Test (enabled = false)
    public void DistTest3() {
        Point p1 = new Point (5.0, 5.0);
        Point p2 = new Point (2.0, 3.0);
        Assert.assertEquals(p1.distance(p2),4.0);
    }
    @Test (enabled = false)
    public void DistTest4() {
        Point p1 = new Point (5.0, 5.0);
        Point p2 = new Point (7.0, 4.0);
        Assert.assertEquals(p1.distance(p2),6.0);
    }


}
