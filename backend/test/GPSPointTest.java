import model.GpsPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GPSPointTest {

    @Test
    void testGPSPointTestinitialise(){
        GpsPoint gpspoint = new GpsPoint(504433614.0,-999056531.0);
        assertEquals(504433614.0, gpspoint.getLatitude(), "La latitude doit correspondre");
        assertEquals(-999056531.0, gpspoint.getLongitude(), "La longitude doit correspondre");

    }

}
