package com.test.registration.service;

import com.test.registration.data.User;
import com.test.registration.entity.UserEntity;
import com.test.registration.exception.UserAlreadyDeletedException;
import com.test.registration.exception.UserNotFoundException;
import com.test.registration.exception.UsernameAlreadyExistsException;
import com.test.registration.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void registerUser(User user) throws UsernameAlreadyExistsException {
        var username = userRepository.findByUsername(user.getUsername());
        if(username.isPresent()){
            throw new UsernameAlreadyExistsException("User " + user.getUsername() + " already exists" );
        }
        userRepository.save(transformToEntity(user));
        emailService.sendWelcomeEmail(user);
   }

   @Transactional
   @Override
   public void softDeleteUser(String username) throws UserNotFoundException, UserAlreadyDeletedException {
        var fetchedUser = userRepository.findByUsername(username);
        if(fetchedUser.isEmpty()){
            throw new UserNotFoundException("User " + username + " is not registered" );
        } else if(fetchedUser.get().isDeleted()){
            throw new UserAlreadyDeletedException("User " + username + " is already deleted" );
        }
        userRepository.softDeleteByUsername(username);
    }

    @Override
    public void updateUser(User user) throws UserNotFoundException, UserAlreadyDeletedException {
       var fetchedUser = userRepository.findByUsername(user.getUsername());
       if(fetchedUser.isEmpty()){
           throw new UserNotFoundException("User " + user.getUsername() + " is not registered" );
       } else if(fetchedUser.get().isDeleted()){
           throw new UserAlreadyDeletedException("User " + user.getUsername() + " is already deleted" );
       }
       var updatedUser = fetchedUser.get();
       updatedUser.setFirstName(user.getFirstName());
       updatedUser.setLastName(user.getLastName());
       updatedUser.setEmail(user.getEmail());
       userRepository.save(updatedUser);
    }

    @Override
    public List<User> listAllRegisteredUsers(){
        List<User> userList = new ArrayList<>();
        userRepository.findByDeletedIsFalse().forEach(u -> userList.add(transformToData(u)));
        return userList;
    }

    @Override
    public List<User> listAllUsers() {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(u -> userList.add(transformToData(u)));
        return userList;
    }

    private User transformToData(UserEntity u) {
        var user = new User();
        user.setUsername(u.getUsername());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setEmail(u.getEmail());
        return user;
    }

    private UserEntity transformToEntity(User u) {
        var user = new UserEntity();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        user.setUsername(u.getUsername());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setEmail(u.getEmail());
        user.setEncryptedPassword(encoder.encode(u.getPassword()));
        return user;
    }
}
