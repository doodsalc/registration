package com.test.registration.service;

import com.test.registration.data.User;
import com.test.registration.entity.UserEntity;
import com.test.registration.exception.UserAlreadyDeletedException;
import com.test.registration.exception.UserNotFoundException;
import com.test.registration.exception.UsernameAlreadyExistsException;
import com.test.registration.helper.MailHelper;
import com.test.registration.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldRegisterUser() throws UsernameAlreadyExistsException {
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
        User user = getTestUser();
        userService.registerUser(user);
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(emailService, times(1)).sendWelcomeEmail(any(User.class));
    }

    @Test
    void shouldThrowExceptionIfUsernameAlreadyExistsWhenRegistering()  {
        when(userRepository.findByUsername(any(String.class))).thenReturn((Optional<UserEntity>) Optional.of(new UserEntity()));
        User user = getTestUser();
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(userRepository, times(0)).save(any(UserEntity.class));
        verify(emailService, times(0)).sendWelcomeEmail(any(User.class));
    }

    @Test
    void shouldSoftDeleteUser() throws UserNotFoundException, UserAlreadyDeletedException {
        when(userRepository.findByUsername(any(String.class))).thenReturn((Optional<UserEntity>) Optional.of(new UserEntity()));
        userService.softDeleteUser("test");
        verify(userRepository, times(1)).softDeleteByUsername(any(String.class));
    }

    @Test
    void shouldThrowExceptionIfUserIsNotRegisteredWhenDeleting() throws UserNotFoundException, UserAlreadyDeletedException {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.softDeleteUser("test"));
        verify(userRepository, times(0)).softDeleteByUsername(any(String.class));
    }

    @Test
    void shouldThrowExceptionIfUserIsAlreadyDeletedWhenDeleting() throws UserNotFoundException, UserAlreadyDeletedException {
        var deletedUser = new UserEntity();
        deletedUser.setDeleted(true);
        when(userRepository.findByUsername(any(String.class))).thenReturn((Optional<UserEntity>) Optional.of(deletedUser));
        assertThrows(UserAlreadyDeletedException.class, () -> userService.softDeleteUser("test"));
        verify(userRepository, times(0)).softDeleteByUsername(any(String.class));
    }

    @Test
    void shouldUpdateUser() throws UserNotFoundException, UserAlreadyDeletedException {
        when(userRepository.findByUsername(any(String.class))).thenReturn((Optional<UserEntity>) Optional.of(new UserEntity()));
        userService.updateUser(getTestUser());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionIfUserIsNotFoundWhenUpdating() throws UserNotFoundException, UserAlreadyDeletedException {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(getTestUser()));
        verify(userRepository, times(0)).softDeleteByUsername(any(String.class));
    }

    @Test
    void shouldThrowExceptionIfUserIsAlreadyDeletedWhenUpdating() throws UserNotFoundException, UserAlreadyDeletedException {
        var deletedUser = new UserEntity();
        deletedUser.setDeleted(true);
        when(userRepository.findByUsername(any(String.class))).thenReturn((Optional<UserEntity>) Optional.of(deletedUser));
        assertThrows(UserAlreadyDeletedException.class, () -> userService.updateUser(getTestUser()));
        verify(userRepository, times(0)).softDeleteByUsername(any(String.class));
    }

    @Test
    void shouldGetAllUsers() throws UserNotFoundException, UserAlreadyDeletedException {
        List<UserEntity> userList = new ArrayList<>();
        userList.add(new UserEntity());
        userList.add(new UserEntity());
        when(userRepository.findAll()).thenReturn(userList);
        var users = userService.listAllUsers();
        assertEquals(2, users.size());
    }

    private User getTestUser() {
        var user = new User();
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setFirstName("Ping");
        user.setFirstName("Pong");
        user.setPassword("secret");
        return user;

    }


}