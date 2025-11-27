package com.example.projetmpisi.service.imp;

import com.example.projetmpisi.entity.User;
import com.example.projetmpisi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "Alice", "alice@example.com");
        user2 = new User(2, "Bob", "bob@example.com");
    }

    @Test
    void saveUser_ShouldReturnSavedUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(user1);

        // Act
        User savedUser = userService.saveUser(user1);

        // Assert
        assertNotNull(savedUser);
        assertEquals(1, savedUser.getId());
        assertEquals("Alice", savedUser.getUsername());
        assertEquals("alice@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getUsername());
        assertEquals("Bob", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_WhenNoUsers_ShouldReturnEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        // Act
        Optional<User> result = userService.getUserById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getUsername());
        assertEquals("alice@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserById_WhenUserNotExists_ShouldReturnEmpty() {
        // Arrange
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserById(99);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(99);
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        // Arrange
        User updatedUser = new User(1, "AliceUpdated", "alice.updated@example.com");
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(1, updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals("AliceUpdated", result.getUsername());
        assertEquals("alice.updated@example.com", result.getEmail());
        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void updateUser_WhenUserNotExists_ShouldReturnNull() {
        // Arrange
        User updatedUser = new User(99, "NotFound", "notfound@example.com");
        when(userRepository.existsById(99)).thenReturn(false);

        // Act
        User result = userService.updateUser(99, updatedUser);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).existsById(99);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_ShouldSetCorrectId() {
        // Arrange
        User inputUser = new User(999, "WrongID", "wrong@example.com"); // ID différent
        User expectedUser = new User(1, "AliceUpdated", "alice.updated@example.com");

        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            assertEquals(1, userToSave.getId()); // Vérifie que l'ID a été corrigé
            return expectedUser;
        });

        // Act
        User result = userService.updateUser(1, inputUser);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldCallRepository() {
        // Arrange
        doNothing().when(userRepository).deleteById(1);

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteUser_WithInvalidId_ShouldStillCallRepository() {
        // Arrange
        doNothing().when(userRepository).deleteById(99);

        // Act
        userService.deleteUser(99);

        // Assert
        verify(userRepository, times(1)).deleteById(99);
    }
}