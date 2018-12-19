package ru.mytest.functions;

public class CalculationDist {
    public static void main (String[] args) {
        Point p1 = new Point(6.0, 5.0);
        Point p2 = new Point(2.0, 5.0);
        double d = p1.distance(p2);
        System.out.println("Расстояние между точками с координатами " + "(" + p1.x + "," + p1.y + ") и (" + p2.x + "," + p2.y + ") равно = " + d);
    }

}
