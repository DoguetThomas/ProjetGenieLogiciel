import java.time.LocalDateTime;
import model.ActivityImpl;
import model.StravaRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.UserImpl;
import services.Traitement;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class TraitementTest {
    private Traitement traitement;
    private UserImpl User1;
    private StravaRecord Record1;
    private StravaRecord Record2;
    private StravaRecord Record3;


   @BeforeEach
    public void setUp() {
        User1 = new UserImpl(22, true, 170,60);
        Record1 = new StravaRecord(0.0,0.0, 0.0,0.0,100.0,0.0, 100.0, "Activity1", 100.0, 100.0,100.0,100.0,100.0,100.0,LocalDateTime.of(2023, 12, 12, 12, 12, 12));
        Record2 = new StravaRecord(0.0,0.0, 0.0,0.0,100.0,0.0, 100.0, "Activity1", 100.0, 100.0,100.0,100.0,100.0,100.0,LocalDateTime.of(2023, 12, 12, 12, 12, 12));
        Record3 = new StravaRecord(0.0,0.0, 0.0,0.0,100.0,0.0, 100.0, "Activity1", 100.0, 100.0,100.0,100.0,100.0,100.0,LocalDateTime.of(2023, 12, 12, 12, 12, 12));
    }
}