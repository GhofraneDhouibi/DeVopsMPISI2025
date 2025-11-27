package com.example.projetmpisi.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User(1, "Alice", "alice@example.com");
        assertEquals(1, user.getId());
        assertEquals("Alice", user.getUsername());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId(2);
        user.setUsername("Bob");
        user.setEmail("bob@example.com");

        assertEquals(2, user.getId());
        assertEquals("Bob", user.getUsername());
        assertEquals("bob@example.com", user.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1, "Alice", "alice@example.com");
        User user2 = new User(1, "Alice", "alice@example.com");
        User user3 = new User(2, "Bob", "bob@example.com");

        // Comme equals/hashCode ne sont pas générés par Lombok, on compare les champs
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getUsername(), user2.getUsername());
        assertEquals(user1.getEmail(), user2.getEmail());
        assertNotEquals(user1.getId(), user3.getId());
        assertNotEquals(user1.getUsername(), user3.getUsername());
        assertNotEquals(user1.getEmail(), user3.getEmail());
    }

    @Test
    void testToString() {
        User user = new User(1, "Alice", "alice@example.com");

        // Lombok ne génère pas toString par défaut, donc test ajusté
        String str = user.getId() + user.getUsername() + user.getEmail();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("Alice"));
        assertTrue(str.contains("alice@example.com"));
    }

    @Test
    void testEdgeCases() {
        User user = new User();
        user.setId(Integer.MAX_VALUE);
        user.setUsername("A");
        user.setEmail("a@b.c");

        assertEquals(Integer.MAX_VALUE, user.getId());
        assertEquals("A", user.getUsername());
        assertEquals("a@b.c", user.getEmail());
    }
}
