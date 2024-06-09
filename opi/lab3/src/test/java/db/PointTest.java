package db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    Point point;

    @Test
    public void test() {
        point = new Point(1, 2, 3);
        assertFalse(point.isHit());

        point = new Point(-1, 2, 3);
        assertTrue(point.isHit());
    }
}
