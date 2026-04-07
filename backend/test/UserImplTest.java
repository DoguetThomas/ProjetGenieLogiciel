import model.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserImplTest {

    @Test
    void testCreationEtLectureUtilisateur() {
        UserImpl user = new UserImpl(25, true, 180.5, 75.0);


        assertEquals(25, user.getAge(), "L'âge récupéré doit être 25");
        assertEquals(true, user.getGenre(), "Le genre récupéré doit être true");
        assertEquals(180.5, user.getHeight(), "La taille récupérée doit être 180.5");
        assertEquals(75.0, user.getWeight(), "Le poids récupéré doit être 75.0");
    }
}