package test2;

import org.junit.jupiter.api.Test;
import test2.opdracht1.Team;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void testHashCode() {
        Team t = new Team("laurien", 13);
        int hash1 = t.hashCode();
        int hash2 = t.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testEquals() {
        Team t = new Team("Martin", 13);
        Team t2 = new Team("Martin", 13);
        assertTrue(t.equals(t2));
    }
    @Test
    void testNoEquals() {
        Team t = new Team("Martin", 13);
        Team t2 = new Team("Bob", 15);
        assertFalse(t.equals(t2));
    }

}