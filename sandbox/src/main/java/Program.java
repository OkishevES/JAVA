public class Program
{
    public static void main(String[] args) {
        Point p1 = new Point(0,0);
        Point p2 = new Point(1,2);

        Double r = p1.distance(p2);

        System.out.println(
                "The distance between points (" + p1.x + "," + p1.y + ") and (" + p2.x + "," +p2.y + ") is " + r );
    }
}
