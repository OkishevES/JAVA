import org.testng.annotations.Test;

public class PointTest {

    @Test
    public void PointTestOne() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);

        assert (p1.distance(p2) == 1);
    }

    @Test
    public void PointTestTwo(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        assert (p1.distance(p2) == 0);
    }

    @Test
    public void PointTestThree(){
        Point p1 = new Point(0, 1);
        Point p2 = new Point(1, 0);

        assert (p1.distance(p2) == p2.distance(p1));
    }
}

