package ru.stqa.pft.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {

        Square s = new Square(5);
        System.out.println("ѕлощ€дь кварата со стороной " + s.l + " = " + s.area());

        Rectangle r = new Rectangle(4, 6);
        System.out.println("ѕлощ€дь пр€моугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

        Point p1 = new Point(4, 2);
        Point p2 = new Point(5, 13);
        System.out.println(p1.distance(p2));

    }

}