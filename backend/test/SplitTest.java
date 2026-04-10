import model.Split;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SplitTest {

    @Test
    void testSplitInitialise() {

        Split split = new Split(1, 350, 148);

        assertEquals(1, split.getKm(), "Les km doivent correspondre");
        assertEquals(350, split.getDurationSeconds(), "La durée doit correspondre");
        assertEquals(148, split.getAvgHeartRate(), "Le rythme cardiaque doit correspondre");
    }

    @Test
    void testvaleursnegatives(){
        Split split1 = new Split(-1, 350, 148);
        assertFalse(split1.getKm()>0);
        Split split2 = new Split(1, -350, 148);
        assertFalse(split2.getDurationSeconds()>0);
        Split split3 = new Split(1, -350, -148);
        assertFalse(split3.getAvgHeartRate()>0);

    }
}

