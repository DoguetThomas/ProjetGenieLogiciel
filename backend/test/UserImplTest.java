import model.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserImplTest {

    @Test
    void testCreationEtLectureUtilisateur() {
        UserImpl user = new UserImpl(25, true, 180.5, 75.0);


        assertEquals(25, user.getAge(), "L'âge récupéré doit être 25");
        assertEquals(true, user.getGenre(), "Le genre récupéré doit être true");
        assertEquals(180.5, user.getHeight(), "La taille récupérée doit être 180.5");
        assertEquals(75.0, user.getWeight(), "Le poids récupéré doit être 75.0");
    }

    @Test
    void testValeursImpossible() {
        assertThrows(Exception.class, () -> {
            new UserImpl(-5, true, 180.5, 75.0);
        }, "Un âge en dessous du seuil doit déclencher une exception");
        assertThrows(Exception.class, () -> {
            new UserImpl(120, true, 180.5, 75.0);
        }, "Un âge au dessus du seuil doit déclencher une exception");
        assertThrows(Exception.class, () -> {
            new UserImpl(20, true, 70.0, 75.0);
        }, "Une taille en dessous du seuil doit déclencher une exception");
        assertThrows(Exception.class, () -> {
            new UserImpl(20, true, 300.0, 75.0);
        }, "Une taille au dessus du seuil doit déclencher une exception");
        assertThrows(Exception.class, () -> {
            new UserImpl(20, true, 180.5, 30.0);
        }, "Un poids en dessous du seuil doit déclencher une exception");
        assertThrows(Exception.class, () -> {
            new UserImpl(20, true, 180.5, 30.0);
        }, "Un poids au dessus du seuil doit déclencher une exception");
    }
    @Test
    void testEgaliteDeuxUtilisateurs() {
        UserImpl user1 = new UserImpl(20, false, 164.0, 56.5);
        UserImpl user2 = new UserImpl(20, false, 164.0, 56.5);

        assertEquals(user1.getAge(), user2.getAge(), "Deux utilisateurs doivent avoir le même age");
        assertEquals(user1.getGenre(), user2.getGenre(), "Deux utilisateurs doivent avoir le même genre");
        assertEquals(user1.getHeight(), user2.getHeight(), "Deux utilisateurs doivent faire la même taille");
        assertEquals(user1.getWeight(), user2.getWeight(), "Deux utilisateurs doivent avoir le même poids");
    }

}