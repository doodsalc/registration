package com.test.registration.service;

import com.test.registration.data.User;
import com.test.registration.exception.UserAlreadyDeletedException;
import com.test.registration.exception.UserNotFoundException;
import com.test.registration.exception.UsernameAlreadyExistsException;

import java.util.List;

public interface UserService {
    void registerUser(User user) throws UsernameAlreadyExistsException;

    void softDeleteUser(String username) throws UserNotFoundException, UserAlreadyDeletedException;

    void updateUser(User user) throws UserNotFoundException, UserAlreadyDeletedException;

    List<User> listAllRegisteredUsers();

    List<User> listAllUsers();
}
