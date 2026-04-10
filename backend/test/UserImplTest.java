import model.UserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class UserImplTest {

    @Test
    void testCreationEtLectureUtilisateur() {
        UserImpl user = new UserImpl(25, true, 180.5, 75.0);
        assertEquals(25, user.getAge());
        Assertions.assertTrue(user.getGenre());
        assertEquals(180.5, user.getHeight());
        assertEquals(75.0, user.getWeight());
    }

    @Test
    void testSetters() {
        UserImpl user = new UserImpl();
        user.setAge(30);
        user.setGenre(false);
        user.setHeight(170.0);
        user.setWeight(70.0);

        assertEquals(30, user.getAge());
        assertFalse(user.getGenre());
        assertEquals(170.0, user.getHeight());
        assertEquals(70.0, user.getWeight());
    }

    @Test
    void testValeursImpossible() {
        assertThrows(IllegalArgumentException.class, () -> new UserImpl(-5, true, 180.5, 75.0));
        assertThrows(IllegalArgumentException.class, () -> new UserImpl(25, true, 40.0, 75.0));
        assertThrows(IllegalArgumentException.class, () -> new UserImpl(25, true, 180.5, 20.0));
    }

    @Test
    void testCalculsCardiaques() {
        UserImpl user = new UserImpl(20, false, 180.0, 75.0);

        user.setMaxHRUser(20, false);
        assertEquals(200.0, user.getMaxHRUser());

        user.setMaxHRUser(20, true);
        assertEquals(206.0, user.getMaxHRUser());

        user.setMaxHRUser(20, false);
        user.setSeuilZoneHR(user.getMaxHRUser());
        ArrayList<Double> seuils = user.getSeuilZoneHR();

        assertEquals(120.0, seuils.get(0));
        assertEquals(180.0, seuils.get(3));
    }

    @Test
    void testToString() {
        UserImpl user = new UserImpl(25, true, 180.0, 75.0);
        String s = user.toString();
        Assertions.assertTrue(s.contains("age=25"));
        Assertions.assertTrue(s.contains("genre=true"));
    }
}